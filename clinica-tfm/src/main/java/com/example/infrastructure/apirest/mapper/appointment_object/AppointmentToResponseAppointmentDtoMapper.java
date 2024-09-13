package com.example.infrastructure.apirest.mapper.appointment_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.domain.model.Doctor;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.response.ResponseAppointmentDto;
import com.example.infrastructure.apirest.dto.response.ResponseDoctorDto;
import com.example.infrastructure.apirest.dto.response.ResponsePatientDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToResponseAppointmentDtoMapper extends BaseMapper<Appointment, ResponseAppointmentDto> {
	@Mapping(source = "personalInformation", target = "personalInformationDto")
	ResponseDoctorDto doctorToResponseDoctorDto(Doctor doctor);

	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Doctor doctorToResponseDoctorDto(ResponseDoctorDto doctor);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	ResponsePatientDto patientToResponsePatientDto(Patient patient);

	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Patient doctorToResponseDoctorDto(ResponsePatientDto patient);
}