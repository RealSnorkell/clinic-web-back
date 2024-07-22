package com.example.infrastructure.apirest.mapper.doctor_object;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Doctor;
import com.example.infrastructure.apirest.dto.request.doctor_object.PostPutDoctorDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorToPostPutDoctorDtoMapper extends BaseMapper<Doctor, PostPutDoctorDto> {

}