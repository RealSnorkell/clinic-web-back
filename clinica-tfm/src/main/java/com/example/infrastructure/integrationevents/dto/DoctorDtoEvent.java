package com.example.infrastructure.integrationevents.dto;

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
public class DoctorDtoEvent extends PersonalInformationDtoEvent {
	String id;
	String licenseNum;
	LocalDate mIRDate;
	List<String> specializations;
}
