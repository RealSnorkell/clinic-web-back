package com.example.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.domain.model.Appointment;
import com.example.domain.model.Doctor;
import com.example.domain.model.Patient;

@Mapper(componentModel = "spring")
public interface ClinicPatchMapper {
	void updateDoctor(@MappingTarget Doctor output, Doctor input);

	void updatePatient(@MappingTarget Patient output, Patient input);

	void updateAppointment(@MappingTarget Appointment output, Appointment input);
}