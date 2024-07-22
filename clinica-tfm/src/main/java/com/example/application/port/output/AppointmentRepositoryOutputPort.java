package com.example.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domain.model.Appointment;

import jakarta.validation.Valid;

public interface AppointmentRepositoryOutputPort {

	Optional<Appointment> getAppointment(@Valid String id);

	Page<Appointment> getAppointmentsByDoctorDocument(@Valid String document, Pageable pageable);

	Page<Appointment> getAppointmentsByPatientDocument(@Valid String document, Pageable pageable);

	Page<Appointment> getAllAppointments(@Valid Pageable pageable);

	String postAppointment(@Valid Appointment appointment);

	void modifyAppointment(@Valid Appointment appointment);

	void deleteAppointment(@Valid String idAppointment);
}