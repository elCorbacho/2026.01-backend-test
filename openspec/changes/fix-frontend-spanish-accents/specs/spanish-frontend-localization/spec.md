## ADDED Requirements

### Requirement: Frontend static pages declare Spanish language and UTF-8
Todos los HTML servidos desde `src/main/resources/static` o su versión empaquetada en `target/classes/static` SHALL incluir `<meta charset="UTF-8">` en la cabecera y el elemento `<html>` SHALL contener el atributo `lang="es"`.

#### Scenario: Meta tags en index.html
- **WHEN** un navegador solicita `/` o se abre `src/main/resources/static/index.html`
- **THEN** el documento tiene `<html lang="es">` y `<meta charset="UTF-8">` en la cabecera, asegurando que el navegador interprete correctamente los caracteres y que los lectores detecten el idioma español.

### Requirement: Texto visible en español con acentos correctos
Los títulos, subtítulos y etiquetas visibles al usuario (p.ej. “API Álbumes y Láminas”, “Catálogo”, “Láminas”, “Álbum”) SHALL escribirse con los acentos correctos para el español y no contener secuencias como “Ã¡” o “Ã±”.

#### Scenario: Titulares del hero y secciones principales
- **WHEN** se visualiza el hero y las secciones del `index.html` y `db-estructura.html`
- **THEN** los textos muestran “Álbumes”, “Láminas”, “Catálogo”, “Base de Datos” con acentos/diacríticos correctos, sin mojibake (por ejemplo, no debe aparecer “LÃ¡minas”).

### Requirement: Documentación de estilo de idioma
La documentación del proyecto (README o documento específico) SHALL incluir una nota que indique el uso obligatorio de UTF-8 y la escritura en español con acentos, para que cualquier contribución futura mantenga esta convención.

#### Scenario: Revisión de README/estilo
- **WHEN** un desarrollador lee el README.md o el documento de estilo
- **THEN** encuentra la sección que indica que los assets estáticos deben guardarse en UTF-8 y que las cadenas públicas se redactan en español con los acentos apropiados.
