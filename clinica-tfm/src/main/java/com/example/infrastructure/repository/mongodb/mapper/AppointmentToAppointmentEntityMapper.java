package com.example.infrastructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.infrastructure.repository.mongodb.entity.AppointmentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToAppointmentEntityMapper extends BaseMapper<Appointment, AppointmentEntity> {

}
