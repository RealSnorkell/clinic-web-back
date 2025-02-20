package com.example.infrastructure.integrationevents.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class DoctorDtoEvent {
	String id;
	String licenseNum;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDate mIRDate;
	PersonalInformationDtoEvent personalInformationDtoEvent;
	List<String> specializations;
	List<String> idDoctorAppointments;
}
