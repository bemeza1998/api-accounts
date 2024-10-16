# Microservicio api-accounts

La siguiente codificación corresponde al microservicio orientado al manejo de cuentas y sus respectivos movimientos.

## Estructura del proyecto

Para un mejor manejo de las clases, la implementación se realizó haciendo uso de una arquitectura en capas, donde se tiene lo siguiente:

- config: Archivos de configuración general para el proyecto
- dao: Definición de repositorios para la interacción con la base de datos
- dto: Clases auxiliares para el intercambio de datos
- enums: Definición de estados en una clase
- exception: Excepciones lanzadas al no cumplir ciertas validaciones
- mapper: Clase auxiliar para convertir un objeto de un tipo a otro
- model: Definición de clases que mapean las entidades de la base de datos
- resource: Definición de endpoints
- service: Definición de lógica del negocio

## Archivos extras

- API CLIENTES.postman_collection.json: colección de Postman para probar los endpoints
- BaseDatos.sql: DDL de la base de datos utilizada