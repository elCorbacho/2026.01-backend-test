<div align="center">

# ðŸ“š Sistema de GestiÃ³n de Ãlbumes y LÃ¡minas

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**API REST completa para gestiÃ³n de Ã¡lbumes de colecciÃ³n**

[CaracterÃ­sticas](#-caracterÃ­sticas-principales) â€¢
[InstalaciÃ³n](#-instalaciÃ³n-y-configuraciÃ³n) â€¢
[Endpoints](#-api-endpoints) â€¢
[TecnologÃ­as](#ï¸-stack-tecnolÃ³gico)

</div>

---

## ðŸ“– DescripciÃ³n

API REST desarrollada con **Spring Boot 3.5.9** para la gestiÃ³n integral de Ã¡lbumes de colecciÃ³n y sus lÃ¡minas.

### âœ¨ CaracterÃ­sticas Destacadas

- âœ… **CRUD Completo** - Operaciones completas para Ã¡lbumes y lÃ¡minas
- ðŸ” **ValidaciÃ³n AutomÃ¡tica** - ValidaciÃ³n contra catÃ¡logo maestro
- ðŸ”„ **DetecciÃ³n de Duplicados** - IdentificaciÃ³n automÃ¡tica de lÃ¡minas repetidas
- ðŸ—‘ï¸ **Soft Delete** - EliminaciÃ³n lÃ³gica para mantener trazabilidad
- ðŸ“¦ **Carga Masiva** - ImportaciÃ³n de mÃºltiples lÃ¡minas simultÃ¡neamente
- ðŸ“Š **Estado en Tiempo Real** - Progreso de colecciÃ³n instantÃ¡neo
- ðŸ“ **AuditorÃ­a AutomÃ¡tica** - Timestamps automÃ¡ticos en todas las operaciones
- ðŸ—ï¸ **Arquitectura REST** - DiseÃ±o siguiendo mejores prÃ¡cticas RESTful

---

## ðŸš€ InstalaciÃ³n y ConfiguraciÃ³n

### ðŸ“‹ Prerrequisitos

AsegÃºrate de tener instalado:

- â˜• **Java 21** o superior
- ðŸ“¦ **Maven** (incluido wrapper en el proyecto)
- ðŸ”§ **Git**

### ðŸ“¥ InstalaciÃ³n

#### 1ï¸âƒ£ Clonar el Repositorio

```bash
git clone https://github.com/elCorbacho/18.web2-examen
cd 18.web2-examen
```

#### 2ï¸âƒ£ Instalar Dependencias

**Windows:**
```bash
.\mvnw.cmd clean install
```

**Linux/Mac:**
```bash
./mvnw clean install
```

#### 3ï¸âƒ£ Ejecutar la AplicaciÃ³n

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

#### 4ï¸âƒ£ Acceder a la AplicaciÃ³n

Una vez iniciada la aplicaciÃ³n, accede a:

ðŸŒ **API Base URL:** `http://localhost:8080`
ðŸ” **Actuator Health:** `http://localhost:8080/actuator/health`
ðŸ“œ **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## ðŸ’¾ Base de Datos

El proyecto utiliza **MySQL** montado en **AWS RDS** 
![ddbb](screenshots/ddbb-diagram2.png)


## ðŸ“¡ API Endpoints

### ðŸŽ¯ 1. GestiÃ³n de Ãlbumes
**Base:** `/api/albums`

<table>
<thead>
<tr>
<th width="80">MÃ©todo</th>
<th width="250">Endpoint</th>
<th>DescripciÃ³n</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/albums</code></td>
<td>Crear un nuevo Ã¡lbum de colecciÃ³n</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums</code></td>
<td>Listar todos los Ã¡lbumes registrados</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Obtener detalles de un Ã¡lbum especÃ­fico</td>
</tr>
<tr>
<td><code>PUT</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Actualizar informaciÃ³n de un Ã¡lbum</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td><code>/api/albums/{id}</code></td>
<td>Eliminar Ã¡lbum (soft delete)</td>
</tr>
</tbody>
</table>

---

### ðŸ·ï¸ 2. GestiÃ³n de LÃ¡minas (Usuario)
**Base:** `/api/laminas`

> ðŸ’¡ **ValidaciÃ³n automÃ¡tica contra catÃ¡logo + DetecciÃ³n de repetidas**

<table>
<thead>
<tr>
<th width="80">MÃ©todo</th>
<th width="300">Endpoint</th>
<th>DescripciÃ³n</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/laminas</code></td>
<td>Agregar lÃ¡mina con validaciÃ³n y detecciÃ³n de repetidas</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas</code></td>
<td>Listar todas las lÃ¡minas del sistema</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Obtener detalles de una lÃ¡mina especÃ­fica</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/laminas/album/{albumId}</code></td>
<td>Listar todas las lÃ¡minas de un Ã¡lbum</td>
</tr>
<tr>
<td><code>PUT</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Actualizar informaciÃ³n de una lÃ¡mina</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td><code>/api/laminas/{id}</code></td>
<td>Eliminar lÃ¡mina (soft delete)</td>
</tr>
</tbody>
</table>

---

### ðŸ“– 3. CatÃ¡logo y EstadÃ­sticas
**Base:** `/api/albums/{albumId}/catalogo`

> ðŸ“Š **AdministraciÃ³n del catÃ¡logo maestro y seguimiento de progreso**

<table>
<thead>
<tr>
<th width="80">MÃ©todo</th>
<th width="350">Endpoint</th>
<th>DescripciÃ³n</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/albums/{albumId}/catalogo</code></td>
<td>Crear catÃ¡logo maestro de lÃ¡minas</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{albumId}/catalogo</code></td>
<td>Ver catÃ¡logo completo disponible</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/api/albums/{albumId}/catalogo/estado</code></td>
<td>Ver estadÃ­sticas: poseÃ­das, faltantes, repetidas y totales</td>
</tr>
</tbody>
</table>

---

### ðŸ“¦ 4. Operaciones Masivas
**Base:** `/api/laminas/masivo`

> âš¡ **Carga rÃ¡pida de mÃºltiples lÃ¡minas**

<table>
<thead>
<tr>
<th width="80">MÃ©todo</th>
<th width="250">Endpoint</th>
<th>DescripciÃ³n</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td><code>/api/laminas/masivo</code></td>
<td>Agregar mÃºltiples lÃ¡minas (valida cada una individualmente)</td>
</tr>
</tbody>
</table>

---

### ðŸ¥ 5. Monitoreo y Salud
**Base:** `/actuator`

> ðŸ” **Spring Boot Actuator para monitoring**

<table>
<thead>
<tr>
<th width="80">MÃ©todo</th>
<th width="250">Endpoint</th>
<th>DescripciÃ³n</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>GET</code></td>
<td><code>/actuator/health</code></td>
<td>Estado de salud de la aplicaciÃ³n</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/actuator/info</code></td>
<td>InformaciÃ³n de la aplicaciÃ³n</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/actuator</code></td>
<td>Lista completa de endpoints disponibles</td>
</tr>
</tbody>
</table>

---

## ðŸ› ï¸ Stack TecnolÃ³gico

### Backend Framework
- â˜• **Java 21** - Lenguaje de programaciÃ³n
- ðŸƒ **Spring Boot 3.5.9** - Framework principal

### Dependencias Spring
- ðŸŒ **Spring Web** - ConstrucciÃ³n de APIs REST
- ðŸ—„ï¸ **Spring Data JPA** - Persistencia y gestiÃ³n de datos
- ðŸ“Š **Spring Boot Actuator** - Monitoreo y mÃ©tricas
- ðŸ”¥ **Spring Boot DevTools** - Hot-reload en desarrollo

### Base de Datos
- ðŸ¬ **MySQL** - Base de datos en AWS RDS

### Herramientas y LibrerÃ­as
- ðŸ§° **Lombok** - ReducciÃ³n de cÃ³digo boilerplate
- ðŸ“¦ **Maven** - GestiÃ³n de dependencias y construcciÃ³n
- âœ… **Jakarta Validation** - ValidaciÃ³n de datos

### Arquitectura
- ðŸ—ï¸ **MVC Pattern** - SeparaciÃ³n de capas
- ðŸ”„ **DTOs & Mappers** - Transferencia segura de datos
- ðŸ—‘ï¸ **Soft Delete Pattern** - EliminaciÃ³n lÃ³gica
- â° **JPA Auditing** - AuditorÃ­a automÃ¡tica

---

## ðŸ“¸ Capturas de Pantalla

### ðŸš€ AplicaciÃ³n en EjecuciÃ³n

<div align="center">

![AplicaciÃ³n Iniciada](screenshots/1%20app%20up.png)
*AplicaciÃ³n Spring Boot iniciada correctamente*

</div>

---

### ðŸ§ª Pruebas de API con Postman

<div align="center">

![Prueba de API](screenshots/2%20api%20test.png)
*Testing de endpoints REST con Postman*

</div>

---

### ðŸ“Š Operaciones y Respuestas

<div align="center">

![OperaciÃ³n 3](screenshots/3.png)
![OperaciÃ³n 4](screenshots/4.png)
![OperaciÃ³n 5](screenshots/5.png)
![OperaciÃ³n 6](screenshots/6.png)
![OperaciÃ³n 7](screenshots/7.png)
![OperaciÃ³n 8](screenshots/8.png)
![OperaciÃ³n 9](screenshots/9.png)
![OperaciÃ³n 10](screenshots/10.png)
![OperaciÃ³n 11](screenshots/11.png)
![OperaciÃ³n 12](screenshots/12.png)
![OperaciÃ³n 13](screenshots/13.png)
![OperaciÃ³n 14](screenshots/14.png)
![OperaciÃ³n 15](screenshots/15.png)
![OperaciÃ³n 16](screenshots/16.png)
![OperaciÃ³n 17](screenshots/17.png)
![OperaciÃ³n 18](screenshots/18.png)
![OperaciÃ³n 19](screenshots/19.png)

</div>

---

### ðŸ’¾ Base de Datos

<div align="center">

![Base de Datos 1](screenshots/ddbb1.png)
*Estructura de tablas*

![Base de Datos 2](screenshots/ddbb2.png)
*Datos almacenados*

![Base de Datos 3](screenshots/ddbb3.png)
*Registros de lÃ¡minas*

![Base de Datos 4](screenshots/ddbb4.png)
*CatÃ¡logo de lÃ¡minas*

![Base de Datos 5](screenshots/ddbb5.png)
*Relaciones y datos completos*

</div>

### ðŸ“œ Swagger UI

<div align="center">

![SWAGGER](screenshots/swagger.png)
*DocumentaciÃ³n interactiva con Swagger UI*

</div>

---

## ðŸ§ª Colecciones de API para Testing

Para facilitar las pruebas de la API, se incluyen colecciones completas para **Postman** y **Bruno** con todos los endpoints documentados y ejemplos de respuestas.

### ðŸ“¦ Archivos Disponibles

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
<td align="center">ðŸ“® <strong>Postman</strong></td>
<td><a href="api-collections/18.web2.examen-postman.json">18.web2.examen-postman.json</a></td>
<td>18 endpoints testeados</td>
</tr>
<tr>
<td align="center">ðŸ» <strong>Bruno</strong></td>
<td><a href="api-collections/18.web2.examen-bruno.json">18.web2.examen-bruno.json</a></td>
<td>18 endpoints testeados</td>
</tr>
</tbody>
</table>

### ðŸš€ CÃ³mo Usar

#### Para Postman:
1. Abre **Postman**
2. Click en **Import** (esquina superior izquierda)
3. Selecciona el archivo [`18.web2.examen-postman.json`](api-collections/18.web2.examen-postman.json)
4. Â¡Listo! TendrÃ¡s 18 peticiones configuradas y listas para probar

#### Para Bruno:
1. Abre **Bruno**
2. Click en **Import Collection**
3. Selecciona el archivo [`18.web2.examen-bruno.json`](api-collections/18.web2.examen-bruno.json)
4. Todas las peticiones con ejemplos estarÃ¡n disponibles

### âœ… Contenido de las Colecciones

Las colecciones incluyen:
- âœ”ï¸ **CRUD Ãlbumes** - 5 endpoints (Crear, Listar, Obtener, Actualizar, Eliminar)
- âœ”ï¸ **CRUD LÃ¡minas** - 6 endpoints (Crear, Listar, Obtener por ID/Ãlbum, Actualizar, Eliminar)
- âœ”ï¸ **CatÃ¡logo** - 3 endpoints (Crear catÃ¡logo, Ver catÃ¡logo, Estado)
- âœ”ï¸ **Carga Masiva** - 2 endpoints (Agregar mÃºltiples lÃ¡minas)
- âœ”ï¸ **Casos de Error** - Ejemplos de validaciÃ³n y errores
- âœ”ï¸ **Ejemplos de Respuesta** - Respuestas reales guardadas para referencia
 
---

## ðŸ¤– Agentes, Skills y Memoria

La documentaciÃ³n especÃ­fica sobre agentes, skills y las reglas de memoria (Engram) se encuentra en:

- [README â€” Agentes, Skills y Memoria (Engram)](readme-agents.md)

Revisa ese archivo para normas, plantillas y procedimientos recomendados para trabajar con agentes en este repositorio.
## ðŸ–¥ï¸ Vistas Web

- Inicio: `http://localhost:8080/`
- Estructura detallada de base de datos: `http://localhost:8080/db/estructura-detallada`
- Swagger UI: `http://localhost:8080/swagger-ui.html`


## 🎨 Convenciones Frontend

- Guía de normalización y estructura: [docs/frontend-conventions.md](docs/frontend-conventions.md)
- Inventario de auditoría frontend: [docs/frontend-normalization-inventory.md](docs/frontend-normalization-inventory.md)
- Baseline automático: [docs/frontend-normalization-baseline.md](docs/frontend-normalization-baseline.md)

