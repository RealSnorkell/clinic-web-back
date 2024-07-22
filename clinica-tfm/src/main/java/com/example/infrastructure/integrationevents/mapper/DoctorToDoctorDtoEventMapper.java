package com.example.infrastructure.integrationevents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Doctor;
import com.example.infrastructure.integrationevents.dto.DoctorDtoEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorToDoctorDtoEventMapper extends BaseMapper<Doctor, DoctorDtoEvent> {

}