-- Migración V2: Agregar índices para mejorar el rendimiento de búsquedas
ALTER TABLE ejercicio ADD COLUMN descripcion TEXT;

CREATE INDEX idx_ejercicio_grupo_muscular ON ejercicio(grupo_muscular);
CREATE INDEX idx_rutina_id_usuario ON rutina(id_usuario);
CREATE INDEX idx_usuario_estado ON usuario(estado);