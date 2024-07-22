package com.example.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domain.model.Patient;

import jakarta.validation.Valid;

public interface PatientRepositoryOutputPort {

	Optional<Patient> getPatient(@Valid String id);

	Optional<Patient> getPatientByDocument(@Valid String document);

	Page<Patient> getAllPatients(@Valid Pageable pageable);

	String postPatient(@Valid Patient inputPatient);

	void modifyPatient(@Valid Patient inputDoc);

	void deletePatient(@Valid String idPatient);
}
