# Control de Pagos RedTel

Aplicación web para gestionar los clientes y los pagos de una empresa de internet. Permite iniciar sesión como administrador, registrar y editar clientes, consultar su velocidad y monto mensual, marcar pagos y ver estadísticas de clientes pagados y con deuda.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- HTML, CSS y JavaScript

## Ejecución

1. Crear la base de datos `controldepagos` en MySQL.
2. Revisar las credenciales de conexión en
	`src/main/resources/application.properties`.
3. Ejecutar el proyecto con:

	```bash
	./mvnw spring-boot:run
	```

	En Windows:

	```powershell
	.\mvnw.cmd spring-boot:run
	```

4. Abrir `http://localhost:8080` en el navegador.

## Autores

- Gabriel Altamirano
- Maximiliano Rojas
- Alvaro Montero
