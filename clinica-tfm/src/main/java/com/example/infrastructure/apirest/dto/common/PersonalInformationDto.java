package com.example.infrastructure.apirest.dto.common;

import com.example.domain.model.IdDocument;

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
	IdDocument idDocument;
	String document;
}