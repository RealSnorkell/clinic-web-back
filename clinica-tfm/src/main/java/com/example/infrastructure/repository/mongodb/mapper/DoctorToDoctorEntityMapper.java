package com.example.infrastructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Doctor;
import com.example.infrastructure.repository.mongodb.entity.DoctorEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorToDoctorEntityMapper extends BaseMapper<Doctor, DoctorEntity> {
	@Mapping(source = "personalInformationEntity", target = "personalInformation")
	Doctor fromOutputToInput(DoctorEntity input);

	@Mapping(source = "personalInformation", target = "personalInformationEntity")
	DoctorEntity fromInputToOutput(Doctor input);
}
