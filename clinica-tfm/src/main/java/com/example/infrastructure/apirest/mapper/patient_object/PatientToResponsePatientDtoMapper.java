package com.example.infrastructure.apirest.mapper.patient_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.response.ResponsePatientDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientToResponsePatientDtoMapper extends BaseMapper<Patient, ResponsePatientDto> {
	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Patient fromOutputToInput(ResponsePatientDto input);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	ResponsePatientDto fromInputToOutput(Patient input);
}