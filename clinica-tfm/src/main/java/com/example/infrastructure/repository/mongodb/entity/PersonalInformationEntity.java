package com.example.infrastructure.repository.mongodb.entity;

import com.example.domain.model.IdDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformationEntity {
	String name;
	String surname;
	IdDocument idDocument;
	String document;
}