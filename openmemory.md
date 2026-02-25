# OpenMemory Guide

## Overview
- Java 21 + Spring Boot 3.5.9 REST API to manage album and lamina collections.
- Layered architecture: controllers/api → services → repositories → JPA entities; DTOs/mappers handle IO mapping.
- Soft delete via `active` flag plus auditing (`createdAt`, `updatedAt`) through `JpaAuditingConfig`.
- Consistent API envelope with `ApiResponseDTO` and centralized errors via `GlobalExceptionHandler`.
- Build/test with `./mvnw clean install`; run locally with `./mvnw spring-boot:run`.

## Architecture
- Persistence: Spring Data JPA repositories with derived queries (`findByActiveTrue`, `findByAlbumAndActiveTrue`).
- Domain: Album, Lamina, catalog entities with relationships; soft delete and auditing on all entities.
- Services: `AlbumService`, `LaminaService`, etc. enforce domain rules, transactional; read methods use `@Transactional(readOnly = true)`.
- Controllers: thin REST endpoints in `controllers/api`, validated inputs (`@Valid`), responses only DTOs wrapped in `ApiResponseDTO`.
- Errors: domain exceptions (`ResourceNotFoundException`, `InvalidOperationException`) handled centrally; validation uses Jakarta annotations.

## User Defined Namespaces
- [Leave blank - user populates]

## Components
- ApiResponse envelope: `ApiResponseDTO` with `success`, `message`, `data`, `timestamp` for all responses.
- Global error handling: `GlobalExceptionHandler` maps domain and validation errors to consistent responses.
- Album module: DTOs (`AlbumRequestDTO`, `AlbumBasicResponseDTO`, etc.), mapper, repository, service, controller; soft delete operations.
- Lamina module: DTOs/mappers/services for single and bulk catalog operations; uses catalog validation before inserts; soft delete support.
- Auditing config: `JpaAuditingConfig` enabling `@CreatedDate` / `@LastModifiedDate`; `DataInitializer` seeds baseline data.
- Setup web page: `src/main/resources/static/setup.html` resume cómo montar la app con prerrequisitos, comandos y URLs clave.

## Patterns
- Soft delete pattern: mark `active = false` instead of physical delete; queries prefer `findBy...ActiveTrue`.
- DTO + mapper pattern: convert requests to entities and back (`toEntity`, `toResponseDTO`, `updateEntity`).
- Controller discipline: no repository access; use `@Valid` and return `ApiResponseDTO` with mapped DTOs only.
- Transactional services: annotate service classes; mark read-only queries with `@Transactional(readOnly = true)`.
- Error handling: throw `ResourceNotFoundException` for missing resources and `InvalidOperationException` for business rule violations.
