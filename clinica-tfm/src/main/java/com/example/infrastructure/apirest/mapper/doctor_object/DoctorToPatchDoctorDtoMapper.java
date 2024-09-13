package com.example.infrastructure.apirest.mapper.doctor_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Doctor;
import com.example.infrastructure.apirest.dto.request.doctor_object.PatchDoctorDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorToPatchDoctorDtoMapper extends BaseMapper<Doctor, PatchDoctorDto> {
	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Doctor fromOutputToInput(PatchDoctorDto input);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	PatchDoctorDto fromInputToOutput(Doctor input);
}