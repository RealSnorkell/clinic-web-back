package com.example.infrastructure.repository.mongodb.service.doctor_entity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.infrastructure.repository.mongodb.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, String> {

	Page<DoctorEntity> findByDeleted(Pageable pageable, boolean deleted);

	Optional<DoctorEntity> findByIdAndDeleted(String id, boolean deleted);

	Optional<DoctorEntity> findByPersonalInformationEntityDocumentAndDeleted(String document, boolean deleted);
}