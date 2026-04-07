# ESTRUCTURA DEL SOFTWARE

## SST-Registro - Arquitectura y Paquetes

---

## 1. Arquitectura General

```
┌─────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                  │
│              (Thymeleaf + Controllers)                  │
│     ┌──────────┐  ┌──────────┐  ┌──────────┐            │
│     │LoginCtrl │  │ EventCtrl│  │StatsCtrl │            │
│     └──────────┘  └──────────┘  └──────────┘            │
├─────────────────────────────────────────────────────────┤
│                      SERVICE LAYER                      │
│     ┌──────────┐  ┌──────────┐  ┌──────────┐            │
│     │UserService│ │EventSvc  │  │StatsSvc  │            │
│     └──────────┘  └──────────┘  └──────────┘            │
├─────────────────────────────────────────────────────────┤
│                    REPOSITORY LAYER                     │
│     ┌──────────┐  ┌──────────┐  ┌──────────┐            │
│     │UserRepo  │  │EventRepo │  │ActionRepo│            │
│     └──────────┘  └──────────┘  └──────────┘            │
├─────────────────────────────────────────────────────────┤
│                       DOMAIN                            │
│              (Entities + Relationships)                 │
│     ┌──────────┐  ┌──────────┐  ┌──────────┐            │
│     │  Usuario │  │  Evento  │  │  Accion  │            │
│     └──────────┘  └──────────┘  └──────────┘            │
├─────────────────────────────────────────────────────────┤
│                      DATABASE                           │
│                        MySQL                            │
└─────────────────────────────────────────────────────────┘
```

---

## 2. Estructura de Paquetes

```
src/main/java/com/sst/registro/
│
├── SstRegistroApplication.java        ← Clase principal
│
├── config/
│   ├── SecurityConfig.java            ← Configuración de Spring Security
│   └── WebConfig.java                 ← Configuración web
│
├── controller/
│   ├── LoginController.java           ← Login y logout
│   ├── DashboardController.java       ← Página principal
│   ├── EventoController.java          ← CRUD de eventos
│   ├── InvestigacionController.java   ← Gestión de investigaciones
│   ├── EstadisticaController.java     ← Estadísticas y reportes
│   ├── UsuarioController.java         ← Gestión de usuarios (admin)
│   └── ReporteController.java         ← Generación de PDFs
│
├── model/
│   ├── entity/
│   │   ├── Usuario.java               ← Entidad Usuario
│   │   ├── Evento.java                ← Entidad Evento (accidente/incidente)
│   │   ├── Investigacion.java         ← Entidad Investigación
│   │   └── AccionCorrectiva.java      ← Entidad Acción Correctiva
│   │
│   ├── enums/
│   │   ├── TipoEvento.java             ← ACCIDENTE, INCIDENTE, ENFERMEDAD
│   │   ├── Gravedad.java               ← LEVE, GRAVE, MORTAL
│   │   ├── EstadoEvento.java           ← ABIERTO, EN_PROCESO, CERRADO
│   │   ├── EstadoAccion.java           ← PENDIENTE, COMPLETADA
│   │   └── RolUsuario.java             ← ADMIN, RESPONSABLE_SST, JEFE_AREA, TRABAJADOR
│   │
│   └── dto/
│       ├── EventoDTO.java              ← Objeto de transferencia
│       ├── InvestigacionDTO.java
│       ├── EstadisticaDTO.java
│       └── LoginDTO.java
│
├── repository/
│   ├── UsuarioRepository.java          ← JpaRepository<Usuario, Long>
│   ├── EventoRepository.java           ← JpaRepository<Evento, Long>
│   ├── InvestigacionRepository.java
│   └── AccionCorrectivaRepository.java
│
├── service/
│   ├── UsuarioService.java             ← Lógica de usuarios
│   ├── EventoService.java              ← Lógica de eventos
│   ├── InvestigacionService.java       ← Lógica de investigaciones
│   ├── EstadisticaService.java         ← Lógica de estadísticas
│   └── ReporteService.java             ← Generación de reportes
│
└── util/
    ├── PasswordEncoder.java            ← Utilidad para encriptar contraseñas
    └── FechaUtil.java                  ← Utilidades de fechas
```

---

## 3. Estructura de Recursos (Templates)

```
src/main/resources/
│
├── application.properties              ← Configuración de BD, puerto, etc.
│
└── templates/
    ├── layout/
    │   ├── base.html                   ← Plantilla base (head, body, scripts)
    │   └── sidebar.html                ← Menú lateral
    │
    ├── login/
    │   └── login.html                  ← Pantalla de login
    │
    ├── dashboard/
    │   └── dashboard.html              ← Página principal
    │
    ├── evento/
    │   ├── lista.html                  ← Lista de eventos
    │   ├── formulario.html             ← Registrar/editar evento
    │   └── detalle.html                ← Ver detalle de evento
    │
    ├── investigacion/
    │   └── formulario.html             ← Formulario de investigación
    │
    ├── estadistica/
    │   └── estadisticas.html           ← Dashboard de estadísticas
    │
    ├── usuario/
    │   ├── lista.html                  ← Lista de usuarios
    │   └── formulario.html             ← Crear/editar usuario
    │
    └── fragment/
        ├── pagination.html             ← Componente de paginación
        └── mensajes.html               ← Alertas y notificaciones
```

---

## 4. Estructura de Archivos Estáticos

