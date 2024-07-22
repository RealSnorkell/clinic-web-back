package com.example.infrastructure.apirest.dto.response;

import java.time.LocalDateTime;

import com.example.domain.model.Doctor;
import com.example.domain.model.Patient;
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
public class ResponseAppointmentDto {
	String appointmentId;
	Doctor doctor;
	Patient patient;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime date;
	String diagnostic;
	String treatment;
}
