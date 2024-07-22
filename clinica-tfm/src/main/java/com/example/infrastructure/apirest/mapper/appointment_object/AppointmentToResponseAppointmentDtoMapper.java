package com.example.infrastructure.apirest.mapper.appointment_object;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.infrastructure.apirest.dto.response.ResponseAppointmentDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToResponseAppointmentDtoMapper extends BaseMapper<Appointment, ResponseAppointmentDto> {

}