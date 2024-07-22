package com.example.infrastructure.integrationevents.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class ConsumerConfiguration {

//	@Value("${spring.kafka.host}")
//	private String bootstrapAdress;

	@Bean
	ConsumerFactory<String, Object> messageConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		// props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAdress);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		JsonDeserializer<Object> deserializer = new JsonDeserializer<>();
		deserializer.trustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(messageConsumerFactory());
		return factory;

	}
}