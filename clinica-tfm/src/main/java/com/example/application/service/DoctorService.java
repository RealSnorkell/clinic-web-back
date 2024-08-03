package com.example.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.port.input.DoctorServiceInputPort;
import com.example.application.port.output.ClinicProducerOutputPort;
import com.example.application.port.output.DoctorRepositoryOutputPort;
import com.example.application.util.ClinicLogicException;
import com.example.application.util.Errors;
import com.example.domain.mapper.ClinicPatchMapper;
import com.example.domain.model.Doctor;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling operations related to doctors. Implements the
 * {@link DoctorServiceInputPort} interface.
 * 
 * Uses various output ports to interact with repositories and other services.
 * 
 * @autor Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@Service
public class DoctorService implements DoctorServiceInputPort {

	@Autowired
	DoctorRepositoryOutputPort doctorRepositoryOutputPort;

	@Autowired
	ClinicProducerOutputPort clinicProducerOutputPort;

	@Autowired
	ClinicPatchMapper clinicPatchMapper;

	/**
	 * Retrieves a doctor by their ID.
	 * 
	 * @param id The ID of the doctor.
	 * @return An Optional containing the doctor if found, or empty if not found.
	 */
	@Override
	@Transactional
	public Optional<Doctor> getDoctor(@Valid String id) {
		log.debug("Getting doctor.");

		return doctorRepositoryOutputPort.getDoctor(id);
	}

	/**
	 * Retrieves a doctor by their document.
	 * 
	 * @param document The document of the doctor.
	 * @return An Optional containing the doctor if found, or empty if not found.
	 */
	@Override
	@Transactional
	public Optional<Doctor> getDoctorByDocument(@Valid String document) {
		log.debug("Getting doctor via document.");

		return doctorRepositoryOutputPort.getDoctor(document);
	}

	/**
	 * Retrieves all doctors in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A Page containing doctors.
	 */
	@Override
	@Transactional
	public Page<Doctor> getAllDoctors(@Valid Pageable pageable) {
		log.debug("Getting all doctors");

		return doctorRepositoryOutputPort.getAllDoctors(pageable);
	}

	/**
	 * Creates a new doctor.
	 * 
	 * @param inputDoc The doctor to be created.
	 * @return The ID of the newly created doctor.
	 */
	@Override
	@Transactional
	public String createDoctor(@Valid Doctor inputDoc) {
		log.debug("Creating a doctor");

		String newId = doctorRepositoryOutputPort.postDoctor(inputDoc);

		inputDoc.setId(newId);

		return newId;
	}

	/**
	 * Partially modifies an existing doctor.
	 * 
	 * @param inputDoc The doctor with the updated information.
	 * @throws ClinicLogicException If the doctor is not found.
	 */
	@Override
	@Transactional
	public void partialModificationDoctor(@Valid Doctor inputDoc) throws ClinicLogicException {
		log.debug("Partially modifying a doctor.");

		Optional<Doctor> opt = doctorRepositoryOutputPort.getDoctor(inputDoc.getId());

		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.DOCTOR_NOT_FOUND);
		}

		clinicPatchMapper.updateDoctor(opt.get(), inputDoc);
		doctorRepositoryOutputPort.modifyDoctor(opt.get());
		// clinicProducerOutputPort.modifiedClinicEvent(opt.get()); //Kafka not working.
	}

	/**
	 * Totally modifies an existing doctor.
	 * 
	 * @param inputDoc The doctor with the updated information.
	 * @throws ClinicLogicException If the doctor is not found.
	 */
	@Override
	@Transactional
	public void totalModificationDoctor(@Valid Doctor inputDoc) throws ClinicLogicException {
		log.debug("Totally modifying a doctor");

		Optional<Doctor> opt = doctorRepositoryOutputPort.getDoctor(inputDoc.getId());

		if (!opt.isPresent()) {
			throw new ClinicLogicException(Errors.DOCTOR_NOT_FOUND);
		}

		doctorRepositoryOutputPort.modifyDoctor(inputDoc);
		// clinicProducerOutputPort.modifiedClinicEvent(inputDoc); //Kafka not working.
	}

	/**
	 * Deletes a doctor by their ID.
	 * 
	 * @param idDoc The ID of the doctor to be deleted.
	 */
	@Override
	@Transactional
	public void deleteDoctor(@Valid String idDoc) {
		log.debug("Deleting a doctor");

		Optional<Doctor> opt = doctorRepositoryOutputPort.getDoctor(idDoc);

		if (!opt.isPresent()) {
			return;
		}

		doctorRepositoryOutputPort.deleteDoctor(idDoc);
		// clinicProducerOutputPort.deletedClinicEvent(opt.get()); //Kafka not working.
	}
}