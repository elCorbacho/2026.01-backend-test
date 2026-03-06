<div align="center">

# 📚 Sistema de Gestión de Álbumes y Láminas...oijoijioj

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**API REST completa para gestión de álbumes de colección**

[Características](#-características-principales) •
[Instalación](#-instalación-y-configuración) •
[Endpoints](#-api-endpoints) •
[Tecnologías](#️-stack-tecnológico)

</div>

---

## 📖 Descripción

API REST desarrollada con **Spring Boot 3.5.9** para la gestión integral de álbumes de colección y sus láminas.

### ✨ Características Destacadas

- ✅ **CRUD Completo** - Operaciones completas para álbumes y láminas
- 🔍 **Validación Automática** - Validación contra catálogo maestro
- 🔄 **Detección de Duplicados** - Identificación automática de láminas repetidas
- 🗑️ **Soft Delete** - Eliminación lógica para mantener trazabilidad
- 📦 **Carga Masiva** - Importación de múltiples láminas simultáneamente
- 📊 **Estado en Tiempo Real** - Progreso de colección instantáneo
- 📝 **Auditoría Automática** - Timestamps automáticos en todas las operaciones
- 🏗️ **Arquitectura REST** - Diseño siguiendo mejores prácticas RESTful

---

## 🚀 Instalación y Configuración

### 📋 Prerrequisitos

Asegúrate de tener instalado:

- ☕ **Java 21** o superior
- 📦 **Maven** (incluido wrapper en el proyecto)
- 🔧 **Git**

### 📥 Instalación

#### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/elCorbacho/18.web2-examen
cd 18.web2-examen
```

#### 2️⃣ Instalar Dependencias

**Windows:**
```bash
.\mvnw.cmd clean install
```

**Linux/Mac:**
```bash
./mvnw clean install
```

#### 3️⃣ Ejecutar la Aplicación

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

#### 4️⃣ Acceder a la Aplicación

Una vez iniciada la aplicación, accede a:

🌐 **API Base URL:** `http://localhost:8080`
🔍 **Actuator Health:** `http://localhost:8080/actuator/health`
📜 **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 💾 Base de Datos

El proyecto utiliza **MySQL** montado en **AWS RDS** 
![ddbb](screenshots/ddbb-diagram2.png)

### 🧾 Script de tabla `presidente_chile`

Para crear la nueva tabla de presidentes se incluye el script:

- `docs/db/presidentes_chile.sql`


## 📡 API Endpoints

### 🎯 1. Gestión de Álbumes
**Base:** `/api/albums`

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="250">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/albums</code></td>
<td>Crear un nuevo álbum de colección</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums</code></td>
<td>Listar todos los álbumes registrados</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Obtener detalles de un álbum específico</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{id}/ganadores</code></td>
<td>Listar los ganadores asociados a un álbum</td>
</tr>
<tr>
<td><code>PUT</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Actualizar información de un álbum</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Eliminar álbum (soft delete)</td>
</tr>
</tbody>
</table>

---

### 🏷️ 2. Gestión de Láminas (Usuario)
**Base:** `/api/laminas`

> 💡 **Validación automática contra catálogo + Detección de repetidas**

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="300">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/laminas</code></td>
<td>Agregar lámina con validación y detección de repetidas</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas</code></td>
<td>Listar todas las láminas del sistema</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Obtener detalles de una lámina específica</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas/album/{albumId}</code></td>
<td>Listar todas las láminas de un álbum</td>
</tr>
<tr>
<td><code>PUT</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Actualizar información de una lámina</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Eliminar lámina (soft delete)</td>
</tr>
</tbody>
</table>

---

### 🚗 3. Catálogo de Marcas de Automóvil
**Base:** `/api/marcas`

> 💡 El endpoint entrega una lista auditada de marcas activas disponibles para filtros, catálogos o UI que necesite referencias de fabricantes.

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="300">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>GET</code></td>
<td><code>/api/marcas</code></td>
<td>Listar todas las marcas de automóviles activas (30 registros semilla) en `ApiResponseDTO`.</td>
</tr>
</tbody>
</table>

---

### 📖 3. Catálogo y Estadísticas
**Base:** `/api/albums/{albumId}/catalogo`

> 📊 **Administración del catálogo maestro y seguimiento de progreso**

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="350">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/albums/{albumId}/catalogo</code></td>
<td>Crear catálogo maestro de láminas</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{albumId}/catalogo</code></td>
<td>Ver catálogo completo disponible</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{albumId}/catalogo/estado</code></td>
<td>Ver estadísticas: poseídas, faltantes, repetidas y totales</td>
</tr>
</tbody>
</table>

---

### 📦 4. Operaciones Masivas
**Base:** `/api/laminas/masivo`

> ⚡ **Carga rápida de múltiples láminas**

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="250">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/laminas/masivo</code></td>
<td>Agregar múltiples láminas (valida cada una individualmente)</td>
</tr>
</tbody>
</table>

---

### 🏥 5. Monitoreo y Salud
**Base:** `/actuator`

> 🔍 **Spring Boot Actuator para monitoring**

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="250">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>GET</code></td>
<td><code>/actuator/health</code></td>
<td>Estado de salud de la aplicación</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/actuator/info</code></td>
<td>Información de la aplicación</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/actuator</code></td>
<td>Lista completa de endpoints disponibles</td>
</tr>
</tbody>
</table>

---

### 🌎 6. Regiones de Chile
**Base:** `/api/regiones`

> ✅ Devuelve la lista canonica de regiones activas en formato `ApiResponseDTO`.

<table>
<thead>
<tr>
<th width="80">Método</th>
<th width="250">Endpoint</th>
<th>Descripción</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>GET</code></td>
<td><code>/api/regiones</code></td>
<td>Listar todas las regiones activas de Chile ordenadas por código</td>
</tr>
</tbody>
</table>

---

## 🛠️ Stack Tecnológico

### Backend Framework
- ☕ **Java 21** - Lenguaje de programación
- 🍃 **Spring Boot 3.5.9** - Framework principal

### Dependencias Spring
- 🌐 **Spring Web** - Construcción de APIs REST
- 🗄️ **Spring Data JPA** - Persistencia y gestión de datos
- 📊 **Spring Boot Actuator** - Monitoreo y métricas
- 🔥 **Spring Boot DevTools** - Hot-reload en desarrollo

### Base de Datos
- 🐬 **MySQL** - Base de datos en AWS RDS

### Herramientas y Librerías
- 🧰 **Lombok** - Reducción de código boilerplate
- 📦 **Maven** - Gestión de dependencias y construcción
- ✅ **Jakarta Validation** - Validación de datos

### Arquitectura
- 🏗️ **MVC Pattern** - Separación de capas
- 🔄 **DTOs & Mappers** - Transferencia segura de datos
- 🗑️ **Soft Delete Pattern** - Eliminación lógica
- ⏰ **JPA Auditing** - Auditoría automática

---

## 📸 Capturas de Pantalla

### 🚀 Aplicación en Ejecución

<div align="center">

![Aplicación Iniciada](screenshots/1%20app%20up.png)
*Aplicación Spring Boot iniciada correctamente*

</div>

---

### 🧪 Pruebas de API con Postman

<div align="center">

![Prueba de API](screenshots/2%20api%20test.png)
*Testing de endpoints REST con Postman*

</div>

---

### 📊 Operaciones y Respuestas

<div align="center">

![Operación 3](screenshots/3.png)
![Operación 4](screenshots/4.png)
![Operación 5](screenshots/5.png)
![Operación 6](screenshots/6.png)
![Operación 7](screenshots/7.png)
![Operación 8](screenshots/8.png)
![Operación 9](screenshots/9.png)
![Operación 10](screenshots/10.png)
![Operación 11](screenshots/11.png)
![Operación 12](screenshots/12.png)
![Operación 13](screenshots/13.png)
![Operación 14](screenshots/14.png)
![Operación 15](screenshots/15.png)
![Operación 16](screenshots/16.png)
![Operación 17](screenshots/17.png)
![Operación 18](screenshots/18.png)
![Operación 19](screenshots/19.png)

</div>

---

### 💾 Base de Datos

<div align="center">

![Base de Datos 1](screenshots/ddbb1.png)
*Estructura de tablas*

![Base de Datos 2](screenshots/ddbb2.png)
*Datos almacenados*

![Base de Datos 3](screenshots/ddbb3.png)
*Registros de láminas*

![Base de Datos 4](screenshots/ddbb4.png)
*Catálogo de láminas*

![Base de Datos 5](screenshots/ddbb5.png)
*Relaciones y datos completos*

</div>

### 📜 Swagger UI

<div align="center">

![SWAGGER](screenshots/swagger.png)
*Documentación interactiva con Swagger UI*

</div>

---

## 🧪 Colecciones de API para Testing

Para facilitar las pruebas de la API, se incluyen colecciones completas para **Postman** y **Bruno** con todos los endpoints documentados y ejemplos de respuestas.

### 📦 Archivos Disponibles

<table>
<thead>
<tr>
<th width="120">Cliente</th>
<th width="300">Archivo</th>
<th>Endpoints</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">📮 <strong>Postman</strong></td>
<td><a href="api-collections/18.web2.examen-postman.json">18.web2.examen-postman.json</a></td>
<td>18 endpoints testeados</td>
</tr>
<tr>
<td align="center">🐻 <strong>Bruno</strong></td>
<td><a href="api-collections/18.web2.examen-bruno.json">18.web2.examen-bruno.json</a></td>
<td>18 endpoints testeados</td>
</tr>
</tbody>
</table>

### 🚀 Cómo Usar

#### Para Postman:
1. Abre **Postman**
2. Click en **Import** (esquina superior izquierda)
3. Selecciona el archivo [`18.web2.examen-postman.json`](api-collections/18.web2.examen-postman.json)
4. ¡Listo! Tendrás 18 peticiones configuradas y listas para probar

#### Para Bruno:
1. Abre **Bruno**
2. Click en **Import Collection**
3. Selecciona el archivo [`18.web2.examen-bruno.json`](api-collections/18.web2.examen-bruno.json)
4. Todas las peticiones con ejemplos estarán disponibles

### ✅ Contenido de las Colecciones

Las colecciones incluyen:
- ✔️ **CRUD Álbumes** - 5 endpoints (Crear, Listar, Obtener, Actualizar, Eliminar)
- ✔️ **CRUD Láminas** - 6 endpoints (Crear, Listar, Obtener por ID/Álbum, Actualizar, Eliminar)
- ✔️ **Catálogo** - 3 endpoints (Crear catálogo, Ver catálogo, Estado)
- ✔️ **Carga Masiva** - 2 endpoints (Agregar múltiples láminas)
- ✔️ **Casos de Error** - Ejemplos de validación y errores
- ✔️ **Ejemplos de Respuesta** - Respuestas reales guardadas para referencia
 
---

## 🤖 Agentes, Skills y Memoria

La documentación específica sobre agentes, skills y las reglas de memoria (Engram) se encuentra en:

- [README — Agentes, Skills y Memoria (Engram)](readme-agents.md)

Revisa ese archivo para normas, plantillas y procedimientos recomendados para trabajar con agentes en este repositorio.
## 🖥️ Vistas Web

- Inicio: `http://localhost:8080/`
- Estructura detallada de base de datos: `http://localhost:8080/db/estructura-detallada`
- Swagger UI: `http://localhost:8080/swagger-ui.html`


## 🎨 Convenciones Frontend

- Guía de normalización y estructura: [docs/frontend-conventions.md](docs/frontend-conventions.md)
- Inventario de auditoría frontend: [docs/frontend-normalization-inventory.md](docs/frontend-normalization-inventory.md)
- Baseline automático: [docs/frontend-normalization-baseline.md](docs/frontend-normalization-baseline.md)

## 🎯 Idioma y codificación

- Todos los archivos públicos que se sirven al navegador se guardan en UTF-8 y declaran `<meta charset="UTF-8" />` junto con `<html lang="es">` para que el contenido se interprete correctamente como español.
- Al editar textos visibles (landing, guías, tablas y mensajes), escribe las cadenas en español con los acentos adecuados (`á`, `é`, `í`, `ó`, `ú`, `ñ`, etc.) y evita dejar mojibake como `Ã¡`/`Ã±`.
- El spec `spanish-frontend-localization` en `openspec/changes/fix-frontend-spanish-accents/specs/` documenta este comportamiento y define escenarios de verificación.
- Después de cambios en los assets estáticos, ejecuta `mvn clean package` y confirma que `target/classes/static` conserva los acentos antes de compartir el build.

