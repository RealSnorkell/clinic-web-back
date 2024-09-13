package com.example.infrastructure.apirest.mapper.doctor_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Doctor;
import com.example.infrastructure.apirest.dto.response.ResponseDoctorDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorToResponseDoctorDtoMapper extends BaseMapper<Doctor, ResponseDoctorDto> {
	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Doctor fromOutputToInput(ResponseDoctorDto input);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	ResponseDoctorDto fromInputToOutput(Doctor input);
}