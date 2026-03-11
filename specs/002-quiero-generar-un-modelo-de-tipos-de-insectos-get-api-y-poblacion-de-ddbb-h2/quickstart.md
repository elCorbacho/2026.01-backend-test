# Quickstart - Catalogo de tipos de insectos

## Prerrequisitos

- Java 21 disponible
- Maven Wrapper del proyecto (`mvnw.cmd` en Windows)

## 1) Ejecutar aplicacion

```bash
mvnw.cmd spring-boot:run
```

## 2) Verificar seed inicial de tipos de insecto

- En el primer arranque en base limpia, deben existir al menos 10 tipos de insecto activos.
- En reinicios posteriores no deben duplicarse registros.

## 3) Probar endpoints

### Obtener listado

```bash
curl -X GET http://localhost:8080/api/tipos-insecto
```

Resultado esperado:
- Respuesta exitosa con envelope `success/message/data`.
- `data` contiene solo tipos de insecto activos.

### Obtener detalle por id

```bash
curl -X GET http://localhost:8080/api/tipos-insecto/1
```

Resultado esperado:
- Si existe y esta activo: respuesta exitosa con un registro.
- Si no existe o esta inactivo: respuesta 404 con error controlado.

## 4) Ejecutar pruebas

```bash
mvnw.cmd test -Dtest=TipoInsectoControllerWebMvcTest
mvnw.cmd test -Dtest=TipoInsectoServiceTest
mvnw.cmd test -Dtest=DataInitializerInsectosIntegrationTest
```

## 5) Validar build completo

```bash
mvnw.cmd clean install
```

## 6) Resultado de verificacion (2026-03-11)

- `./mvnw test -Dtest=TipoInsectoControllerWebMvcTest,TipoInsectoServiceTest,DataInitializerInsectosIntegrationTest` -> BUILD SUCCESS
- `./mvnw clean install` -> BUILD SUCCESS
