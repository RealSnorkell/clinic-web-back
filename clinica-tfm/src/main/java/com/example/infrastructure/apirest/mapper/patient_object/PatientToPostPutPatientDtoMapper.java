package com.example.infrastructure.apirest.mapper.patient_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.request.patient_object.PostPutPatientDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PatientToPostPutPatientDtoMapper extends BaseMapper<Patient, PostPutPatientDto> {
	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Patient fromOutputToInput(PostPutPatientDto input);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	PostPutPatientDto fromInputToOutput(Patient input);
}