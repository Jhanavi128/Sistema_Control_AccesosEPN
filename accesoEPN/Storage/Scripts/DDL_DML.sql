-- ========================================
-- BASE DE DATOS OPTIMIZADA
-- ========================================

DROP TABLE IF EXISTS RegistroIngreso;
DROP TABLE IF EXISTS QRAcceso;
DROP TABLE IF EXISTS Estudiante;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Periodo;

CREATE TABLE Periodo(
    IdPeriodo       INTEGER PRIMARY KEY AUTOINCREMENT,
    Carrera         TEXT NOT NULL,
    Semestre        INTEGER NOT NULL,
    Nombre          TEXT NOT NULL UNIQUE,
    FechaInicio     TEXT NOT NULL,
    FechaFin        TEXT NOT NULL,
    Estado          TEXT DEFAULT 'A',
    FechaCreacion   DATETIME DEFAULT (datetime('now','localtime')),
    FechaModifica   DATETIME DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Usuario (
    IdUsuario       INTEGER PRIMARY KEY AUTOINCREMENT,
    CodigoUnico     TEXT UNIQUE NOT NULL, -- Sigue aquí para el Login
    Contrasena      TEXT NOT NULL,
    Rol             TEXT NOT NULL CHECK (Rol IN ('Admin','Estudiante','Guardia')),
    Estado          TEXT DEFAULT 'A',
    FechaCreacion   DATETIME DEFAULT (datetime('now','localtime')),
    FechaModifica   DATETIME DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Estudiante(
    IdEstudiante     INTEGER PRIMARY KEY AUTOINCREMENT,
    IdUsuario        INTEGER NOT NULL UNIQUE,
    IdPeriodo        INTEGER NOT NULL,
    CodigoUnico      TEXT NOT NULL, -- 🔹 AGREGADO: Para mapeo directo en el DAO
    Nombre           TEXT NOT NULL,
    Apellido         TEXT NOT NULL,
    Cedula           TEXT UNIQUE NOT NULL,
    FechaNacimiento  TEXT NOT NULL,
    Sexo             TEXT CHECK (Sexo IN ('M','F')),
    Estado           TEXT DEFAULT 'A',
    FechaCreacion    DATETIME DEFAULT (datetime('now','localtime')),
    FechaModifica    DATETIME DEFAULT (datetime('now','localtime')),
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario),
    FOREIGN KEY (IdPeriodo) REFERENCES Periodo(IdPeriodo)
);

CREATE TABLE QRAcceso (
    IdQRAcceso       INTEGER PRIMARY KEY AUTOINCREMENT,
    IdUsuario        INTEGER NOT NULL,
    CodigoQR         TEXT UNIQUE NOT NULL,
    FechaExpiracion  DATETIME,
    Estado           TEXT DEFAULT 'A',
    FechaCreacion    DATETIME DEFAULT (datetime('now','localtime')),
    FechaModifica    DATETIME DEFAULT (datetime('now','localtime')),
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario)
);

CREATE TABLE RegistroIngreso(
    IdRegistroIngreso INTEGER PRIMARY KEY AUTOINCREMENT,
    IdUsuario         INTEGER NOT NULL, -- El Guardia que escanea
    IdEstudiante      INTEGER NOT NULL, -- El Estudiante que ingresa
    Resultado         TEXT CHECK (Resultado IN ('Autorizado','Rechazado','Invalido')),
    Estado            TEXT DEFAULT 'A',
    FechaCreacion     DATETIME DEFAULT (datetime('now','localtime')),
    FechaModifica     DATETIME DEFAULT (datetime('now','localtime')),
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario),
    FOREIGN KEY (IdEstudiante) REFERENCES Estudiante(IdEstudiante)
);

-- ==============================
-- INSERCIÓN DE DATOS DE PRUEBA
-- ==============================
INSERT INTO Periodo (Carrera, Semestre, Nombre, FechaInicio, FechaFin) 
VALUES ('Ingeniería en Software', 2, '2025-B','2025-10-01', '2026-03-01');

INSERT INTO Periodo (Carrera, Semestre, Nombre, FechaInicio, FechaFin) 
VALUES ('Ingeniería civil', 2, '2000-A','2025-10-01', '2026-03-01');

INSERT INTO Usuario (CodigoUnico, Contrasena, Rol) VALUES ('202210001', '1234', 'Estudiante');
INSERT INTO Usuario (CodigoUnico, Contrasena, Rol) VALUES ('GUARDIA01', '1234', 'Guardia');

-- Ahora Estudiante ya tiene su propio CodigoUnico (Redundancia controlada para optimizar lectura)
INSERT INTO Estudiante (IdUsuario, IdPeriodo, CodigoUnico, Nombre, Apellido, Cedula, FechaNacimiento, Sexo) 
VALUES (1, 1, '202210001', 'Jhanavi', 'Apellido', '1700000001', '2001-01-01', 'F');

-- 1. Insertamos el Usuario para el Login
INSERT INTO Usuario (CodigoUnico, Contrasena, Rol) 
VALUES ('202420421', '12345', 'Estudiante');

-- 2. Insertamos los datos en la tabla Estudiante
-- Explicación de los valores:
-- last_insert_rowid(): Toma el ID del usuario 'diego' que acabamos de crear.
-- 1: El IdPeriodo (asumiendo que es el de Software 2025-B que ya tenías).
-- '1799999999': Cédula inventada (debe ser única).
INSERT INTO Estudiante (IdUsuario, IdPeriodo, CodigoUnico, Nombre, Apellido, Cedula, FechaNacimiento, Sexo) 
VALUES (last_insert_rowid(), 2, '202420421', 'Diego', 'Lima', '1799999999', '2004-05-20', 'M');