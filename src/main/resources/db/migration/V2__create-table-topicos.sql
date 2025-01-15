CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_de_creacion DATETIME NOT NULL,
    status BOOLEAN NOT NULL,
    autor_id BIGINT,
    curso VARCHAR(255),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
