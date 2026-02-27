# Spec: Poblar 30 registros de TestModel

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | TestModel |
| **Table** | test_model |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 100 |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de TestModel

**GIVEN** la aplicación se inicia y la tabla `test_model` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `test_model` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de TestModel
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 100 caracteres

### REQ-003: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de TestModel
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `test_model` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Auditoría automática de fechas

**GIVEN** un nuevo registro de TestModel
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
