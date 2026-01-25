-- Creación de la Base de Datos y Tablas para el Sistema de Control de Accesos

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user VARCHAR(50) NOT NULL UNIQUE, -- Código Único (ej: 202421164)
    pass VARCHAR(255) NOT NULL,       -- PIN (ej: 1234)
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ESTUDIANTE', 'GUARDIA')),
    foto_url VARCHAR(255),            -- Ruta a la foto del usuario
    carrera VARCHAR(100),             -- Carrera del estudiante
    nombre VARCHAR(100)               -- Nombre completo
);

-- Tabla de Accesos
CREATE TABLE IF NOT EXISTS Accesos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    hora_entrada DATETIME DEFAULT CURRENT_TIMESTAMP,
    token_qr VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE
);

-- Inserts de Prueba

-- 1. Estudiante: John López
INSERT INTO Usuarios (user, pass, rol, foto_url, carrera, nombre) 
VALUES ('202421164', '1234', 'ESTUDIANTE', 'perfil_john.jpg', 'Software', 'John López');

-- 2. Guardia
INSERT INTO Usuarios (user, pass, rol, foto_url, carrera, nombre) 
VALUES ('202421165', '4321', 'GUARDIA', NULL, NULL, 'Guardia Principal');
