package com.example.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domain.model.Doctor;

import jakarta.validation.Valid;

public interface DoctorRepositoryOutputPort {
	// Doctor logic related to service starts from here.

	Optional<Doctor> getDoctor(@Valid String id);

	Optional<Doctor> getDoctorByDocument(@Valid String document);

	Page<Doctor> getAllDoctors(@Valid Pageable pageable);

	String postDoctor(@Valid Doctor inputDoc);

	void modifyDoctor(@Valid Doctor inputDoc);

	void deleteDoctor(@Valid String idDoc);
}