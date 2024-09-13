package com.example.infrastructure.apirest.dto.request.doctor_object;

import java.time.LocalDate;
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
public class PostPutDoctorDto {
	String licenseNum;
	LocalDate mirDate;
	PersonalInformationDto personalInformationDto;
	List<String> specializations;
	List<String> idDoctorAppointments;
}