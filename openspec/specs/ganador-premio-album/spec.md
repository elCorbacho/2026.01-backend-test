# Spec: Poblar 30 registros de GanadorPremioAlbum

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | GanadorPremioAlbum |
| **Table** | winner_premio_album |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| artista | String | NOT NULL, max 120 |
| album | String | NOT NULL, max 150 |
| premio | String | NOT NULL, max 120 |
| anio | Integer | NOT NULL |
| genero | String | nullable, max 80 |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de GanadorPremioAlbum

**GIVEN** la aplicación se inicia y la tabla `ganador_premio_album` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `ganador_premio_album` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un artista válido

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro
**THEN** el campo `artista` NO DEBE ser null ni estar vacío
**AND** el campo `artista` DEBE tener máximo 120 caracteres

### REQ-003: Cada registro DEBE tener un nombre de álbum válido

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro
**THEN** el campo `album` NO DEBE ser null ni estar vacío
**AND** el campo `album` DEBE tener máximo 150 caracteres

### REQ-004: Cada registro DEBE tener un premio válido

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro
**THEN** el campo `premio` NO DEBE ser null ni estar vacío
**AND** el campo `premio` DEBE tener máximo 120 caracteres

### REQ-005: Cada registro DEBE tener un año válido

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro
**THEN** el campo `anio` NO DEBE ser null
**AND** el campo `anio` DEBE ser un valor entre 1950 y 2030

### REQ-006: El campo género ES OPCIONAL

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro sin especificar el campo genero
**THEN** el campo `genero` DEBE ser null o tener máximo 80 caracteres

### REQ-007: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `ganador_premio_album` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Registro con género nulo

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se crea sin especificar el campo genero
**THEN** el campo `genero` DEBE ser null
**AND** la operación DEBE completarse exitosamente

### EC-003: Auditoría automática de fechas

**GIVEN** un nuevo registro de GanadorPremioAlbum
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
