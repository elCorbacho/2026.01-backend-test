## Context

El sitio es un backend Spring Boot que sirve contenido estático desde `src/main/resources/static`, incluyendo `index.html`, `db-estructura.html`, `pedidos.html`, etc. El texto que aparece en pantalla está escrito originalmente en español, pero varios archivos muestran caracteres mojibake (ej. “LÃ¡minas”). El navegador muestra `Láminas` cuando el HTML se sirve correctamente con UTF-8 y un `lang="es"` declarado.

## Goals / Non-Goals

**Goals:**
- Garantizar que los archivos estáticos que llegan al cliente usan UTF-8 y declaran `lang="es"` en la raíz del documento.
- Reescribir los textos clave (títulos, etiquetas, secciones) para que se muestren con los acentos correctos en español.
- Documentar la convención de idioma/encoding para futuros editores.

**Non-Goals:**
- Reescribir el frontend completo ni habilitar multi-idioma.
- Cambiar el backend o las APIs excepto para servir los recursos estáticos con la codificación adecuada.

## Decisions

1. **Estandarizar meta tags:** Todos los HTML en `src/main/resources/static` deben incluir `<meta charset="UTF-8">` y `<html lang="es">` en la cabecera. Esto asegura que el navegador interprete el contenido como UTF-8 y que los lectores de pantalla detecten el idioma.
2. **Re-guardar archivos como UTF-8:** Los archivos históricos que muestran mojibake se re-guardarán explícitamente en UTF-8 sin BOM utilizando el editor del equipo o `iconv`, evitando ediciones que cambien solo la codificación y no el contenido.
3. **Copiar-texto en español:** Se actualizarán los textos visibles (p. ej. “API Álbumes y Láminas”, titulos y descripciones) para que se escriban con acentos. Es recomendable usar un script o búsqueda (`"LÃ"`) para detectar líneas restantes con mojibake y corregirlas manualmente.
4. **Documentar el criterio:** Se agrega una nota en el README o en un documento nuevo describiendo que los assets deben guardarse como UTF-8 y en español, lo que ayuda a evitar regresiones.

## Risks / Trade-offs

- [Risk] Cambiar archivos planos puede introducir espacios invisibles o eliminar caracteres inesperadamente.
  → Mitigación: usar herramientas que comparen los bytes antes y después (por ejemplo, `git diff --word-diff` o `diff -U0`).
- [Risk] Un pipeline que reempaqueta `target` podría convertir de nuevo a otra codificación si `pom.xml` no define `encoding`.
  → Mitigación: después de los cambios ejecutar `mvn clean package` y verificar que los recursos en `target/classes/static` mantienen UTF-8 y los acentos.

## Migration Plan

1. Editar los archivos HTML estáticos encontrados en `src/main/resources/static` para que la cabecera declare UTF-8 y `lang="es"`.
2. Reescribir las cadenas más visibles (“API Álbumes y Láminas”, “Láminas”, “Catálogo”, etc.) y re-guardar los archivos asegurando la codificación UTF-8.
3. Buscar cualquier mención adicional de `Ã`/`Ã¡` en `readme.md` y otros recursos de documentación para corregirlos con la nueva convención.
4. Documentar la regla de idioma/codificación en un archivo de estilo o en el README.
5. Ejecutar `mvn clean package` y revisar `target/classes/static` para confirmar que la codificación se conserva.

## Open Questions

1. ¿Debemos automatizar la verificación de codificación (p. ej. un script que busca `Ã` o `Ã¡`), o es suficiente una revisión manual?
2. Además de los archivos HTML enlistados, ¿hay otros recursos (por ejemplo, archivos en `docs/` o `screenshots/`) que deben mantenerse en español con UTF-8?
