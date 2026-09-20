# ApexFit - Backend

Backend en Spring Boot para el sistema de gestión de gimnasios ApexFit.

## Requisitos
- Java 17
- MySQL Server (Local o AWS RDS)

## Configuración y Ejecución

El proyecto utiliza variables de entorno para conectarse a la base de datos.
Por defecto, usa el perfil `dev` (`application-dev.yml`) que apunta a localhost.

### 1. Base de datos local
Asegúrate de tener un servidor MySQL corriendo localmente.
Crea una base de datos vacía llamada `apexfit`. (Flyway se encargará de crear las tablas).

### 2. Variables de entorno (Opcional si usas root/root)
Si tu MySQL local tiene un usuario/contraseña distinto a root/root, configura estas variables antes de iniciar:
- `DB_USER`
- `DB_PASS`
- `DB_NAME` (por defecto `apexfit`)

### 3. Ejecutar el proyecto
No necesitas instalar Maven, el proyecto incluye el wrapper `mvnw`.
Ejecuta en la terminal (estando dentro de la carpeta `backend`):
```bash
./mvnw spring-boot:run
```

## Pruebas (Swagger y Postman)
Una vez iniciado el proyecto, puedes probar los endpoints (GET, POST, PUT, DELETE) desde la interfaz gráfica de Swagger:
**http://localhost:8080/swagger-ui.html**

Endpoints implementados (con paginación y validaciones):
- `/api/v1/usuarios`
- `/api/v1/ejercicios`
- `/api/v1/rutinas`

## Despliegue con AWS RDS (Perfil Producción)
Para conectarte a la base de datos de AWS, debes iniciar la aplicación con el perfil `prod` y configurar las variables de entorno de AWS RDS.

Variables requeridas para producción:
- `APP_PROFILE=prod`
- `RDS_HOSTNAME`
- `RDS_PORT`
- `RDS_DB_NAME`
- `RDS_USERNAME`
- `RDS_PASSWORD`

Ejecución:
```bash
$env:APP_PROFILE="prod"
$env:RDS_HOSTNAME="tu-db.xxxx.us-east-1.rds.amazonaws.com"
$env:RDS_USERNAME="admin"
$env:RDS_PASSWORD="password"
./mvnw spring-boot:run
```
