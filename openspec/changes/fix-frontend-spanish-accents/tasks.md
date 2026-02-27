## 1. Encoding audit and metadata

- [x] 1.1 Confirm cada HTML en `src/main/resources/static` (index.html, db-estructura.html, pedidos.html, etc.) tiene `<html lang="es">` y `<meta charset="UTF-8">` en la cabecera; actualiza las plantillas que lo requieran.

## 2. Spanish copy correction

- [x] 2.1 Reemplaza el texto con caracteres mojibake (ej. “Ã¡”, “Ã±”, “LÃ¡minas”) por las frases en español correcto (“Láminas”, “Álbum”, “Catálogo”) en los HTML estáticos identificados.
- [x] 2.2 Guarda los archivos corregidos explícitamente en UTF-8 (sin BOM) y ejecuta `mvn clean package` para verificar que `target/classes/static` también contiene texto con acentos correctos.

## 3. Documentation

- [x] 3.1 Añade una sección al README u otro documento de estilo que indique que los assets estáticos deben mantenerse en UTF-8 y redactarse en español con los acentos apropiados, haciendo referencia al cambio y al nuevo spec.

## 4. Validation

- [x] 4.1 Documenta brevemente en el commit o en el ticket cómo se verificó la corrección (por ejemplo, inspeccionando `target/classes/static/index.html` después de compilar y confirmando que no aparece `Ã` en los textos principales).
