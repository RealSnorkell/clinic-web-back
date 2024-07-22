package com.example.infrastructure.repository.mongodb.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "APPOINTMENTS")
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
	@Id
	String appointmentId;
	Doctor doctor;
	Patient patient;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime date;
	String diagnostic;
	String treatment;
	boolean deleted;
}