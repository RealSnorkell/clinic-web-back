package com.example.domain.model;

import java.time.LocalDate;
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
public class Doctor {
	String id;
	String licenseNum;
	LocalDate mirDate;
	PersonalInformation personalInformation;
	List<String> specializations;
	List<String> idDoctorAppointments;
}