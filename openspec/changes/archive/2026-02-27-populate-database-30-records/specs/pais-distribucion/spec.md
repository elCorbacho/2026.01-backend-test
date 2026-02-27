# Spec: Poblar 30 registros de PaisDistribucion

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | PaisDistribucion |
| **Table** | pais_distribucion |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 100 |
| codigoIso | String | nullable, max 3 |
| descripcion | String | nullable, max 500 |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de PaisDistribucion

**GIVEN** la aplicación se inicia y la tabla `pais_distribucion` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `pais_distribucion` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de PaisDistribucion
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 100 caracteres

### REQ-003: El campo código ISO ES OPCIONAL

**GIVEN** un nuevo registro de PaisDistribucion
**WHEN** se crea sin especificar el campo codigoIso
**THEN** el campo `codigoIso` DEBE ser null o tener máximo 3 caracteres

### REQ-004: El campo descripción ES OPCIONAL

**GIVEN** un nuevo registro de PaisDistribucion
**WHEN** se crea sin especificar el campo descripcion
**THEN** el campo `descripcion` DEBE ser null o tener máximo 500 caracteres

### REQ-005: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de PaisDistribucion
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `pais_distribucion` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Código ISO en diferentes formatos

**GIVEN** registros con diferentes formatos de código ISO (2 y 3 caracteres)
**WHEN** se persiste el registro
**THEN** el código ISO DEBE preservarse correctamente

### EC-003: Auditoría automática de fechas

**GIVEN** un nuevo registro de PaisDistribucion
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
