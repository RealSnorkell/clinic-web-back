package com.example.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonalInformation {
	String name;
	String surname;
	LocalDate birthDate;
	IdDocument idDocument;
	String idTypeContent;
}