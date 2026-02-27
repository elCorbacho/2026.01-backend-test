# Spec: Poblar 30 registros de TiendaLamina

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | TiendaLamina |
| **Table** | tienda_lamina |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 150 |
| ciudad | String | NOT NULL, max 80 |
| direccion | String | nullable, max 200 |
| telefono | String | nullable, max 30 |
| email | String | nullable, max 120 |
| fechaApertura | LocalDate | nullable |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de TiendaLamina

**GIVEN** la aplicación se inicia y la tabla `tienda_lamina` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `tienda_lamina` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 150 caracteres

### REQ-003: Cada registro DEBE tener una ciudad válida

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea el registro
**THEN** el campo `ciudad` NO DEBE ser null ni estar vacío
**AND** el campo `ciudad` DEBE tener máximo 80 caracteres

### REQ-004: El campo dirección ES OPCIONAL

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea sin especificar el campo direccion
**THEN** el campo `direccion` DEBE ser null o tener máximo 200 caracteres

### REQ-005: El campo teléfono ES OPCIONAL

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea sin especificar el campo telefono
**THEN** el campo `telefono` DEBE ser null o tener máximo 30 caracteres

### REQ-006: El campo email ES OPCIONAL

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea sin especificar el campo email
**THEN** el campo `email` DEBE ser null o tener máximo 120 caracteres

### REQ-007: El campo fecha de apertura ES OPCIONAL

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea sin especificar el campo fechaApertura
**THEN** el campo `fechaApertura` DEBE ser null

### REQ-008: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `tienda_lamina` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Registro con todos los campos opcionales nulos

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se crea solo con nombre y ciudad
**THEN** la operación DEBE completarse exitosamente
**AND** los campos opcionales DEBEN ser null

### EC-003: Fecha de apertura con diferentes formatos

**GIVEN** registros con diferentes fechas de apertura
**WHEN** se persiste el registro
**THEN** la fecha DEBE preservarse correctamente

### EC-004: Auditoría automática de fechas

**GIVEN** un nuevo registro de TiendaLamina
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
