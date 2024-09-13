package com.example.infrastructure.apirest.dto.request.appointment_object;

import java.time.LocalDateTime;

import com.example.infrastructure.apirest.dto.request.doctor_object.PatchDoctorDto;
import com.example.infrastructure.apirest.dto.request.patient_object.PatchPatientDto;
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
public class PatchAppointmentDto {
	String appointmentId;
	PatchDoctorDto doctor;
	PatchPatientDto patient;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime date;
	String diagnostic;
	String treatment;
}
