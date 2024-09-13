package com.example.infrastructure.apirest.mapper.appointment_object;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.domain.mapper.BaseMapper;
import com.example.domain.model.Appointment;
import com.example.domain.model.Doctor;
import com.example.domain.model.Patient;
import com.example.infrastructure.apirest.dto.request.appointment_object.PatchAppointmentDto;
import com.example.infrastructure.apirest.dto.request.doctor_object.PatchDoctorDto;
import com.example.infrastructure.apirest.dto.request.patient_object.PatchPatientDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToPatchAppointmentDtoMapper extends BaseMapper<Appointment, PatchAppointmentDto> {
	@Mapping(source = "personalInformation", target = "personalInformationDto")
	PatchDoctorDto doctorToResponseDoctorDto(Doctor doctor);

	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Doctor doctorToResponseDoctorDto(PatchDoctorDto doctor);

	@Mapping(source = "personalInformation", target = "personalInformationDto")
	PatchPatientDto patientToResponsePatientDto(Patient patient);

	@Mapping(source = "personalInformationDto", target = "personalInformation")
	Patient doctorToResponseDoctorDto(PatchPatientDto patient);
}