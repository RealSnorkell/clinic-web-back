package com.example.infrastructure.apirest.dto.request.patient_object;

import java.util.List;

import com.example.domain.model.Appointment;
import com.example.infrastructure.apirest.dto.common.PersonalInformationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class PatchPatientDto {
	String sSNumber;
	PersonalInformationDto personalInformationDto;
	double height;
	double weight;
	List<Appointment> patientAppointments;
}
