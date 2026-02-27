# Spec: Poblar 30 registros de EmpresaInsumos

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | EmpresaInsumos |
| **Table** | empresa_insumos |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 150 |
| rubro | String | NOT NULL, max 120 |
| contacto | String | nullable, max 120 |
| telefono | String | nullable, max 30 |
| email | String | nullable, max 120 |
| sitioWeb | String | nullable, max 200 |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de EmpresaInsumos

**GIVEN** la aplicación se inicia y la tabla `empresa_insumos` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `empresa_insumos` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 150 caracteres

### REQ-003: Cada registro DEBE tener un rubro válido

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea el registro
**THEN** el campo `rubro` NO DEBE ser null ni estar vacío
**AND** el campo `rubro` DEBE tener máximo 120 caracteres

### REQ-004: El campo contacto ES OPCIONAL

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea sin especificar el campo contacto
**THEN** el campo `contacto` DEBE ser null o tener máximo 120 caracteres

### REQ-005: El campo teléfono ES OPCIONAL

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea sin especificar el campo telefono
**THEN** el campo `telefono` DEBE ser null o tener máximo 30 caracteres

### REQ-006: El campo email ES OPCIONAL

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea sin especificar el campo email
**THEN** el campo `email` DEBE ser null o tener máximo 120 caracteres

### REQ-007: El campo sitio web ES OPCIONAL

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea sin especificar el campo sitioWeb
**THEN** el campo `sitioWeb` DEBE ser null o tener máximo 200 caracteres

### REQ-008: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `empresa_insumos` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Registros con todos los campos opcionales nulos

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se crea solo con nombre y rubro
**THEN** la operación DEBE completarse exitosamente
**AND** los campos opcionales DEBEN ser null

### EC-003: Auditoría automática de fechas

**GIVEN** un nuevo registro de EmpresaInsumos
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
