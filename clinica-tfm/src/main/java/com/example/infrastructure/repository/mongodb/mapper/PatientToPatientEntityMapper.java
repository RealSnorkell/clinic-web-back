package com.example.infrastructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Patient;
import com.example.infrastructure.repository.mongodb.entity.PatientEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientToPatientEntityMapper extends BaseMapper<Patient, PatientEntity> {

}
