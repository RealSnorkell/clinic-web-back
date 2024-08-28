package com.example.infrastructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Patient;
import com.example.infrastructure.repository.mongodb.entity.PatientEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PatientToPatientEntityMapper extends BaseMapper<Patient, PatientEntity> {
	@Mapping(source = "personalInformationEntity", target = "personalInformation")
	Patient fromOutputToInput(PatientEntity input);

	@Mapping(source = "personalInformation", target = "personalInformationEntity")
	PatientEntity fromInputToOutput(Patient input);
}