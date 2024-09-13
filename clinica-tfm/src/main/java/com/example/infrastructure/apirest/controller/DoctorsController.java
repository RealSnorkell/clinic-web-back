package com.example.infrastructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.application.port.input.DoctorServiceInputPort;
import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Doctor;
import com.example.infrastructure.apirest.dto.request.doctor_object.PatchDoctorDto;
import com.example.infrastructure.apirest.dto.request.doctor_object.PostPutDoctorDto;
import com.example.infrastructure.apirest.mapper.doctor_object.DoctorToPatchDoctorDtoMapper;
import com.example.infrastructure.apirest.mapper.doctor_object.DoctorToPostPutDoctorDtoMapper;
import com.example.infrastructure.apirest.mapper.doctor_object.DoctorToResponseDoctorDtoMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle requests related to doctors. Provides endpoints to
 * retrieve, create, modify, and delete doctors.
 * 
 * Uses mappers to convert between different representations of doctors.
 * Utilizes the doctor service input port to perform business operations.
 * 
 * Allows access from http://localhost:4200.
 * 
 * @author: Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorsController {

	@Autowired
	private DoctorServiceInputPort doctorServiceInputPort;

	@Autowired
	private DoctorToPatchDoctorDtoMapper doctorToPatchDoctorDtoMapper;

	@Autowired
	private DoctorToPostPutDoctorDtoMapper doctorToPostPutDoctorDtoMapper;

	@Autowired
	private DoctorToResponseDoctorDtoMapper doctorToResponseDoctorDtoMapper;

	/**
	 * Retrieves all doctors in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A ResponseEntity containing a paginated list of doctors.
	 */
	@GetMapping
	public ResponseEntity getAllDoctors(@Valid Pageable pageable) {
		log.debug("Getting all doctors");

		return ResponseEntity
				.ok(doctorToResponseDoctorDtoMapper.fromInputToOutput(doctorServiceInputPort.getAllDoctors(pageable)));
	}

	/**
	 * Retrieves a doctor by its ID.
	 * 
	 * @param id The doctor ID.
	 * @return A ResponseEntity containing the found doctor.
	 */
	@GetMapping("/{doctor-id}")
	public ResponseEntity getDoctor(@Valid @PathVariable("doctor-id") String id) {
		log.debug("Getting doctor");

		return ResponseEntity
				.ok(doctorToResponseDoctorDtoMapper.fromInputToOutput(doctorServiceInputPort.getDoctor(id)));
	}

	/**
	 * Retrieves a doctor by its document.
	 * 
	 * @param document The doctor's document.
	 * @return A ResponseEntity containing the found doctor.
	 */
	@GetMapping("/list/{document}")
	public ResponseEntity getDoctorByDocument(@PathVariable("document") String document) {
		log.debug("Getting doctor with document");

		Optional<Doctor> opt = doctorServiceInputPort.getDoctorByDocument(document);

		if (!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(doctorToResponseDoctorDtoMapper.fromInputToOutput(opt.get()));
	}

	/**
	 * Creates a new doctor.
	 * 
	 * @param doctor The DTO with the information of the doctor to create.
	 * @return A ResponseEntity with the location of the new doctor.
	 */
	@PostMapping
	public ResponseEntity postDoctor(@Valid @RequestBody PostPutDoctorDto doctor) {
		log.debug("Creating doctor");

		Doctor domainDoc = doctorToPostPutDoctorDtoMapper.fromOutputToInput(doctor);

		String idNewDoctor = doctorServiceInputPort.createDoctor(domainDoc);

		URI locationHeader = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
				.buildAndExpand(idNewDoctor).toUri();

		return ResponseEntity.created(locationHeader).build();
	}

	/**
	 * Fully modifies an existing doctor.
	 * 
	 * @param id     The ID of the doctor to modify.
	 * @param doctor The DTO with the updated doctor information.
	 * @return A ResponseEntity with a 204 status if successfully modified or an
	 *         error message.
	 */
	@PutMapping("/{doctor-id}")
	public ResponseEntity putDoctor(@Valid @PathVariable("doctor-id") String id,
			@Valid @RequestBody PostPutDoctorDto doctor) {
		log.debug("Totally modifying a doctor");

		Doctor inputDoc = doctorToPostPutDoctorDtoMapper.fromOutputToInput(doctor);

		try {
			doctorServiceInputPort.totalModificationDoctor(inputDoc);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * Partially modifies an existing doctor.
	 * 
	 * @param id     The ID of the doctor to modify.
	 * @param doctor The DTO with the partial doctor information.
	 * @return A ResponseEntity containing the modified doctor or an error message.
	 */
	@PatchMapping("/{doctor-id}")
	public ResponseEntity patchDoctor(@Valid @PathVariable("doctor-id") String id,
			@Valid @RequestBody PatchDoctorDto doctor) {
		log.debug("Partially modifying a doctor");

		Doctor inputDoc = doctorToPatchDoctorDtoMapper.fromOutputToInput(doctor);
		inputDoc.setId(id);

		try {
			doctorServiceInputPort.partialModificationDoctor(inputDoc);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		Optional<Doctor> updated = doctorServiceInputPort.getDoctor(inputDoc.getId());

		PatchDoctorDto response = doctorToPatchDoctorDtoMapper.fromInputToOutput(updated.get());

		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes a doctor by its ID.
	 * 
	 * @param id The ID of the doctor to delete.
	 * @return A ResponseEntity with a 204 status if successfully deleted.
	 */
	@DeleteMapping("/{doctor-id}")
	public ResponseEntity deleteDoctor(@Valid @PathVariable("doctor-id") String id) {
		log.debug("Deleting doctor");

		doctorServiceInputPort.deleteDoctor(id);

		return ResponseEntity.noContent().build();
	}
}
