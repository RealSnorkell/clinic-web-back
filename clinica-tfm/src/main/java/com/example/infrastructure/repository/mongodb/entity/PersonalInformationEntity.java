package com.example.infrastructure.repository.mongodb.entity;

import java.time.LocalDate;

import com.example.domain.model.IdDocument;

import lombok.Data;

@Data
public class PersonalInformationEntity {
	String name;
	String surname;
	LocalDate birthDate;
	IdDocument idDocument;
	String idDocContent;
}