package com.example.infrastructure.repository.mongodb.service.appointment_entity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.infrastructure.repository.mongodb.entity.AppointmentEntity;

import jakarta.validation.Valid;

@Repository
public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {

	Optional<AppointmentEntity> findByAppointmentIdAndDeleted(@Valid String id, boolean deleted);

	Page<AppointmentEntity> findByDeleted(@Valid Pageable pageable, boolean deleted);

	Page<AppointmentEntity> findByDoctorPersonalInformationDocumentAndDeleted(String document, boolean deleted,
			Pageable pageable);

	Page<AppointmentEntity> findByPatientPersonalInformationDocumentAndDeleted(String document, boolean deleted,
			Pageable pageable);
}
