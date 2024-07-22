package com.example.infrastructure.apirest.dto.common;

import java.time.LocalDate;

import com.example.domain.model.IdDocument;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformationDto {
	String name;
	String surname;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDate birthDate;
	IdDocument idDocument;
	String document;
}