package com.example.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Appointment;

import jakarta.validation.Valid;

public interface AppointmentServiceInputPort {

	Optional<Appointment> getAppointment(@Valid String id);

	Page<Appointment> getAllAppointments(@Valid Pageable pageable) throws ClinicLogicException;

	Page<Appointment> getAppointmentsByDoctorDocument(@Valid String document, @Valid Pageable pageable)
			throws ClinicLogicException;

	Page<Appointment> getAppointmentsByPatientDocument(@Valid String document, @Valid Pageable pageable)
			throws ClinicLogicException;

	String createAppointment(@Valid Appointment appointment) throws ClinicLogicException;

	void partialModificationAppointment(@Valid Appointment appointment) throws ClinicLogicException;

	void totalModificationAppointment(@Valid Appointment appointment) throws ClinicLogicException;

	void deleteAppointment(@Valid String idAppointment);
}