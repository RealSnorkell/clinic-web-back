package com.example.infrastructure.repository.mongodb.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Document(collection = "DOCTORS")
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity extends PersonalInformationEntity {
	@Id
	String id;
	String licenseNum;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDate mIRDate;
	List<String> specializations;
	List<String> idDoctorAppointments;
	boolean deleted;
}