# Gestión de Pólizas - Seguros 

API REST para la gestión de pólizas de seguros, desarrollado en Java Spring Boot.

## Tecnologías Utilizadas
* Java 25
* Spring Boot 4.1.1 (Web, Data JPA)
* Base de datos H2 (En memoria)

## Instrucciones de Ejecución
1. Clonar el repositorio.
2. Ejecutar el comando `./mvnw spring-boot:run` (Mac/Linux) o `.\mvnw.cmd spring-boot:run` (Windows).
3. La aplicación se iniciará en el puerto `8080`.

## Seguridad
Todos los endpoints están protegidos. Se debe incluir el siguiente Header en cada petición HTTP:
`x-api-key: 123456`

## Consola H2
Se puede acceder a la base de datos en: `http://localhost:8080/h2-console`. Como es local la BD no hay problema de seguridad con las credenciales expuestas.
* JDBC URL: `jdbc:h2:mem:polizasdb`
* Usuario: `sa`
* Contraseña: `password`

## Ejemplos de Uso (Payloads)

**1. Crear una Póliza Individual (POST `/api/polizas`)**
Recuerda enviar el header `x-api-key: 123456`.
```json
{
  "tipoPoliza": "INDIVIDUAL",
  "valorPrimaTotal": 1500.0,
  "valorCanonTotal": 150.0,
  "riesgos": [
    {
      "descripcion": "Seguro de Auto",
      "valorAsegurado": 45000.0
    }
  ]
}
