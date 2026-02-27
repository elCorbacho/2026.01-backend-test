# Spec: Poblar 30 registros de DemoWidget

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | DemoWidget |
| **Table** | demo_widget |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 120 |
| tipo | String | nullable, max 100 |
| active | Boolean | @Builder.Default true |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de DemoWidget

**GIVEN** la aplicación se inicia y la tabla `demo_widget` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `demo_widget` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de DemoWidget
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 120 caracteres

### REQ-003: El campo tipo ES OPCIONAL

**GIVEN** un nuevo registro de DemoWidget
**WHEN** se crea sin especificar el campo tipo
**THEN** el campo `tipo` DEBE ser null o tener máximo 100 caracteres

### REQ-004: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de DemoWidget
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `demo_widget` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Registro con tipo nulo

**GIVEN** un nuevo registro de DemoWidget
**WHEN** se crea sin especificar el campo tipo
**THEN** el campo `tipo` DEBE ser null
**AND** la operación DEBE completarse exitosamente

### EC-003: Auditoría automática de fechas

**GIVEN** un nuevo registro de DemoWidget
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
