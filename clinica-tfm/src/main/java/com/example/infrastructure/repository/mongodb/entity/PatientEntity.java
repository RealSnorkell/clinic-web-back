package com.example.infrastructure.repository.mongodb.entity;

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
@Document(collection = "PATIENTS")
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
	@Id
	String id;
	String sSNumber;
	PersonalInformationEntity personalInformationEntity;
	double height;
	double weight;
	List<String> idPatientAppointments;
	boolean deleted;
}
