package com.example.infrastructure.integrationevents.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.port.input.AppointmentServiceInputPort;
import com.example.application.port.input.DoctorServiceInputPort;
import com.example.application.port.input.PatientServiceInputPort;
import com.example.infrastructure.integrationevents.mapper.AppointmentToAppointmentDtoEventMapper;
import com.example.infrastructure.integrationevents.mapper.DoctorToDoctorDtoEventMapper;
import com.example.infrastructure.integrationevents.mapper.PatientToPatientDtoEventMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClinicaConsumerService {

	@Autowired
	AppointmentServiceInputPort appointmentServiceInputPort;

	@Autowired
	DoctorServiceInputPort doctorServiceInputPort;

	@Autowired
	PatientServiceInputPort patientServiceInputPort;

	@Autowired
	AppointmentToAppointmentDtoEventMapper appointmentToAppointmentDtoEventMapper;

	@Autowired
	DoctorToDoctorDtoEventMapper doctorToDoctorDtoEventMapper;

	@Autowired
	PatientToPatientDtoEventMapper patientToPatientDtoEventMapper;
}