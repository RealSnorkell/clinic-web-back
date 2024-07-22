package com.example.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.port.input.AppointmentServiceInputPort;
import com.example.application.port.output.AppointmentRepositoryOutputPort;
import com.example.application.port.output.ClinicProducerOutputPort;
import com.example.application.port.output.DoctorRepositoryOutputPort;
import com.example.application.port.output.PatientRepositoryOutputPort;
import com.example.application.util.ClinicLogicException;
import com.example.application.util.Errors;
import com.example.domain.mapper.ClinicPatchMapper;
import com.example.domain.model.Appointment;
import com.example.domain.model.Doctor;
import com.example.domain.model.Patient;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling operations related to appointments. Implements the
 * {@link AppointmentServiceInputPort} interface.
 * 
 * Uses various output ports to interact with repositories and other services.
 * 
 * Logs activities at the debug level using the {@link Slf4j} annotation.
 * Annotated with {@link Service} to indicate it's a service-layer component.
 * 
 * @author Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@Service
public class AppointmentService implements AppointmentServiceInputPort {

	@Autowired
	PatientRepositoryOutputPort patientRepositoryOutputPort;

	@Autowired
	DoctorRepositoryOutputPort doctorRepositoryOutputPort;

	@Autowired
	AppointmentRepositoryOutputPort appointmentRepositoryOutputPort;

	@Autowired
	ClinicProducerOutputPort clinicProducerOutputPort;

	@Autowired
	ClinicPatchMapper clinicPatchMapper;

	/**
	 * Retrieves all appointments in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 * @throws ClinicLogicException If the pagination exceeds the maximum allowed
	 *                              size.
	 */
	@Override
	@Transactional
	public Page<Appointment> getAllAppointments(@Valid Pageable pageable) throws ClinicLogicException {
		log.debug("Getting all appointments");

		if (pageable.getPageSize() >= 100) {
			throw new ClinicLogicException(Errors.MAXIMUM_PAGINATION);
		}

		return appointmentRepositoryOutputPort.getAllAppointments(pageable);
	}

