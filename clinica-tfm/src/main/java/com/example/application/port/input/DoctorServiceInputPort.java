package com.example.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.application.util.ClinicLogicException;
import com.example.domain.model.Doctor;

import jakarta.validation.Valid;

public interface DoctorServiceInputPort {

	Optional<Doctor> getDoctor(@Valid String id);

	Optional<Doctor> getDoctorByDocument(@Valid String document);

	Page<Doctor> getAllDoctors(@Valid Pageable pageable);

	String createDoctor(@Valid Doctor inputDoc);

	void partialModificationDoctor(@Valid Doctor inputDoc) throws ClinicLogicException;

	void totalModificationDoctor(@Valid Doctor inputDoc) throws ClinicLogicException;

	void deleteDoctor(@Valid String idDoc);
}