package com.example.infrastructure.repository.mongodb.service.patient_entity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.application.port.output.PatientRepositoryOutputPort;
import com.example.domain.model.Patient;
import com.example.infrastructure.repository.mongodb.entity.PatientEntity;
import com.example.infrastructure.repository.mongodb.mapper.PatientToPatientEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Repository service for handling CRUD operations for patients. Implements the
 * PatientRepositoryOutputPort interface.
 * 
 * Utilizes mappers to convert between Patient entities and domain
 * representations.
 * 
 * Uses caching to optimize patient retrieval operations.
 * 
 * @author Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@Component
public class PatientRepositoryService implements PatientRepositoryOutputPort {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	PatientToPatientEntityMapper patientToPatientEntityMapper;

	/**
	 * Retrieves a patient by their ID, using cache to optimize the operation.
	 * 
	 * @param id The ID of the patient.
	 * @return An Optional containing the patient if found, or empty if not found.
	 */
	@Override
	@Cacheable(value = "patients", key = "#id")
	public Optional<Patient> getPatient(@Valid String id) {
		log.debug("Getting patient");

		Optional<PatientEntity> opt = patientRepository.findByIdAndDeleted(id, false);

		return patientToPatientEntityMapper.fromOutputToInput(opt);
	}

	/**
	 * Retrieves a patient by their document, using cache to optimize the operation.
	 * 
	 * @param document The document of the patient.
	 * @return An Optional containing the patient if found, or empty if not found.
	 */
	@Override
	@Cacheable(value = "patients", key = "#document")
	public Optional<Patient> getPatientByDocument(@Valid String document) {
		log.debug("Getting patient with document");

		Optional<PatientEntity> opt = patientRepository.findByDocumentAndEliminate(document, false);

		return patientToPatientEntityMapper.fromOutputToInput(opt);
	}

	/**
	 * Retrieves all patients in a paginated format, using cache to optimize the
	 * operation.
	 * 
	 * @param pageable Pagination information.
	 * @return A page of patients.
	 */
	@Override
	@Cacheable(value = "patients", key = "#pageable")
	public Page<Patient> getAllPatients(@Valid Pageable pageable) {
		log.debug("Getting all patients");

		Page<PatientEntity> pageEntity = patientRepository.findByDeleted(pageable, false);

		return patientToPatientEntityMapper.fromOutputToInput(pageEntity);
	}

	/**
	 * Creates a new patient and clears the patient cache.
	 * 
	 * @param inputPatient The patient to create.
	 * @return The ID of the new patient.
	 */
	@Override
	@CacheEvict(value = "patients", allEntries = true)
	public String postPatient(@Valid Patient inputPatient) {
		log.debug("Creating a patient");

		PatientEntity mappedPat = patientToPatientEntityMapper.fromInputToOutput(inputPatient);

		return patientRepository.save(mappedPat).getId();
	}

	/**
	 * Modifies an existing patient and clears the patient cache.
	 * 
	 * @param inputPatient The patient with updated information.
	 */
	@Override
	@CacheEvict(value = "patients", allEntries = true)
	public void modifyPatient(@Valid Patient inputPatient) {
		log.debug("Modifying a patient");

		patientRepository.save(patientToPatientEntityMapper.fromInputToOutput(inputPatient));
	}

	/**
	 * Deletes a patient by their ID (marking it as deleted) and clears the patient
	 * cache.
	 * 
	 * @param idPatient The ID of the patient to delete.
	 */
	@Override
	@CacheEvict(value = "patients", allEntries = true)
	public void deletePatient(@Valid String idPatient) {
		log.debug("Deleting a patient");

		Optional<PatientEntity> opt = patientRepository.findByIdAndDeleted(idPatient, false);

		if (!opt.isPresent()) {
			return;
		}

		opt.get().setDeleted(true);
		patientRepository.save(opt.get());
	}
}
