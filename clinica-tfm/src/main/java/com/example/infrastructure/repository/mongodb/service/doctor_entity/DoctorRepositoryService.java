package com.example.infrastructure.repository.mongodb.service.doctor_entity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.application.port.output.DoctorRepositoryOutputPort;
import com.example.domain.model.Doctor;
import com.example.infrastructure.repository.mongodb.entity.DoctorEntity;
import com.example.infrastructure.repository.mongodb.mapper.DoctorToDoctorEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing doctor repository operations. Provides methods to
 * retrieve, create, modify, and delete doctors, with caching to optimize
 * operations.
 * 
 * Utilizes mappers to convert between doctor models and entities. Uses the
 * doctor repository for data access operations.
 *
 * @autor Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@Component
public class DoctorRepositoryService implements DoctorRepositoryOutputPort {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	DoctorToDoctorEntityMapper doctorToDoctorEntityMapper;

	/**
	 * Retrieves a doctor by their ID, using cache to optimize the operation.
	 * 
	 * @param id The ID of the doctor.
	 * @return An Optional containing the doctor if found, or empty if not found.
	 */
	@Override
	@Cacheable(value = "doctors", key = "#id")
	public Optional<Doctor> getDoctor(@Valid String id) {
		log.debug("Getting a doctor");

		Optional<DoctorEntity> opt = doctorRepository.findByIdAndDeleted(id, false);

		return doctorToDoctorEntityMapper.fromOutputToInput(opt);
	}

	/**
	 * Retrieves a doctor by their document, using cache to optimize the operation.
	 * 
	 * @param document The document of the doctor.
	 * @return An Optional containing the doctor if found, or empty if not found.
	 */
	@Override
	@Cacheable(value = "doctors", key = "#document")
	public Optional<Doctor> getDoctorByDocument(@Valid String document) {
		log.debug("Getting doctor with document");

		Optional<DoctorEntity> opt = doctorRepository.findByPersonalInformationEntityDocumentAndDeleted(document,
				false);

		return doctorToDoctorEntityMapper.fromOutputToInput(opt);
	}

	/**
	 * Retrieves all doctors in a paginated format, using cache to optimize the
	 * operation.
	 * 
	 * @param pageable Pagination information.
	 * @return A page of doctors.
	 */
	@Override
	@Cacheable(value = "doctors", key = "#pageable")
	public Page<Doctor> getAllDoctors(@Valid Pageable pageable) {
		log.debug("Getting all doctors");

		Page<DoctorEntity> pageableDoctors = doctorRepository.findByDeleted(pageable, false);

		return doctorToDoctorEntityMapper.fromOutputToInput(pageableDoctors);
	}

	/**
	 * Creates a new doctor and clears the doctor cache.
	 * 
	 * @param inputDoc The doctor to create.
	 * @return The ID of the new doctor.
	 */
	@Override
	@CacheEvict(value = "doctors", allEntries = true)
	public String postDoctor(@Valid Doctor inputDoc) {
		log.debug("Creating a doctor");

		DoctorEntity mappedDoc = doctorToDoctorEntityMapper.fromInputToOutput(inputDoc);

		return doctorRepository.save(mappedDoc).getId();
	}

	/**
	 * Modifies an existing doctor and clears the doctor cache.
	 * 
	 * @param inputDoc The doctor with updated information.
	 */
	@Override
	@CacheEvict(value = "doctors", allEntries = true)
	public void modifyDoctor(@Valid Doctor inputDoc) {
		log.debug("Modifying a doctor");

		doctorRepository.save(doctorToDoctorEntityMapper.fromInputToOutput(inputDoc));
	}

	/**
	 * Deletes a doctor by their ID (marking it as deleted) and clears the doctor
	 * cache.
	 * 
	 * @param idDoc The ID of the doctor to delete.
	 */
	@Override
	@CacheEvict(value = "doctors", allEntries = true)
	public void deleteDoctor(@Valid String idDoc) {
		log.debug("Deleting a doctor");

		Optional<DoctorEntity> opt = doctorRepository.findByIdAndDeleted(idDoc, false);

		if (!opt.isPresent()) {
			return;
		}
		opt.get().setDeleted(true);
		doctorRepository.save(opt.get());
	}
}
