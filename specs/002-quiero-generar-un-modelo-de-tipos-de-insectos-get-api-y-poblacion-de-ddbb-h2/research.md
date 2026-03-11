# Phase 0 Research - Catalogo de tipos de insectos

## Decision 1: Reutilizar patron de catalogo simple con endpoints de solo lectura

- Decision: Implementar el dominio de insectos siguiendo el mismo patron usado en catalogos existentes del proyecto (entidad + repository + service + controller), limitando alcance a operaciones de consulta.
- Rationale: El feature solicita modelo, GET API y poblacion inicial; no requiere operaciones de escritura para cumplir el objetivo.
- Alternatives considered:
  - Incluir CRUD completo desde inicio: descartado por ampliar alcance sin valor inmediato para el caso solicitado.
  - Exponer solo listado sin detalle: descartado porque el spec exige consulta por identificador.

## Decision 2: Seed idempotente en inicializador de datos existente

- Decision: Agregar poblacion inicial de tipos de insectos en el flujo de inicializacion ya existente, con regla idempotente para no duplicar registros.
- Rationale: El proyecto ya utiliza inicializadores con chequeo previo de existencia (`count` o busqueda), lo que reduce riesgo de duplicados en reinicios.
- Alternatives considered:
  - SQL de inicializacion externa: descartado por menor alineacion con convencion actual del repositorio.
  - Seed no idempotente: descartado por riesgo de crecimiento no controlado de datos de prueba.

## Decision 3: Mantener envelope y errores estandar del proyecto

- Decision: Documentar respuestas de exito/error en formato `ApiResponseDTO` y usar excepciones custom para detalle no encontrado y entradas invalidas.
- Rationale: La constitucion del proyecto exige consistencia de respuesta y prohibe excepciones genericas.
- Alternatives considered:
  - Respuestas planas sin envelope: descartado por romper contrato transversal de API.
  - Errores genericos de framework sin codigo: descartado por no cumplir estandar de manejo centralizado.

## Decision 4: Estrategia de pruebas en tres niveles

- Decision: Planificar pruebas de controller (`@WebMvcTest`), service (Mockito) e integracion (`@SpringBootTest`) enfocadas en listado, detalle, no encontrado y seed inicial.
- Rationale: Cobertura minima requerida por constitucion y coherente con patrones de pruebas ya presentes en el repositorio.
- Alternatives considered:
  - Solo pruebas de integracion: descartado por menor aislamiento de fallos.
  - Solo pruebas unitarias: descartado por no validar wiring real ni inicializacion de datos.
