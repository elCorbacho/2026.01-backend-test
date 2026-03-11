# 2026.01-backend-test

API REST for managing collectible albums and items..........888888888888888888888888888877777777777

## Quick Start

### Prerequisites:
- Java 21+
- Maven

### Installation:
1. Clone the repo:
   ```bash
   git clone https://github.com/elCorbacho/18.web2-examen
   cd 18.web2-examen
   ```

2. Install dependencies:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

Access the API:
- Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## New Resource: Ciudades de Chile

- `GET /api/ciudades-chile` listado general de ciudades activas
- `GET /api/ciudades-chile/{id}` detalle de ciudad activa por id
- Datos iniciales H2: 10 ciudades con poblacion cargadas automaticamente al iniciar

## Test Commit
This is a test commit from Claude Code.
Updated at 2026-03-06 - Another test commit.