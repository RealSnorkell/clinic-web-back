# Getting Started

## Clínica médica.

Este proyecto se ha creado con la finalidad de proporcionar una aplicación web con la que poder gestionar diversos elementos que pueden englobar la lógica de negocio de algunas empresas, siendo en este caso la gestión de 'Citas', 'Pacientes' y 'Doctores'. La aplicación permite actuar como un gestor de los elementos previamente mencionados, pudiendo así crear, obtener, modificar o eliminar dichos elementos.

**Características Principales:**

•	**Gestión de doctores:** Registro de doctores. El gestor puede crear perfiles proporcionando información a la que tendrá acceso como nombre, documento (DNI, NIE o Pasaporte), número de licencia o especializaciones.

•	**Gestión de Citas:** El gestor podra crear, obtener, modificar y eliminar estos elementos. Los pacientes y doctores pueden solicitar citas, por lo que con la información apropiada el gestor tendrá que crearlas.

•	**Gestión de pacientes:** Registro de pacientes. El gestor puede crear perfiles proporcionando información a la que tendrá acceso como nombre, documento (DNI, NIE o Pasaporte), número de la seguridad social, altura o peso.

## Tecnologías utilizadas.

_Framework: Spring Boot 4.22.0_

_Lenguaje: Java 17_

_BBDD: MongoDB_

### EndPoints.

Patients

	Post/patients: Crear un nuevo paciente.
	Get/patients: Obtener todos los pacientes.
	Get/patients/{id}: Obtener un paciente via ID.
	Get/patients/list/{document}: Obtener un paciente via documento.
	Put/patients/{id}: Modificar completamente un paciente via ID.
	Patch/patients/{id}: Modificar un paciente via ID.
	Delete/patients/{id}: Eliminar un paciente via ID.

Doctors

	Post/doctors: Crear un nuevo doctor.
	Get/doctors: Obtener todos los doctores.
	Get/doctors/{id}: Obtener un doctor via ID.
	Get/doctors/list/{document}: Obtener un doctor via documento.
	Put/doctors/{id}: Modificar completamente un doctor via ID.
	Patch/doctors/{id}: Modificar un doctor via ID.
	Delete/doctors/{id}: Eliminar un doctor via ID.

Appointments

	Post/appointments: Crear una nueva cita.
	Get/appointments: Obtener todas las citas.
	Get/appointments/{id}: Obtener una cita via ID.
	Get/appointments/patients/{document}: Obtener las citas de un paciente via documento. 
	Get/appointments /doctors/{document}: Obtener las citas de un doctor via documento.
	Put/appointments/{id}: Modificar completamente una cita via ID.
	Patch/appointments/{id}: Modificar una cita via ID.
	Delete/appointments/{id}: Eliminar una cita via ID.
