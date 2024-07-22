package com.example.application.port.output;

import jakarta.validation.Valid;

public interface ClinicProducerOutputPort {

	void createdClinicEvent(@Valid Object input);

	void modifiedClinicEvent(@Valid Object updated);

	void deletedClinicEvent(@Valid Object deleted);
}