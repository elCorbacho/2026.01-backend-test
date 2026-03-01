-- Índices requeridos para consultas de album (year, active, nombre)
-- Aplicar en entornos donde la app use ddl-auto=validate.

CREATE INDEX idx_album_release_year ON album (release_year);
CREATE INDEX idx_album_is_active ON album (is_active);
CREATE INDEX idx_album_nombre ON album (nombre);
