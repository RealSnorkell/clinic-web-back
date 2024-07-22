package com.example.infrastructure.integrationevents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.infrastructure.integrationevents.dto.DoctorDtoEvent;
import com.example.infrastructure.repository.mongodb.entity.DoctorEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorEntityToDoctorDtoEventMapper extends BaseMapper<DoctorEntity, DoctorDtoEvent> {

}