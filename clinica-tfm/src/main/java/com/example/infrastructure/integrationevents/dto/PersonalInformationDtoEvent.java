package com.example.infrastructure.integrationevents.dto;

import java.time.LocalDate;

import com.example.domain.model.IdDocument;

import lombok.Data;

@Data
public class PersonalInformationDtoEvent {
	String name;
	String firstSurname;
	String secondSurname;
	LocalDate birthDate;
	IdDocument idDocument;
	String idDocContent;
}