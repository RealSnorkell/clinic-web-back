package com.example.domain.model;

import java.util.List;

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
public class Patient extends PersonalInformation {
	String id;
	String sSNumber;
	double height;
	double weight;
	List<String> idPatientAppointments;
}