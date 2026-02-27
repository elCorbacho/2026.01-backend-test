# Spec: Poblar 30 registros de GanadorAlbum

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | GanadorAlbum |
| **Table** | winner_album |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| album | Album | @ManyToOne, NOT NULL |
| artista | String | NOT NULL, max 120 |
| premio | String | NOT NULL, max 120 |
| anio | Integer | NOT NULL |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de GanadorAlbum

**GIVEN** la aplicación se inicia y la tabla `ganador_album` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `ganador_album` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener una referencia válida a Album

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se crea el registro
**THEN** el campo `album` NO DEBE ser null
**AND** el `album` DEBE existir en la tabla `album`

### REQ-003: Cada registro DEBE tener un artista válido

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se crea el registro
**THEN** el campo `artista` NO DEBE ser null ni estar vacío
**AND** el campo `artista` DEBE tener máximo 120 caracteres

### REQ-004: Cada registro DEBE tener un premio válido

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se crea el registro
**THEN** el campo `premio` NO DEBE ser null ni estar vacío
**AND** el campo `premio` DEBE tener máximo 120 caracteres

### REQ-005: Cada registro DEBE tener un año válido

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se crea el registro
**THEN** el campo `anio` NO DEBE ser null
**AND** el campo `anio` DEBE ser un valor entre 1950 y 2030

### REQ-006: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

### REQ-007: Los Albumes necesarios DEBEN existir antes de poblar GanadorAlbum

**GIVEN** la tabla `album` contiene registros existentes
**WHEN** se ejecutan los DataInitializers de Album y GanadorAlbum
**THEN** los Albumes necesarios DEBEN ser creados primero
**AND** los GanadorAlbum DEBEN referenciar Albumes válidos

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `ganador_album` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Manejo de relaciones lazy

**GIVEN** se consulta un GanadorAlbum
**WHEN** se accede al campo `album`
**THEN** la relación DEBE cargarse correctamente (LAZY con fetch)

### EC-003: Auditoría automática de fechas

**GIVEN** un nuevo registro de GanadorAlbum
**WHEN** se persiste el registro
**THEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
