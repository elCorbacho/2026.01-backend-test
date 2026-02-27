-- Script de creación para la tabla presidente_chile
CREATE TABLE presidente_chile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    periodo_inicio DATE NOT NULL,
    periodo_fin DATE NULL,
    partido VARCHAR(120) NULL,
    descripcion VARCHAR(500) NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,
    is_active TINYINT(1) DEFAULT 1
);
