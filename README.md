# ForoHub API

**ForoHub** es una API que permite la autenticación de usuarios, la gestión de tópicos y el registro de nuevos usuarios. A continuación, se detallan los endpoints disponibles y su funcionalidad.

---

## 🛠 Tecnologías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de la API.
- **Hibernate**: ORM para la interacción con la base de datos.
- **JWT**: Para la autenticación y autorización de usuarios.
- **Validation API**: Para la validación de datos.
- **Lombok**: Simplifica la generación de código repetitivo, como getters, setters y constructores.
- **FlywayDB**: Herramienta de migración de base de datos para manejar versiones del esquema de forma automática.
- **MySQL**: Sistema de gestión de bases de datos relacional utilizado para almacenar datos.

---

## 🔐 Autenticación

### **POST /login**

Autentica a un usuario y genera un token JWT.

**Request Body**:
```json
{
  "userName": "string",
  "clave": "string"
}
Responses:

200 OK: Devuelve el token JWT generado.
json
Copiar
Editar
{
  "token": "string"
}
401 Unauthorized: Credenciales inválidas.
json
Copiar
Editar
"Credenciales inválidas"
📝 Gestión de Tópicos
POST /topicos
Crea un nuevo tópico.

Request Body:

json
Copiar
Editar
{
  "titulo": "string",
  "mensaje": "string",
  "curso": "string"
}
Responses:

200 OK: Tópico creado con éxito.
json
Copiar
Editar
"Tópico creado con éxito"
409 Conflict: Ya existe un tópico con el mismo título y mensaje.
json
Copiar
Editar
"Ya existe un tópico con el mismo título y mensaje"
GET /topicos
Obtiene una lista paginada de tópicos activos.

Query Parameters:

size (opcional): Tamaño de la página (default: 2).
page (opcional): Número de la página (default: 0).
Responses:

200 OK: Lista de tópicos.
GET /topicos/{id}
Obtiene un tópico por su ID.

Responses:

200 OK: Detalles del tópico.
json
Copiar
Editar
{
  "id": "number",
  "titulo": "string",
  "mensaje": "string",
  "fechaDeCreacion": "string",
  "status": "boolean",
  "autor": {
    "userName": "string"
  },
  "curso": "string"
}
PUT /topicos
Actualiza un tópico existente.

Request Body:

json
Copiar
Editar
{
  "id": "number",
  "titulo": "string",
  "mensaje": "string",
  "curso": "string"
}
Responses:

200 OK: Tópico actualizado con éxito.
json
Copiar
Editar
{
  "id": "number",
  "titulo": "string",
  "mensaje": "string",
  "fechaDeCreacion": "string",
  "status": "boolean",
  "autor": {
    "userName": "string"
  },
  "curso": "string"
}
400 Bad Request: No se puede actualizar un tópico inactivo.
json
Copiar
Editar
"No se puede actualizar un tópico inactivo"
DELETE /topicos/{id}
Desactiva un tópico por su ID.

Responses:

204 No Content: Tópico desactivado con éxito.
PATCH /topicos/{id}/activar
Activa un tópico previamente desactivado.

Responses:

200 OK: Tópico activado con éxito.
👥 Gestión de Usuarios
POST /users
Crea un nuevo usuario.

Request Body:

json
Copiar
Editar
{
  "userName": "string",
  "clave": "string"
}
Responses:

200 OK: Usuario creado con éxito.
json
Copiar
Editar
"Usuario creado con éxito"
400 Bad Request: El nombre de usuario ya está registrado.
json
Copiar
Editar
"El nombre de usuario ya está registrado"
🔧 Configuración de Variables de Entorno
La aplicación utiliza varias variables de entorno para gestionar su configuración. Es importante establecer estas variables antes de ejecutar la aplicación para garantizar su correcto funcionamiento.

Variables de Entorno Requeridas
Base de Datos
DB_URL: URL de conexión a la base de datos.
Valor por defecto: jdbc:mysql://localhost/mysqlDB
Ejemplo: jdbc:mysql://localhost:3306/nombreDB

DB_USERNAME: Usuario para la conexión a la base de datos.

DB_PASSWORD: Contraseña del usuario para la conexión a la base de datos.

Seguridad
JWT_SECRET: Clave secreta utilizada para firmar y verificar tokens JWT.
JWT_EXPIRATION_TIME: Tiempo de expiración de los tokens JWT en horas.
Ejemplo: 2
Migraciones de Base de Datos
SPRING_FLYWAY_ENABLED: Define si Flyway debe estar habilitado para migraciones automáticas de la base de datos.
Valor por defecto: true

SPRING_FLYWAY_LOCATIONS: Ubicación de los scripts de migración.
Valor por defecto: classpath:db/migration

⚙️ Notas Adicionales
La API utiliza autenticación basada en tokens JWT. Los tokens deben incluirse en el encabezado Authorization de las solicitudes protegidas:
Authorization: Bearer <token>

Asegúrate de validar los datos enviados al servidor para evitar errores de validación.

Los objetos DTO se utilizan para estructurar y validar los datos de entrada y salida.

