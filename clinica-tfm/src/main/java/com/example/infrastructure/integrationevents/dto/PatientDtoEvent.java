package com.example.infrastructure.integrationevents.dto;

import java.util.List;

import com.example.domain.model.Appointment;

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
public class PatientDtoEvent {
	String id;
	String sSNumber;
	PersonalInformationDtoEvent personalInformationDtoEvent;
	double height;
	double weight;
	List<Appointment> patientAppointments;
}
