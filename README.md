# SST - Sistema de Gestión de Seguridad y Salud en el Trabajo

## Descripción

SST es una aplicación web desarrollada con Spring Boot para la gestión de seguridad y salud en el trabajo. Permite registrar eventos, realizar investigaciones, gestionar acciones correctivas y generar reportes PDF.

## Características

- **Gestión de Usuarios**: Registro y autenticación con roles (Admin, Responsable SST, Jefe de Área, Trabajador)
- **Registro de Eventos**: Accidentes, incidentes y enfermedades profesionales
- **Investigaciones**: Análisis de causas (inmediatas, básicas) y factores participantes
- **Acciones Correctivas**: Asignación y seguimiento con estados (Pendiente, Completada, Vencida)
- **Dashboard**: Vista general con estadísticas y eventos recientes
- **Reportes PDF**: Exportación de eventos y estadísticas
- **Interaz Web**: Thymeleaf con diseño responsivo
- **Seguridad**: Spring Security con autenticación

## Tecnologías

| Tecnología | Versión |
|------------|--------|
| Java | 17 |
| Spring Boot | 3.2.0 |
| MySQL | 8.x |
| Thymeleaf | 3.1.2 |
| Hibernate | 6.3.1 |
| Bootstrap | 5.3.2 |
| iText PDF | 4.2.2 |

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
│   ├── java/com/sst/registro/
│   │   ├── SgsstApplication.java      # Main class
│   │   ├── config/
│   │   │   ├── SecurityConfig.java   # Spring Security
│   │   │   ├── PasswordEncoderConfig.java
│   │   │   ├── DataInitializer.java  # Datos iniciales
│   │   │   └── WebConfig.java
│   │   ├── controller/
│   │   │   ├── LoginController.java
│   │   │   ├── DashboardController.java
│   │   │   ├── EventoController.java
│   │   │   ├── InvestigacionController.java
│   │   │   ├── EstadisticaController.java
│   │   │   └── UsuarioController.java
│   │   ├── model/
│   │   │   ├── entity/
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── Evento.java
│   │   │   │   ├── Investigacion.java
│   │   │   │   └── AccionCorrectiva.java
│   │   │   └── enums/
│   │   │       ├── RolUsuario.java
│   │   │       ├── TipoEvento.java
│   │   │       ├── Gravedad.java
│   │   │       ├── EstadoEvento.java
│   │   │       └── EstadoAccion.java
│   │   ├── repository/
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── EventoRepository.java
│   │   │   ├── InvestigacionRepository.java
│   │   │   └── AccionCorrectivaRepository.java
│   │   └── service/
│   │       ├── UsuarioService.java
│   │       ├── EventoService.java
│   │       ├── InvestigacionService.java
│   │       ├── EstadisticaService.java
│   │       └── ReporteService.java    # Generación PDF
│   └── resources/
│       ├── application.properties
│       └── templates/
│           ├── layout/base.html
│           ├── login/login.html
│           ├── dashboard/dashboard.html
│           ├── evento/
│           │   ├── lista.html
│           │   ├── detalle.html
│           │   └── formulario.html
│           ├── investigacion/
│           │   ├── detalle.html
│           │   └── formulario.html
│           ├── estadistica/estadisticas.html
│           └── usuario/
│               ├── lista.html
│               └── formulario.html
├── pom.xml
└── README.md
```

## Instalación

### Prerrequisitos

- Java 17
- MySQL 8.x (o H2 para desarrollo)

### Configuración

1. Clonar el repositorio
2. Configurar `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sgsst
   spring.datasource.username=root
   spring.datasource.password=tuclavesql
   ```

### Ejecutar

```bash
# Con Maven
mvn spring-boot:run

# O desde IntelliJ
Build > Rebuild Project
Run > SgsstApplication
```

### Credenciales por Defecto

- **Usuario:** `admin@sgsst.com`
- **Contraseña:** `admin123`

## Uso

### Roles de Usuario

| Rol | Permisos |
|-----|--------|
| ADMIN | Todo |
| RESPONSABLE_SST | Ver/crear eventos, investigar, estadísticas |
| JEFE_AREA | Ver/crear eventos de su área |
| TRABAJADOR | Reportar incidentes |

### Endpoints

| URL | Descripción |
|-----|-------------|
| `/login` | Inicio de sesión |
| `/dashboard` | Panel principal |
| `/evento/lista` | Lista de eventos |
| `/evento/nuevo` | Nuevo evento |
| `/evento/detalle/{id}` | Detalle de evento |
| `/estadisticas` | Estadísticas |
| `/admin/usuarios` | Gestión de usuarios |

### Exportar PDF

- **Lista de eventos:** `/evento/exportar/pdf`
- **Evento individual:** `/evento/exportar/{id}/pdf`
- **Estadísticas:** `/estadisticas/exportar/pdf`

## Versiones

- **v2.0.0** - PDF mejorados, MySQL funciona
- **v1.0.0** - Versión inicial

## Licencia

MIT