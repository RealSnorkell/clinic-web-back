package com.example.infrastructure.apirest.mapper.patient_object;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.request.patient_object.PatchPatientDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientToPatchPatientDto extends BaseMapper<Patient, PatchPatientDto> {

}