	/**
	 * Retrieves all appointments for a doctor by the doctor's document in a
	 * paginated format.
	 * 
	 * @param document The document of the doctor.
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 * @throws ClinicLogicException If the doctor is not found.
	 */
	@Override
	@Transactional
	public Page<Appointment> getAppointmentsByDoctorDocument(@Valid String document, @Valid Pageable pageable)
			throws ClinicLogicException {
		log.debug("Getting appointments by doctor's document.");

		Optional<Doctor> opt = doctorRepositoryOutputPort.getDoctorByDocument(document);
		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.DOCTOR_NOT_FOUND);
		}
		return appointmentRepositoryOutputPort.getAppointmentsByDoctorDocument(opt.get().getIdTypeContent(), pageable);
	}

	/**
	 * Retrieves all appointments for a patient by the patient's document in a
	 * paginated format.
	 * 
	 * @param document The document of the patient.
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 * @throws ClinicLogicException If the patient is not found.
	 */
	@Override
	@Transactional
	public Page<Appointment> getAppointmentsByPatientDocument(@Valid String document, @Valid Pageable pageable)
			throws ClinicLogicException {
		log.debug("Getting appointments by patient's document");

		Optional<Patient> opt = patientRepositoryOutputPort.getPatientByDocument(document);
		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.PATIENT_NOT_FOUND);
		}
		return appointmentRepositoryOutputPort.getAppointmentsByPatientDocument(opt.get().getIdTypeContent(), pageable);
	}

	/**
	 * Retrieves an appointment by its ID.
	 * 
	 * @param id The ID of the appointment.
	 * @return An Optional containing the appointment if found, or empty if not
	 *         found.
	 */
	@Override
	@Transactional
	public Optional<Appointment> getAppointment(@Valid String id) {
		log.debug("Getting appointment");

		return appointmentRepositoryOutputPort.getAppointment(id);
	}

	/**
	 * Creates a new appointment.
	 * 
	 * @param appointment The appointment to be created.
	 * @return The ID of the new appointment.
	 * @throws ClinicLogicException If the associated patient or doctor is not
	 *                              found.
	 */
	@Override
	@Transactional
	public String createAppointment(@Valid Appointment appointment) throws ClinicLogicException {
		log.debug("Creating an appointment");

		String savedAppointment = null;

		Optional<Patient> patOpt = patientRepositoryOutputPort.getPatient(appointment.getPatient().getId());
		if (patOpt.isPresent()) {
			Patient patient = patOpt.get();

			Optional<Doctor> doctorOpt = doctorRepositoryOutputPort.getDoctor(appointment.getDoctor().getId());
			if (doctorOpt.isPresent()) {
				Doctor doctor = doctorOpt.get();

				// Establishing data for objects in the appointment.
				clinicPatchMapper.updatePatient(appointment.getPatient(), patient);
				clinicPatchMapper.updateDoctor(appointment.getDoctor(), doctor);

				// Saving appointment.
				savedAppointment = appointmentRepositoryOutputPort.postAppointment(appointment);

				// Adding the appointment to both parties.
				doctor.getIdDoctorAppointments().add(savedAppointment);
				doctorRepositoryOutputPort.modifyDoctor(doctor);
				patient.getIdPatientAppointments().add(savedAppointment);
				patientRepositoryOutputPort.modifyPatient(patient);
				clinicProducerOutputPort.createdClinicEvent(savedAppointment);
			} else {
				throw new ClinicLogicException(Errors.DOCTOR_NOT_FOUND);
			}
		} else {
			throw new ClinicLogicException(Errors.PATIENT_NOT_FOUND);
		}
		return savedAppointment;
	}

	/**
	 * Totally modifies an appointment.
	 * 
	 * @param inputAppointment The appointment with updated information.
	 * @throws ClinicLogicException If the appointment is not found.
	 */
	@Override
	@Transactional
	public void totalModificationAppointment(@Valid Appointment inputAppointment) throws ClinicLogicException {
		log.debug("Totally modifying an appointment");

		Optional<Appointment> opt = appointmentRepositoryOutputPort.getAppointment(inputAppointment.getAppointmentId());

		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.APPOINTMENT_NOT_FOUND);
		}

		appointmentRepositoryOutputPort.modifyAppointment(inputAppointment);
		clinicProducerOutputPort.modifiedClinicEvent(inputAppointment);
	}

	/**
	 * Partially modifies an appointment.
	 * 
	 * @param inputAppointment The appointment with partially updated information.
	 * @throws ClinicLogicException If the appointment is not found.
	 */
	@Override
	@Transactional
	public void partialModificationAppointment(@Valid Appointment inputAppointment) throws ClinicLogicException {
		log.debug("Partially modifying an appointment");

		Optional<Appointment> opt = appointmentRepositoryOutputPort.getAppointment(inputAppointment.getAppointmentId());
		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.APPOINTMENT_NOT_FOUND);
		}

		clinicPatchMapper.updateAppointment(opt.get(), inputAppointment);
		appointmentRepositoryOutputPort.modifyAppointment(opt.get());
		clinicProducerOutputPort.modifiedClinicEvent(opt.get());
	}

	/**
	 * Deletes an appointment by its ID.
	 * 
	 * @param idAppointment The ID of the appointment to be deleted.
	 */
	@Override
	@Transactional
	public void deleteAppointment(@Valid String idAppointment) {
		log.debug("Deleting appointment");

		Optional<Appointment> opt = appointmentRepositoryOutputPort.getAppointment(idAppointment);
		if (!opt.isPresent()) {
			return;
		}

		appointmentRepositoryOutputPort.deleteAppointment(idAppointment);
		clinicProducerOutputPort.deletedClinicEvent(opt.get());
	}
}