package com.example.infrastructure.integrationevents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.application.port.output.ClinicProducerOutputPort;
import com.example.domain.model.Doctor;
import com.example.infrastructure.integrationevents.producer.ClinicProducerEvent;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClinicProducerService implements ClinicProducerOutputPort {

	// Doctor Topics.
	@Value("${custom.topic.doctor.posted}")
	private String topicPostedDoctor;

	@Value("${custom.topic.doctor.modified}")
	private String topicModifiedDoctor;

	@Value("${custom.topic.doctor.deleted}")
	private String topicDeletedDoctor;

	// Patient Topics.
	@Value("${custom.topic.patient.posted}")
	private String topicPostedPatient;

	@Value("${custom.topic.patient.modified}")
	private String topicModifiedPatient;

	@Value("${custom.topic.patient.deleted}")
	private String topicDeletedPatient;

	@Autowired
	ClinicProducerEvent clinicProducerEvent;

	@Override
	public void createdClinicEvent(@Valid Object input) {
		log.debug("New " + input.toString() + " created.");

		if (input instanceof Doctor) {
			clinicProducerEvent.sendMessageAssync(topicPostedDoctor, input);
			return;
		}

		clinicProducerEvent.sendMessageAssync(topicPostedPatient, input);
	}

	@Override
	public void modifiedClinicEvent(@Valid Object updated) {
		log.debug(updated.toString() + " has been modified.");

		if (updated instanceof Doctor) {
			clinicProducerEvent.sendMessageAssync(topicModifiedDoctor, updated);
			return;
		}

		clinicProducerEvent.sendMessageAssync(topicModifiedPatient, updated);
	}

	@Override
	public void deletedClinicEvent(@Valid Object deleted) {
		log.debug(deleted.toString() + " has been deleted.");

		if (deleted instanceof Doctor) {
			clinicProducerEvent.sendMessageAssync(topicDeletedDoctor, deleted);
			return;
		}

		clinicProducerEvent.sendMessageAssync(topicDeletedPatient, deleted);
	}
}