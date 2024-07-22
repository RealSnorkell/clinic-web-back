package com.example.infrastructure.repository.mongodb.service.patient_entity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.infrastructure.repository.mongodb.entity.PatientEntity;

@Repository
public interface PatientRepository extends MongoRepository<PatientEntity, String> {

	Page<PatientEntity> findByDeleted(Pageable pageable, boolean deleted);

	Optional<PatientEntity> findByIdAndDeleted(String id, boolean deleted);

	Optional<PatientEntity> findByPersonalInformationEntityDocumentAndDeleted(String document, boolean deleted);
}