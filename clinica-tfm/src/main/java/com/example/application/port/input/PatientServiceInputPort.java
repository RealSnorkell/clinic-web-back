package com.example.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Patient;

import jakarta.validation.Valid;

public interface PatientServiceInputPort {

	Optional<Patient> getPatient(@Valid String id);

	Optional<Patient> getPatientByDocument(@Valid String document);

	Page<Patient> getAllPatients(@Valid Pageable pageable);

	String createPatient(@Valid Patient inputPatient);

	void partialModificationPatient(@Valid Patient inputPatient) throws ClinicLogicException;

	void totalModificationPatient(@Valid Patient inputPatient) throws ClinicLogicException;

	void deletePatient(@Valid String idPatient);
}
