# Trabajo Práctico 3 - Spring Boot, JPA y Lombok

Este repositorio contiene la entrega del TP3. El proyecto está conectado a una base de datos PostgreSQL alojada en la nube (Supabase) mediante un Connection Pooler.

## Tecnologías usadas
* Java + Spring Boot
* Spring Data JPA + Hibernate
* Lombok
* iText (Generación de PDF)
* Supabase (PostgreSQL en la nube)

## Instrucciones

El proyecto ya tiene las credenciales de la base de datos en la nube. Para probarlo sin necesidad de configuración local, hacer lo siguiente:

1. **Poblar la base de datos:** Ejecutar el archivo `src/main/java/tp/Main.java`. Este script JPA se conectará a Supabase e insertará una factura de prueba con sus detalles y relaciones.
2. **Generar Reportes:** Ejecutar el archivo `src/main/java/tp/MainReportes.java`. Esto ejecutará la consulta JPQL mediante un DTO y generará el PDF y el archivo de texto separados por tabulaciones en la raíz del proyecto.
3. **Levantar la API REST:** Ejecutar `src/main/java/tp/Application.java` para inicializar el servidor Spring Boot.
4. **Probar el endpoint:** Abrir el navegador o Postman y acceder a `http://localhost:8080/api/facturas`. Se puede probar los siguientes filtros:
   * `?estado=PAGADA`
   * `?montoMinimo=20000`
   * `?fechaDesde=2026-10-01&fechaHasta=2026-10-31`
