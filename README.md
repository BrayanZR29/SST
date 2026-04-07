# SST - Sistema de Gestión de Seguridad y Salud en el Trabajo

## Descripción

SST es una aplicación web desarrollada con Spring Boot para la gestión de seguridad y salud en el trabajo. Permite registrar eventos, realizar investigaciones, gestionar acciones correctivas y administrar usuarios con diferentes roles.

## Características

- **Gestión de Usuarios**: Registro y autenticación de usuarios con roles (Admin, Responsable SST, Jefe de Área, Trabajador).
- **Registro de Eventos**: Creación y gestión de eventos relacionados con seguridad.
- **Investigaciones**: Realización de investigaciones sobre eventos, incluyendo causas inmediatas, básicas y factores contribuyentes.
- **Acciones Correctivas**: Asignación y seguimiento de acciones correctivas con estados (Pendiente, Completada).
- **Interfaz Web**: Interfaz de usuario basada en Thymeleaf con plantillas responsivas.
- **Seguridad**: Autenticación y autorización usando Spring Security.
- **Base de Datos**: Soporte para MySQL y H2 (para desarrollo).

## Tecnologías Utilizadas

- **Backend**: Java 17, Spring Boot 3.2.0
- **Base de Datos**: MySQL 9.0 (producción), H2 (desarrollo)
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Seguridad**: Spring Security
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Otros**: Lombok, Validation, iText (para reportes PDF)

## Estructura del Proyecto

```
SST/
├── planeacion/          # Documentos de planificación y diseño
│   ├── 01-propuesta-software.md
│   ├── 02-diagrama-flujo.md
│   ├── 03-diseno-software.md
│   ├── 04-estructura-codigo.md
│   ├── diseno-web/      # Diseño web estático
│   └── uml/             # Diagramas UML
├── src/
│   ├── main/
│   │   ├── java/com/sst/registro/
│   │   │   ├── config/      # Configuraciones de Spring
│   │   │   ├── controller/  # Controladores REST y web
│   │   │   ├── model/       # Entidades JPA y DTOs
│   │   │   │   ├── entity/  # Entidades: Usuario, Evento, Investigacion, AccionCorrectiva
│   │   │   │   └── enums/   # Enums: RolUsuario, EstadoAccion
│   │   │   ├── repository/  # Repositorios JPA
│   │   │   ├── service/     # Servicios de negocio
│   │   │   └── util/        # Utilidades
│   │   └── resources/
│   │       ├── application.properties  # Configuración
│   │       ├── static/     # CSS, JS, imágenes
│   │       └── templates/  # Plantillas Thymeleaf
│   └── test/               # Pruebas unitarias e integración
├── pom.xml                # Configuración Maven
├── SST.iml                # Archivo IntelliJ
└── target/                # Archivos compilados
```

## Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+ (para producción) o H2 (para desarrollo)

### Configuración de la Base de Datos

#### Opción 1: Usar H2 (Recomendado para desarrollo)

H2 está configurado por defecto. No requiere instalación adicional.

- La base de datos se crea en memoria.
- Accede a la consola H2 en: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:sgsst`
  - Usuario: `sa`
  - Contraseña: (vacía)

#### Opción 2: Usar MySQL

1. Instala MySQL Server.
2. Crea la base de datos `sgsst` (opcional, se crea automáticamente).
3. Configura el usuario root con contraseña 'root'.

##### Configuración de Contraseña Root en MySQL

###### Windows

1. Abre la línea de comandos como administrador (busca "cmd", haz clic derecho y "Ejecutar como administrador").
2. Detén el servicio MySQL:  
   `sc stop MySQL90`
3. Inicia MySQL sin autenticación:  
   `"C:\Program Files\MySQL\MySQL Server 9.0\bin\mysqld.exe" --skip-grant-tables --user=mysql`
4. En otra ventana de cmd (como administrador), conecta:  
   `"C:\Program Files\MySQL\MySQL Server 9.0\bin\mysql.exe" -u root`
5. Dentro de MySQL, ejecuta:  
   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
   FLUSH PRIVILEGES;
   EXIT;
   ```
6. Detén el proceso mysqld (presiona Ctrl+C en la ventana donde lo iniciaste).
7. Inicia el servicio:  
   `sc start MySQL90`

###### Linux

1. Detén el servicio MySQL:  
   `sudo systemctl stop mysql`
2. Inicia MySQL en modo seguro:  
   `sudo mysqld_safe --skip-grant-tables --user=mysql &`
3. Conecta sin contraseña:  
   `mysql -u root`
4. Dentro de MySQL, ejecuta:  
   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
   FLUSH PRIVILEGES;
   EXIT;
   ```
5. Detén el proceso mysqld_safe (encuéntralo con `ps aux | grep mysqld` y mata con `kill`).
6. Inicia el servicio:  
   `sudo systemctl start mysql`

### Compilación y Ejecución

1. Clona el repositorio:  
   `git clone <url-del-repositorio>`

2. Navega al directorio del proyecto:  
   `cd SST`

3. Compila el proyecto:  
   `mvn clean compile`

4. Ejecuta la aplicación:  
   `mvn spring-boot:run`

5. Accede a la aplicación en: `http://localhost:8080`

### Configuración de Seguridad

Por defecto, la aplicación usa Spring Security con un usuario básico:
- Usuario: `admin`
- Contraseña: `admin123`

Para producción, configura usuarios reales en la base de datos.

## API Endpoints

La aplicación incluye endpoints REST para gestión de datos. Algunos ejemplos:

- `GET /api/usuarios` - Lista de usuarios
- `POST /api/eventos` - Crear evento
- `GET /api/investigaciones` - Lista de investigaciones

## Contribución

1. Fork el proyecto.
2. Crea una rama para tu feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit tus cambios: `git commit -am 'Agrega nueva funcionalidad'`
4. Push a la rama: `git push origin feature/nueva-funcionalidad`
5. Crea un Pull Request.

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo LICENSE para más detalles.

## Contacto

Para preguntas o soporte, contacta al equipo de desarrollo.</content>
<parameter name="filePath">C:\Users\ESTUDIANTES\IdeaProjects\SST\README.md
