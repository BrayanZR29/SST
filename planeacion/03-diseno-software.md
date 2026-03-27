# DISEÑO DEL SOFTWARE

## SST-Registro - Diseño de Pantallas

---

## 1. Herramienta Recomendada

**Figma** (gratis para estudiantes)
- Enlace: https://figma.com
- Alternativa gratuita: **Lucidchart** o **Draw.io**

---

## 2. Estructura de Pantallas

### 2.1 Pantalla de Login

```
┌─────────────────────────────────────────────────────────┐
│                                                         │
│              🔒  SST-Registro                           │
│         Gestión de Accidentes e Incidentes              │
│                                                         │
│    ┌─────────────────────────────────────────┐          │
│    │  Correo electrónico                     │          │
│    │  [                             ]        │          │
│    └─────────────────────────────────────────┘          │
│                                                         │
│    ┌─────────────────────────────────────────┐          │
│    │  Contraseña                             │          │
│    │  [                             ]        │          │
│    └─────────────────────────────────────────┘          │
│                                                         │
│         ┌───────────────────────────────┐               │
│         │         INGRESAR              │               │
│         └───────────────────────────────┘               │
│                                                         │
│              ¿Olvidaste tu contraseña?                  │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

### 2.2 Menú Principal (Dashboard)

```
┌─────────────────────────────────────────────────────────┐
│  SST-Registro          Bienvenido: Juan Pérez    [≡]  │
├──────────────┬──────────────────────────────────────────┤
│              │                                          │
│  📊 Dashboard│                                          │
│              │   ┌──────────────────────────────────┐   │
│  📝 Registrar│   │  Resumen del Mes                 │   │
│              │   │  ─────────────────                │   │
│  🔍 Consultar│   │  Accidentes: 3                    │   │
│              │   │  Incidentes: 12                   │   │
│  🔬 Investigar│   │  Pendientes: 2                    │   │
│              │   │  Cerrados: 8                       │   │
│  📈 Estadísticas│ └──────────────────────────────────┘   │
│              │                                          │
│  📄 Reportes │   ┌──────────────────────────────────┐    │
│              │   │  [Gráfico de Barras]             │    │
│  👥 Usuarios │   │  Accidentes por Mes              │    │
│  (Admin)      │   │                                  │    │
│              │   │  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓         │    │
│  ⚙️ Config    │   │  Ene Feb Mar Abr May Jun        │    │
│              │   └──────────────────────────────────┘    │
│  🚪 Cerrar    │                                          │
│     Sesión    │   ┌──────────────────────────────────┐   │
│              │   │  Últimos Eventos                  │   │
│              │   │  ──────────────────                │   │
│              │   │  • ACC-001: Caída en área A  🔴   │   │
│              │   │  • INC-012: Near miss almacén 🔵  │   │
│              │   │  • ACC-002: Quemadura menor  🟡   │   │
│              │   └──────────────────────────────────┘   │
└──────────────┴──────────────────────────────────────────┘
```

---

### 2.3 Formulario de Registro de Evento

```
┌─────────────────────────────────────────────────────────┐
│  ← Registrar Nuevo Evento                              │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Tipo de Evento:                                        │
│  ┌─────────────────┐  ┌─────────────────┐              │
│  │ ○ Accidente     │  │ ○ Incidente     │              │
│  └─────────────────┘  └─────────────────┘              │
│                                                         │
│  ┌─────────────────┐  ┌─────────────────┐              │
│  │ ○ Enfermedad    │  │                 │              │
│  │   Profesional   │  │                 │              │
│  └─────────────────┘  └─────────────────┘              │
│                                                         │
│  ─────────────────────────────────────────────────────│
│                                                         │
│  Fecha y hora: [____/____/2026____:____]               │
│                                                         │
│  Área/Lugar:     [▼ Seleccionar área     ]             │
│                                                         │
│  Personas involucradas:                                 │
│  [                                              ]       │
│                                                         │
│  Descripción del hecho:                                │
│  [                                              ]       │
│  [                                              ]       │
│  [                                              ]       │
│  [                                              ]       │
│                                                         │
│  Consecuencias:                                         │
│  [                                              ]       │
│                                                         │
│  Gravedad:    ( ) Leve  ( ) Grave  ( ) Mortal         │
│                                                         │
│  Responsable de seguimiento: [▼ Seleccionar    ]       │
│                                                         │
│  ┌─────────────────┐  ┌─────────────────┐               │
│  │   GUARDAR       │  │   CANCELAR      │               │
│  └─────────────────┘  └─────────────────┘               │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

### 2.4 Lista de Eventos (Consultar)

```
┌─────────────────────────────────────────────────────────┐
│  Consultar Eventos                           [🔍 Buscar]│
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Filtros:                                               │
│  Estado: [▼ Todos        ]  Tipo: [▼ Todos    ]        │
│  Área:   [▼ Todas        ]  Fecha: [Desde][Hasta]      │
│                                                         │
├─────────────────────────────────────────────────────────┤
│  Código │ Fecha       │ Tipo      │ Área    │ Estado  │
│ ────────┼─────────────┼───────────┼─────────┼─────────│
│ ACC-001 │ 15/03/2026  │ Accidente │ Almacén │ 🔴 Abieto│
│ INC-012 │ 14/03/2026  │ Incidente │ Producción│ 🟡 Proceso│
│ ACC-002 │ 12/03/2026  │ Accidente │ Mantenim.│ 🟢 Cerrado│
│ INC-011 │ 10/03/2026  │ Incidente │ Oficina │ 🟢 Cerrado│
│ ACC-003 │ 08/03/2026  │ Accidente │ Almacén │ 🟢 Cerrado│
│                                                         │
├─────────────────────────────────────────────────────────┤
│  [◀ 1 2 3 4 5 ▶] Mostrando 1-5 de 47                    │
└─────────────────────────────────────────────────────────┘
```