```
src/main/resources/static/
│
├── css/
│   └── estilos.css                      ← Estilos personalizados
│
└── js/
    └── main.js                          ← JavaScript personalizado
```

---

## 5. Diagrama de Clases Principal

```
┌────────────────────────────────────────────────────────────┐
│                        USUARIO                             │
├────────────────────────────────────────────────────────────┤
│ - id: Long                                                 │
│ - nombre: String                                           │
│ - correo: String                                           │
│ - password: String (encriptado)                            │
│ - rol: RolUsuario                                          │
│ - area: String                                             │
├────────────────────────────────────────────────────────────┤
│ + guardar()                                                │
│ + eliminar()                                               │
│ + buscarPorCorreo(correo): Usuario                         │
└────────────────────────────────────────────────────────────┘
              │
              │ 1
              │
              │ tiene
              ▼
┌────────────────────────────────────────────────────────────┐
│                        EVENTO                              │
├────────────────────────────────────────────────────────────┤
│ - id: Long                                                 │
│ - codigo: String (ej: "ACC-001")                           │
│ - tipo: TipoEvento                                         │
│ - gravedad: Gravedad                                       │
│ - estado: EstadoEvento                                     │
│ - fechaHora: LocalDateTime                                 │
│ - lugar: String                                            │
│ - descripcion: String                                      │
│ - consecuencias: String                                    │
│ - personasInvolucradas: String                             │
│ - usuarioRegistra: Usuario                                 │
├────────────────────────────────────────────────────────────┤
│ + guardar()                                                │
│ + cerrar()                                                 │
│ + agregarInvestigacion()                                   │
│ + generarCodigo(): String                                  │
└────────────────────────────────────────────────────────────┘
              │
              │ 1
              │
              │ tiene
              ▼
┌────────────────────────────────────────────────────────────┐
│                    INVESTIGACION                           │
├────────────────────────────────────────────────────────────┤
│ - id: Long                                                 │
│ - causaInmediata: String                                   │
│ - causaBasica: String                                      │
│ - factoresContribuitorios: String                          │
│ - fechaInvestigacion: LocalDateTime                        │
│ - investigador: Usuario                                    │
│ - evento: Evento                                           │
├────────────────────────────────────────────────────────────┤
│ + guardar()                                                │
│ + agregarAccionCorrectiva()                                │
└────────────────────────────────────────────────────────────┘
              │
              │ 1
              │
              │ tiene
              ▼
┌────────────────────────────────────────────────────────────┐
│                  ACCION_CORRECTIVA                         │
├────────────────────────────────────────────────────────────┤
│ - id: Long                                                 │
│ - descripcion: String                                      │
│ - responsable: String                                      │
│ - fechaLimite: LocalDate                                   │
│ - estadoAccion: EstadoAccion                               │
│ - fechaCompletado: LocalDate                               │
│ - investigacion: Investigacion                             │
├────────────────────────────────────────────────────────────┤
│ + completar()                                              │
│ + marcarPendiente()                                        │
└────────────────────────────────────────────────────────────┘
```

---

## 6. Modelo de Datos (Tablas)

### Tabla: usuario
| Campo    | Tipo         | Descripción                                   |
|----------|--------------|-----------------------------------------------|
| id       |  BIGINT (PK) | Identificador                                 |
| nombre   | VARCHAR(100) | Nombre completo                               |
| correo   | VARCHAR(100) | Email (único)                                 |
| password | VARCHAR(255) | Contraseña encriptada                         |
| rol      | ENUM         | ADMIN, RESPONSABLE_SST, JEFE_AREA, TRABAJADOR |
| area     | VARCHAR(100) | Área de trabajo                               |
| activo   | BOOLEAN      | Si está activo                                |

### Tabla: evento
| Campo                 | Tipo         | Descripción                      |
|-----------------------|--------------|----------------------------------|
| id                    | BIGINT (PK)  | Identificador                    |
| codigo                | VARCHAR(20)  | Código (ACC-001, INC-002)        |
| tipo                  | ENUM         | ACCIDENTE, INCIDENTE, ENFERMEDAD |
| gravedad              | ENUM         | LEVE, GRAVE, MORTAL              |
| estado                | ENUM         | ABIERTO, EN_PROCESO, CERRADO     |
| fecha_hora            | DATETIME     | Fecha y hora del evento          |
| lugar                 | VARCHAR(100) | Área o lugar                     |
| descripcion           | TEXT         | Descripción del hecho            |
| consecuencias         | TEXT         | Consecuencias                    |
| personas_involucradas | VARCHAR(255) | Nombres                          |
| usuario_id            | BIGINT (FK)  | Quien registró                   |

### Tabla: investigacion
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | Identificador |
| causa_inmediata | TEXT | Causa inmediata |
| causa_basica | TEXT | Causa básica |
| factores | TEXT | Factores contributorios |
| fecha_investigacion | DATETIME | Fecha de investigación |
| evento_id | BIGINT (FK) | Evento relacionado |
| investigador_id | BIGINT (FK) | Quien investiga |

### Tabla: accion_correctiva
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | Identificador |
| descripcion | VARCHAR(500) | Descripción de la acción |
| responsable | VARCHAR(100) | Persona responsable |
| fecha_limite | DATE | Fecha límite |
| estado | ENUM | PENDIENTE, COMPLETADA |
| fecha_completado | DATE | Fecha de completado |
| investigacion_id | BIGINT (FK) | Investigación relacionada |

---

*Estructura del código - SST-Registro*
