package com.example.infrastructure.apirest.dto.common;

import java.time.LocalDate;

import com.example.domain.model.IdDocument;

import lombok.Data;

@Data
public class PersonalInformationDto {
	String name;
	String surname;
	LocalDate birthDate;
	IdDocument idDocument;
	String idDocContent;
}