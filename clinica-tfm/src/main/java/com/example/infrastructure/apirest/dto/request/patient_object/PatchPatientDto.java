package com.example.infrastructure.apirest.dto.request.patient_object;

import java.util.List;

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
	String id;
	String socialSecurityNumber;
	PersonalInformationDto personalInformationDto;
	double height;
	double weight;
	List<String> idPatientAppointments;
}
