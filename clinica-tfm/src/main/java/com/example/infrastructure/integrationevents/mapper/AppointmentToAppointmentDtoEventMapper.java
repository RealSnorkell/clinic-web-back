package com.example.infrastructure.integrationevents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.infrastructure.integrationevents.dto.AppointmentDtoEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToAppointmentDtoEventMapper extends BaseMapper<Appointment, AppointmentDtoEvent> {

}