---

### 2.5 Detalle de Evento (Ver/Editar)

```
┌─────────────────────────────────────────────────────────┐
│  ← Detalle del Evento ACC-001                           │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  ┌─────────────────────────────────────────────────┐   │
│  │ DATOS DEL EVENTO                    [✏️ Editar]   │   │
│  │ ──────────────────────────────────────────────── │   │
│  │ Tipo: Accidente          Estado: ● Abierto      │   │
│  │ Fecha: 15/03/2026 08:30  Gravedad: Grave        │   │
│  │ Área: Almacén                                   │   │
│  │ Involucrados: Carlos Mendoza, Juan Pérez       │   │
│  │ Descripción:                                    │   │
│  │ "El trabajador realizaba labores de descarga..."│   │
│  │ Consecuencias: Contusión en brazo derecho      │   │
│  └─────────────────────────────────────────────────┘   │
│                                                         │
│  ┌─────────────────────────────────────────────────┐   │
│  │ INVESTIGACIÓN                                  │   │
│  │ ──────────────────────────────────────────────── │   │
│  │ Causa inmediata: Caída por superficie mojada   │   │
│  │ Causas básicas:                                 │   │
│  │ - No se usó señalización de piso mojado        │   │
│  │ - Falta de supervisión del área                │   │
│  │ Factores: Personal sin entrenamiento de SST    │   │
│  │                                              [ ] │   │
│  │ Investigado por: María López (Resp. SST)       │   │
│  └─────────────────────────────────────────────────┘   │
│                                                         │
│  ┌─────────────────────────────────────────────────┐   │
│  │ ACCIONES CORRECTIVAS                            │   │
│  │ ──────────────────────────────────────────────── │   │
│  │ 1. Colocar señalización de piso mojado          │   │
│  │    Responsable: Juan Torres    Fecha: 20/03/26  │   │
│  │    Estado: ✓ Completada                         │   │
│  │                                                  │   │
│  │ 2. Charla de seguridad con personal de almacén   │   │
│  │    Responsable: María López   Fecha: 18/03/26  │   │
│  │    Estado: ✓ Completada                         │   │
│  │                                                  │   │
│  │ 3. Renovar靴山の培训靴山の培训靴山の培训靴山の培训靴山の培训靴山の培训  │   │
│  │    Responsable: RRHH         Fecha: 30/03/26  │   │
│  │    Estado: ● Pendiente                         │   │
│  │                              [+ Agregar acción] │   │
│  └─────────────────────────────────────────────────┘   │
│                                                         │
│  ┌─────────────────┐  ┌─────────────────┐               │
│  │  CERRAR CASO    │  │  IMPRIMIR PDF   │               │
│  └─────────────────┘  └─────────────────┘               │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

### 2.6 Dashboard / Estadísticas

```
┌─────────────────────────────────────────────────────────┐
│  Estadísticas y Reportes                                │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Período: [▼ Este Año        ]  [Actualizar]            │
│                                                         │
│  ┌────────────────────────┐ ┌────────────────────────┐│
│  │ Accidentes por Mes      │ │ Por Área               ││
│  │ ─────────────────────── │ │ ───────────────────────││
│  │ ▓▓▓▓                   │ │  Almacén    ▓▓▓░ 45%   ││
│  │ ▓▓▓░                   │ │  Producción ▓▓░░ 30%   ││
│  │ ▓▓▓▓▓                  │ │  Oficina    ▓░░░ 15%   ││
│  │ ▓▓                     │ │  Mantenim.  ▓░░░ 10%   ││
│  │ Ene Feb Mar Abr        │ │                        ││
│  └────────────────────────┘ └────────────────────────┘│
│                                                         │
│  ┌────────────────────────┐ ┌────────────────────────┐│
│  │ Por Tipo               │ │ Por Gravedad           ││
│  │ ─────────────────────── │ │ ───────────────────────││
│  │ Accidentes   ▓▓▓▓▓ 60%  │ │ Leve     ▓▓▓▓▓▓ 70%   ││
│  │ Incidentes   ▓▓▓░ 35%   │ │ Grave    ▓▓░░░░ 25%   ││
│  │ Enfermedad   ▓░░░░  5%  │ │ Mortal   ░░░░░░  5%   ││
│  └────────────────────────┘ └────────────────────────┘│
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │ [📄 Exportar Reporte Completo en PDF]            │  │
│  └──────────────────────────────────────────────────┘  │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 3. Colores Recomendados (Paleta SST)

```
┌─────────────────────────────────────────┐
│  Primario:     #2E86AB  (Azul oscuro)   │
│  Secundario:   #A23B72  (Rojo vino)    │
│  Acento:       #F18F01  (Naranja)      │
│  Éxito:        #28A745  (Verde)       │
│  Advertencia:  #FFC107  (Amarillo)     │
│  Peligro:      #DC3545  (Rojo)         │
│  Fondo:        #F8F9FA  (Gris claro)  │
│  Texto:        #212529  (Negro)       │
└─────────────────────────────────────────┘
```

---

## 4. Iconografía de Estados

| Estado | Color | Significado |
|--------|-------|-------------|
| 🔴 Rojo | `#DC3545` | Grave / Mortal / Caso abierto |
| 🟡 Amarillo | `#FFC107` | Moderado / En proceso |
| 🟢 Verde | `#28A745` | Leve / Caso cerrado |

---

*Diseño de pantallas - SST-Registro*
