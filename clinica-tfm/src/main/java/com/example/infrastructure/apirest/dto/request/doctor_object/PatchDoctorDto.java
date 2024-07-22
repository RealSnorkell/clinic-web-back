package com.example.infrastructure.apirest.dto.request.doctor_object;

import java.time.LocalDate;
import java.util.List;

import com.example.infrastructure.apirest.dto.common.PersonalInformationDto;
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
public class PatchDoctorDto extends PersonalInformationDto {
	String id;
	String licenseNum;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDate mIRDate;
	List<String> specializations;
	List<String> idDoctorAppointments;
}
