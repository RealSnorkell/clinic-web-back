package com.example.infrastructure.integrationevents.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClinicProducerEvent {
	@Value("${spring.kafka.timeout:6000}")
	private long kafkaTimeout;

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMessageAssync(String topicName, Object msg) {
		kafkaTemplate.send(topicName, msg);
	}
}