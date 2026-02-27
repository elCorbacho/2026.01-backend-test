## Context

El frontend estático se encuentra dentro de `src/main/resources/static/index.html`, pero muchos navegadores muestran caracteres como "Ãº" o "Ã­" porque la respuesta se interpreta como Latin-1 antes de que el usuario pueda ver el `<meta charset>`. El atributo `lang="es-CL"` ya existe, pero no hay ninguna garantía en los headers HTTP de que se sirva con `charset=UTF-8` o `Content-Language: es-CL`.

## Goals / Non-Goals

**Goals:**
- Asegurar que los recursos estáticos se entregan con `charset=UTF-8` de forma consistente y que el documento declara claramente el idioma español de Chile.
- Mantener el texto acentuado (por ejemplo "Enlaces útiles", "Guía") con caracteres reales en lugar de secuencias rotas para que se renderice correctamente en ambientes locales y en producción.

**Non-Goals:**
- Rehacer el diseño visual o el contenido textual más allá de corregir los acentos y los headers de idioma.
- Habilitar internacionalización completa con múltiples idiomas.

## Decisions
- **Metadatos en `index.html`**: Agregaremos otro `<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />` junto al `<meta charset="UTF-8">` y un `<meta http-equiv="Content-Language" content="es-CL" />` para que los navegadores reconozcan el encoding/idioma tan pronto se parsea el head.
- **Filtro de encoding**: Registraremos un `CharacterEncodingFilter` en `WebConfig` con `forceEncoding(true)` y `encoding=UTF-8`, y lo situaremos con alta prioridad (orden `Ordered.HIGHEST_PRECEDENCE`) para cubrir incluso recursos estáticos servidos por Spring.
- **Header de idioma**: Añadiremos un `OncePerRequestFilter` o reutilizaremos el filtro anterior para fijar el header `Content-Language` en `es-CL` para todas las respuestas (además de usar `LocaleResolver` fijo) para reforzar el idioma solicitado.

## Risks / Trade-offs
- [Risk] Cambiar el filtro de encoding en un proyecto existente puede entrar en conflicto con otras configuraciones de filtros ordenados (logging, seguridad). → Mitigación: registrar la nueva política con orden `Ordered.HIGHEST_PRECEDENCE` y no eliminar los filtros existentes.
- [Risk] Forzar `Content-Language` en todas las respuestas puede sorprender a clientes que consumen la API REST y esperan idioma dinámico. → Mitigación: solo aplicamos el header desde el filtro cuando no existe uno diferente (por ejemplo, si ya lo establecen los consumidores) y mantenemos el resolver fijado en `es-CL`.

## Migration Plan
1. Desplegar sin cambios y validar que las cabeceras no incluyen `charset=UTF-8` y el texto se ve mal (sitio de prueba). 2. Aplicar los cambios y redeploy para confirmar que ahora `curl -I /` muestra `charset=UTF-8` y `Content-Language: es-CL`. 3. Incrementar la versión del release si se controla mediante pipeline, confirmando que las páginas estáticas siguen rindiendo.

## Open Questions
- Ninguna por ahora.
