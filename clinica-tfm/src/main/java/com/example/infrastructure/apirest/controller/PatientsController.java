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

import com.example.application.port.input.PatientServiceInputPort;
import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.request.patient_object.PatchPatientDto;
import com.example.infrastructure.apirest.dto.request.patient_object.PostPutPatientDto;
import com.example.infrastructure.apirest.mapper.patient_object.PatientToPatchPatientDto;
import com.example.infrastructure.apirest.mapper.patient_object.PatientToPostPutPatientDtoMapper;
import com.example.infrastructure.apirest.mapper.patient_object.PatientToResponsePatientDtoMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle requests related to patients. Provides endpoints to
 * retrieve, create, modify, and delete patients.
 * 
 * Uses mappers to convert between different representations of patients.
 * Utilizes the patient service input port to perform business operations.
 * 
 * Allows access from http://localhost:4200.
 * 
 * @author: Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientsController {

	@Autowired
	private PatientServiceInputPort patientServiceInputPort;

	@Autowired
	private PatientToPatchPatientDto patientToPatchPatientDto;

	@Autowired
	private PatientToPostPutPatientDtoMapper patientToPostPutPatientDtoMapper;

	@Autowired
	private PatientToResponsePatientDtoMapper patientToResponsePatientDtoMapper;

	/**
	 * Retrieves all patients in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A ResponseEntity containing a paginated list of patients.
	 */
	@GetMapping
	public ResponseEntity getAllPatients(@Valid Pageable pageable) {
		log.debug("Getting all patients");

		return ResponseEntity.ok(
				patientToResponsePatientDtoMapper.fromInputToOutput(patientServiceInputPort.getAllPatients(pageable)));
	}

	/**
	 * Retrieves a patient by its ID.
	 * 
	 * @param id The ID of the patient.
	 * @return A ResponseEntity containing the found patient.
	 */
	@GetMapping("/{patient-id}")
	public ResponseEntity getPatient(@Valid @PathVariable("patient-id") String id) {
		log.debug("Getting patient");

		return ResponseEntity
				.ok(patientToResponsePatientDtoMapper.fromInputToOutput(patientServiceInputPort.getPatient(id)));
	}

	/**
	 * Retrieves a patient by its document.
	 * 
	 * @param document The document of the patient.
	 * @return A ResponseEntity containing the found patient.
	 */
	@GetMapping("/list/{document}")
	public ResponseEntity getPatientByDocument(@PathVariable("document") String document) {
		log.debug("Getting patient with document");

		Optional<Patient> opt = patientServiceInputPort.getPatientByDocument(document);

		if (!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(patientToResponsePatientDtoMapper.fromInputToOutput(opt.get()));
	}

	/**
	 * Creates a new patient.
	 * 
	 * @param patient The DTO with the information of the patient to create.
	 * @return A ResponseEntity with the location of the new patient.
	 */
	@PostMapping
	public ResponseEntity postPatient(@Valid @RequestBody PostPutPatientDto patient) {
		log.debug("Creating patient");

		Patient domainPatient = patientToPostPutPatientDtoMapper.fromOutputToInput(patient);

		String idNewPatient = patientServiceInputPort.createPatient(domainPatient);

		URI locationHeader = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
				.buildAndExpand(idNewPatient).toUri();

		return ResponseEntity.created(locationHeader).build();
	}

	/**
	 * Completely modifies an existing patient.
	 * 
	 * @param id      The ID of the patient to modify.
	 * @param patient The DTO with the updated patient information.
	 * @return A ResponseEntity with a 204 status if successfully modified or an
	 *         error message.
	 */
	@PutMapping("/{patient-id}")
	public ResponseEntity putPatient(@Valid @PathVariable("patient-id") String id,
			@Valid @RequestBody PostPutPatientDto patient) {
		log.debug("Totally modifying a patient");

		Patient inputPatient = patientToPostPutPatientDtoMapper.fromOutputToInput(patient);

		try {
			patientServiceInputPort.totalModificationPatient(inputPatient);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * Partially modifies an existing patient.
	 * 
	 * @param id      The ID of the patient to modify.
	 * @param patient The DTO with the partial patient information.
	 * @return A ResponseEntity containing the modified patient or an error message.
	 */
	@PatchMapping("/{patient-id}")
	public ResponseEntity patchPatient(@Valid @PathVariable("patient-id") String id,
			@Valid @RequestBody PatchPatientDto patient) {
		log.debug("Partially modifying a patient");

		Patient inputPatient = patientToPatchPatientDto.fromOutputToInput(patient);

		try {
			patientServiceInputPort.partialModificationPatient(inputPatient);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		Optional<Patient> updated = patientServiceInputPort.getPatient(inputPatient.getId());

		PatchPatientDto response = patientToPatchPatientDto.fromInputToOutput(updated.get());

		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes a patient by its ID.
	 * 
	 * @param id The ID of the patient to delete.
	 * @return A ResponseEntity with a 204 status if successfully deleted.
	 */
	@DeleteMapping("/{patient-id}")
	public ResponseEntity deletePatient(@Valid @PathVariable("patient-id") String id) {
		log.debug("Deleting patient");

		patientServiceInputPort.deletePatient(id);

		return ResponseEntity.noContent().build();
	}
}
