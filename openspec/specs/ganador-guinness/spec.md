# Spec: Poblar 30 registros de GanadorGuinness

## 1. Overview

| Campo | Valor |
|-------|-------|
| **Entity** | GanadorGuinness |
| **Table** | winner_guinness |
| **Change** | populate-database-30-records |
| **Priority** | HIGH |

## 2. Entity Structure

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | @Id, @GeneratedValue AUTO_INCREMENT |
| nombre | String | NOT NULL, max 150 |
| categoria | String | NOT NULL, max 120 |
| record | String | NOT NULL, max 255 |
| anio | Integer | NOT NULL |
| createdAt | LocalDateTime | @CreatedDate (auto) |
| updatedAt | LocalDateTime | @LastModifiedDate (auto) |
| active | Boolean | @Builder.Default true |

## 3. Requirements

### REQ-001: La base de datos DEBE contener exactamente 30 registros de GanadorGuinness

**GIVEN** la aplicación se inicia y la tabla `ganador_guinness` está vacía
**WHEN** el DataInitializer ejecuta la población de datos
**THEN** la tabla `ganador_guinness` DEBE contener exactamente 30 registros

### REQ-002: Cada registro DEBE tener un nombre válido

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se crea el registro
**THEN** el campo `nombre` NO DEBE ser null ni estar vacío
**AND** el campo `nombre` DEBE tener máximo 150 caracteres

### REQ-003: Cada registro DEBE tener una categoría válida

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se crea el registro
**THEN** el campo `categoria` NO DEBE ser null ni estar vacío
**AND** el campo `categoria` DEBE tener máximo 120 caracteres

### REQ-004: Cada registro DEBE tener un record válido

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se crea el registro
**THEN** el campo `record` NO DEBE ser null ni estar vacío
**AND** el campo `record` DEBE tener máximo 255 caracteres

### REQ-005: Cada registro DEBE tener un año válido

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se crea el registro
**THEN** el campo `anio` NO DEBE ser null
**AND** el campo `anio` DEBE ser un valor entre 1900 y 2030

### REQ-006: Todos los registros DEBEN estar activos por defecto

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se crea el registro sin especificar el campo active
**THEN** el campo `active` DEBE ser true por defecto

### REQ-007: Los registros DEBEN incluir datos diversos de récords Guinness

**GIVEN** la tabla `ganador_guinness` contiene 30 registros
**WHEN** se consultan los registros
**THEN** DEBE haber variety las en categorías (música, deportes, ciencia.)
**AND**, etc DEBE haber variety en los años (iferentes décadasd)

## 4. Edge Cases

### EC-001: No inserir registros duplicados

**GIVEN** la tabla `ganador_guinness` ya contiene 30 registros
**WHEN** el DataInitializer se ejecuta nuevamente
**THEN** NO DEBE insertar registros adicionales
**AND** la tabla DEBE mantener exactamente 30 registros

### EC-002: Manejo de caracteres especiales en nombres

**GIVEN** un registro con caracteres especiales (ñ, acentos, etc.)
**WHEN** se persiste el registro
**THEN** los caracteres especiales DEBEN preservarse correctamente

### EC-003ía automática: Auditor de fechas

**GIVEN** un nuevo registro de GanadorGuinness
**WHEN** se registro
**TH persiste elEN** el campo `createdAt` DEBE ser generado automáticamente
**AND** el campo `updatedAt` DEBE ser generado automáticamente
