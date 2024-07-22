package com.example.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.port.input.PatientServiceInputPort;
import com.example.application.port.output.ClinicProducerOutputPort;
import com.example.application.port.output.PatientRepositoryOutputPort;
import com.example.application.util.ClinicLogicException;
import com.example.application.util.Errors;
import com.example.domain.mapper.ClinicPatchMapper;
import com.example.domain.model.Patient;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling operations related to patients. Implements the
 * {@link PatientServiceInputPort} interface.
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
public class PatientService implements PatientServiceInputPort {

	@Autowired
	PatientRepositoryOutputPort patientRepositoryOutputPort;

	@Autowired
	ClinicProducerOutputPort clinicProducerOutputPort;

	@Autowired
	ClinicPatchMapper clinicPatchMapper;

	/**
	 * Retrieves a patient by their ID.
	 * 
	 * @param id The ID of the patient.
	 * @return An Optional containing the patient if found, or empty if not found.
	 */
	@Override
	@Transactional
	public Optional<Patient> getPatient(@Valid String id) {
		log.debug("Getting one patient");

		return patientRepositoryOutputPort.getPatient(id);
	}

	/**
	 * Retrieves a patient by their document.
	 * 
	 * @param document The document of the patient.
	 * @return An Optional containing the patient if found, or empty if not found.
	 */
	@Override
	@Transactional
	public Optional<Patient> getPatientByDocument(@Valid String document) {
		log.debug("Getting one patient via document.");

		return patientRepositoryOutputPort.getPatient(document);
	}

	/**
	 * Retrieves all patients in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A Page containing patients.
	 */
	@Override
	@Transactional
	public Page<Patient> getAllPatients(@Valid Pageable pageable) {
		log.debug("Getting all patients");

		return patientRepositoryOutputPort.getAllPatients(pageable);
	}

	/**
	 * Creates a new patient.
	 * 
	 * @param inputPatient The patient to be created.
	 * @return The ID of the newly created patient.
	 */
	@Override
	@Transactional
	public String createPatient(@Valid Patient inputPatient) {
		log.debug("Creating a Patient");

		String newId = patientRepositoryOutputPort.postPatient(inputPatient);

		inputPatient.setId(newId);

		clinicProducerOutputPort.createdClinicEvent(inputPatient);

		return newId;
	}

	/**
	 * Partially modifies an existing patient.
	 * 
	 * @param inputPatient The patient with the updated information.
	 * @throws ClinicLogicException If the patient is not found.
	 */
	@Override
	@Transactional
	public void partialModificationPatient(@Valid Patient inputPatient) throws ClinicLogicException {
		log.debug("Modifying partially a patient");

		Optional<Patient> opt = patientRepositoryOutputPort.getPatient(inputPatient.getId());

		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.PATIENT_NOT_FOUND);
		}

		clinicPatchMapper.updatePatient(opt.get(), inputPatient);
		patientRepositoryOutputPort.modifyPatient(opt.get());
		clinicProducerOutputPort.modifiedClinicEvent(opt.get());
	}

	/**
	 * Totally modifies an existing patient.
	 * 
	 * @param inputPatient The patient with the updated information.
	 * @throws ClinicLogicException If the patient is not found.
	 */
	@Override
	@Transactional
	public void totalModificationPatient(@Valid Patient inputPatient) throws ClinicLogicException {
		log.debug("Totally modifying a patient");

		Optional<Patient> opt = patientRepositoryOutputPort.getPatient(inputPatient.getId());

		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.PATIENT_NOT_FOUND);
		}

		patientRepositoryOutputPort.modifyPatient(inputPatient);
		clinicProducerOutputPort.modifiedClinicEvent(inputPatient);
	}

	/**
	 * Deletes a patient by their ID.
	 * 
	 * @param idPatient The ID of the patient to be deleted.
	 */
	@Override
	@Transactional
	public void deletePatient(@Valid String idPatient) {
		log.debug("Deleting a patient");

		Optional<Patient> opt = patientRepositoryOutputPort.getPatient(idPatient);

		if (!opt.isPresent()) {
			return;
		}

		patientRepositoryOutputPort.deletePatient(idPatient);
		clinicProducerOutputPort.deletedClinicEvent(opt.get());
	}
}