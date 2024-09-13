package com.example.infrastructure.repository.mongodb.service.appointment_entity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.port.output.AppointmentRepositoryOutputPort;
import com.example.application.port.output.DoctorRepositoryOutputPort;
import com.example.application.port.output.PatientRepositoryOutputPort;
import com.example.domain.model.Appointment;
import com.example.infrastructure.repository.mongodb.entity.AppointmentEntity;
import com.example.infrastructure.repository.mongodb.mapper.AppointmentToAppointmentEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing appointment repository operations. Provides methods to
 * retrieve, create, modify, and delete appointments, with caching to optimize
 * operations.
 * 
 * Utilizes mappers to convert between appointment models and entities. Uses the
 * appointment repository for data access operations.
 * 
 * @author Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@Component
public class AppointmentsRepositoryService implements AppointmentRepositoryOutputPort {

	@Autowired
	private PatientRepositoryOutputPort patientRepository;

	@Autowired
	private DoctorRepositoryOutputPort doctorRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AppointmentToAppointmentEntityMapper appointmentToAppointmentEntityMapper;

	/**
	 * Retrieves all appointments in a paginated format, using cache to optimize the
	 * operation.
	 * 
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 */
	@Override
	@Cacheable(value = "appointments", key = "#pageable")
	public Page<Appointment> getAllAppointments(@Valid Pageable pageable) {
		log.debug("Getting all appointments");

		Page<AppointmentEntity> pageEntity = appointmentRepository.findByDeleted(pageable, false);

		return appointmentToAppointmentEntityMapper.fromOutputToInput(pageEntity);
	}

	/**
	 * Retrieves an appointment by its ID, using cache to optimize the operation.
	 * 
	 * @param id The ID of the appointment.
	 * @return An Optional containing the appointment if found, or empty if not
	 *         found.
	 */
	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Optional<Appointment> getAppointment(@Valid String id) {
		log.debug("Getting an appointment");

		Optional<AppointmentEntity> opt = appointmentRepository.findByAppointmentIdAndDeleted(id, false);

		return appointmentToAppointmentEntityMapper.fromOutputToInput(opt);
	}

	/**
	 * Retrieves all appointments for a doctor by the doctor's document, using cache
	 * to optimize the operation.
	 * 
	 * @param document The document of the doctor.
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 */
	@Override
	@Cacheable(value = "appointments", key = "#document")
	public Page<Appointment> getAppointmentsByDoctorDocument(@Valid String document, Pageable pageable) {
		log.debug("Getting appointments for doctor with document");

		Page<AppointmentEntity> pageEntity = appointmentRepository
				.findByDoctorPersonalInformationDocumentAndDeleted(document, false, pageable);

		return appointmentToAppointmentEntityMapper.fromOutputToInput(pageEntity);
	}

	/**
	 * Retrieves all appointments for a patient by the patient's document, using
	 * cache to optimize the operation.
	 * 
	 * @param document The document of the patient.
	 * @param pageable Pagination information.
	 * @return A page of appointments.
	 */
	@Override
	@Cacheable(value = "appointments", key = "#document")
	public Page<Appointment> getAppointmentsByPatientDocument(@Valid String document, Pageable pageable) {
		log.debug("Getting appointments for patient with document");

		Page<AppointmentEntity> pageEntity = appointmentRepository
				.findByPatientPersonalInformationDocumentAndDeleted(document, false, pageable);

		return appointmentToAppointmentEntityMapper.fromOutputToInput(pageEntity);
	}

	/**
	 * Creates a new appointment and clears the appointment cache.
	 * 
	 * @param appointment The appointment to create.
	 * @return The ID of the new appointment.
	 */
	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public String postAppointment(@Valid Appointment appointment) {
		log.debug("Creating an appointment");

		AppointmentEntity appToSave = appointmentToAppointmentEntityMapper.fromInputToOutput(appointment);

		return appointmentRepository.save(appToSave).getAppointmentId();
	}

	/**
	 * Modifies an existing appointment and clears the appointment cache.
	 * 
	 * @param appointment The appointment with updated information.
	 */
	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public void modifyAppointment(@Valid Appointment appointment) {
		log.debug("Modifying an appointment");

		appointmentRepository.save(appointmentToAppointmentEntityMapper.fromInputToOutput(appointment));
	}

	/**
	 * Deletes an appointment by its ID (marking it as deleted) and clears the
	 * appointment cache.
	 * 
	 * @param idAppointment The ID of the appointment to delete.
	 */
	@Override
	@Transactional
	@CacheEvict(value = "appointments", allEntries = true)
	public void deleteAppointment(@Valid String idAppointment) {
		log.debug("Deleting an appointment");

		Optional<AppointmentEntity> opt = appointmentRepository.findByAppointmentIdAndDeleted(idAppointment, false);

		if (!opt.isPresent()) {
			return;
		}

		opt.get().setDeleted(true);
		appointmentRepository.save(opt.get());

		// Recorremos al paciente para eliminar la cita asociada.
		List<String> listOfAppPatient = opt.get().getPatient().getIdPatientAppointments();
		for (String id : listOfAppPatient) {
			if (id.equals(idAppointment)) {
				listOfAppPatient.remove(id);
			}
		}
		opt.get().getPatient().setIdPatientAppointments(listOfAppPatient);
		patientRepository.modifyPatient(opt.get().getPatient());

		// Recorremos al doctor para eliminar la cita asociada.
		List<String> listOfAppDoc = opt.get().getDoctor().getIdDoctorAppointments();
		for (String id : listOfAppDoc) {
			if (id.equals(idAppointment)) {
				listOfAppDoc.remove(id);
			}
		}
		opt.get().getDoctor().setIdDoctorAppointments(listOfAppDoc);
		doctorRepository.modifyDoctor(opt.get().getDoctor());
	}
}