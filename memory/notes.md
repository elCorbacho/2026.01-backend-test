# Generalidades del proyecto

1) API REST con Spring Boot 3.5.9 y Java 21 orientada a gestión de álbumes y láminas de colección.
2) Arquitectura en capas: controladores finos en controllers/api con respuestas via ApiResponseDTO; servicios concentran reglas; repositorios con Spring Data JPA; DTOs+mappers para entrada/salida; modelos con soft delete y auditoría.
3) Soft delete y auditoría obligatorios: campo active para bajas lógicas y campos createdAt/updatedAt gestionados por AuditingEntityListener; evitar borrados físicos.
4) Contrato de API: todas las respuestas envuelven ApiResponseDTO con success, message, data, timestamp; controladores usan @Valid y no acceden a repositorios directamente.
