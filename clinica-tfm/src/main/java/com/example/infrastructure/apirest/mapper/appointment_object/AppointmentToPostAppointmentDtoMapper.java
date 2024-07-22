package com.example.infrastructure.apirest.mapper.appointment_object;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.infrastructure.apirest.dto.request.appointment_object.PostAppointmentDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToPostAppointmentDtoMapper extends BaseMapper<Appointment, PostAppointmentDto> {

}