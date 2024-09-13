package com.example.infrastructure.repository.mongodb.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class DoctorEntity {
	@Id
	String id;
	String licenseNum;
	LocalDate mirDate;
	PersonalInformationEntity personalInformationEntity;
	List<String> specializations;
	List<String> idDoctorAppointments;
	boolean deleted;
}