package com.example.infrastructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.example.application.port.input.AppointmentServiceInputPort;
import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Appointment;
import com.example.infrastructure.apirest.dto.request.appointment_object.PatchAppointmentDto;
import com.example.infrastructure.apirest.dto.request.appointment_object.PostAppointmentDto;
import com.example.infrastructure.apirest.mapper.appointment_object.AppointmentToPatchAppointmentDtoMapper;
import com.example.infrastructure.apirest.mapper.appointment_object.AppointmentToPostAppointmentDtoMapper;
import com.example.infrastructure.apirest.mapper.appointment_object.AppointmentToResponseAppointmentDtoMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle requests related to appointments. Provides endpoints to
 * retrieve, create, modify, and delete appointments.
 * 
 * Uses mappers to convert between different representations of appointments.
 * Utilizes the appointment service input port to perform business operations.
 * 
 * Allows access from http://localhost:4200.
 * 
 * @author Ricardo Rodilla Navarro (rrodillan@gmail.com)
 */
@Slf4j
@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentsController {

	@Autowired
	private AppointmentServiceInputPort appointmentServiceInputPort;

	@Autowired
	private AppointmentToPatchAppointmentDtoMapper appointmentToPatchAppointmentDtoMapper;

	@Autowired
	private AppointmentToPostAppointmentDtoMapper appointmentToPostAppointmentDtoMapper;

	@Autowired
	private AppointmentToResponseAppointmentDtoMapper appointmentToResponseAppointmentDtoMapper;

	/**
	 * Retrieves all appointments in a paginated format.
	 * 
	 * @param pageable Pagination information.
	 * @return A ResponseEntity containing a paginated list of appointments or an
	 *         error message.
	 */
	@GetMapping
	public ResponseEntity getAllAppointments(Pageable pageable) {
		log.debug("Getting all appointments");

		Page<Appointment> appointments;
		try {
			appointments = appointmentServiceInputPort.getAllAppointments(pageable);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(appointmentToResponseAppointmentDtoMapper.fromInputToOutput(appointments));
	}

	/**
	 * Retrieves appointments by doctor's document.
	 * 
	 * @param document The doctor's document.
	 * @param pageable Pagination information.
	 * @return A ResponseEntity containing a paginated list of appointments or an
	 *         error message.
	 */
	@GetMapping("/doctors/{document}")
	public ResponseEntity getAppointmentsByDoctorDocument(@PathVariable("document") String document,
			Pageable pageable) {
		log.debug("Getting appointments by doctor's document");

		Page<Appointment> doctorAppointments;
		try {
			doctorAppointments = appointmentServiceInputPort.getAppointmentsByDoctorDocument(document, pageable);
			return ResponseEntity.ok(doctorAppointments);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves appointments by patient's document.
	 * 
	 * @param document The patient's document.
	 * @param pageable Pagination information.
	 * @return A ResponseEntity containing a paginated list of appointments or an
	 *         error message.
	 */
	@GetMapping("/patients/{document}")
	public ResponseEntity getAppointmentsByPatientDocument(@PathVariable("document") String document,
			Pageable pageable) {
		log.debug("Getting appointments by patient's document");

		Page<Appointment> patientAppointments;
		try {
			patientAppointments = appointmentServiceInputPort.getAppointmentsByPatientDocument(document, pageable);
			return ResponseEntity.ok(patientAppointments);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves an appointment by its ID.
	 * 
	 * @param idAppo The appointment ID.
	 * @return A ResponseEntity containing the appointment or a 204 status if not
	 *         found.
	 */
	@GetMapping("/{appointment-id}")
	public ResponseEntity getAppointment(@PathVariable("appointment-id") String idAppo) {
		log.debug("Getting an appointment");

		Optional<Appointment> appointment = appointmentServiceInputPort.getAppointment(idAppo);

		if (appointment.isPresent()) {
			return ResponseEntity.ok(appointmentToResponseAppointmentDtoMapper.fromInputToOutput(appointment.get()));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * Creates a new appointment.
	 * 
	 * @param appointmentDto The DTO with the appointment information to create.
	 * @return A ResponseEntity with the location of the new appointment or an error
	 *         message.
	 */
	@PostMapping
	public ResponseEntity postAppointment(@RequestBody @Valid PostAppointmentDto appointmentDto) {
		log.debug("Creating an appointment");
		try {
			Appointment appointment = appointmentToPostAppointmentDtoMapper.fromOutputToInput(appointmentDto);
			String appoId = appointmentServiceInputPort.createAppointment(appointment);

			URI locationHeader = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(appoId)
					.toUri();

			return ResponseEntity.created(locationHeader).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Fully modifies an existing appointment.
	 * 
	 * @param idAppointment  The ID of the appointment to modify.
	 * @param appointmentDto The DTO with the updated appointment information.
	 * @return A ResponseEntity with a 204 status if successfully modified or an
	 *         error message.
	 */
	@PutMapping("/{appointment-id}")
	public ResponseEntity putAppointment(@Valid @PathVariable("appointment-id") final String idAppointment,
			@Valid @RequestBody PostAppointmentDto appointmentDto) {
		log.debug("Totally modifying an appointment");

		Appointment appointment = appointmentToPostAppointmentDtoMapper.fromOutputToInput(appointmentDto);

		try {
			appointmentServiceInputPort.totalModificationAppointment(appointment);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * Partially modifies an existing appointment.
	 * 
	 * @param id             The ID of the appointment to modify.
	 * @param appointmentDto The DTO with the partial appointment information.
	 * @return A ResponseEntity with a 200 status if successfully modified or an
	 *         error message.
	 */
	@PatchMapping("/{appointment-id}")
	public ResponseEntity patchAppointment(@PathVariable("appointment-id") String id,
			@RequestBody PatchAppointmentDto appointmentDto) {
		log.debug("Partially modifying an appointment");

		Appointment appointment = appointmentToPatchAppointmentDtoMapper.fromOutputToInput(appointmentDto);

		try {
			appointmentServiceInputPort.partialModificationAppointment(appointment);
		} catch (ClinicLogicException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * Deletes an appointment by its ID.
	 * 
	 * @param id The ID of the appointment to delete.
	 * @return A ResponseEntity with a 204 status if successfully deleted.
	 */
	@DeleteMapping("/{appointment-id}")
	public ResponseEntity deleteAppointment(@PathVariable("appointment-id") String id) {
		log.debug("Deleting an appointment");
		appointmentServiceInputPort.deleteAppointment(id);

		return ResponseEntity.noContent().build();
	}
}
