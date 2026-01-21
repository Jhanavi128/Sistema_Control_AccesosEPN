-- database: storage\Databases\bd_acceso_epn.sqlite
DROP TABLE IF EXISTS RegistroIngreso;
DROP TABLE IF EXISTS QRAcceso;
DROP TABLE IF EXISTS Estudiante;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Periodo;

CREATE TABLE Periodo(
    IdPeriodo        INTEGER PRIMARY KEY AUTOINCREMENT,
    Carrera          TEXT NOT NULL,
    Semestre         INTEGER NOT NULL CHECK (Semestre BETWEEN 1 AND 10),
    Nombre           VARCHAR(15) NOT NULL UNIQUE,
    FechaInicio      TEXT NOT NULL,
    FechaFin         TEXT NOT NULL,
    Estado           VARCHAR(1) NOT NULL DEFAULT 'A',
    FechaCreacion    DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModifica    DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Usuario (
    IdUsuario       INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre          VARCHAR(100) NOT NULL,
    Apellido        VARCHAR(100) NOT NULL,
    Correo          VARCHAR(100) UNIQUE NOT NULL,
    Contrasena      VARCHAR(255) NOT NULL,
    Rol             VARCHAR(50) NOT NULL,
    Estado          INTEGER DEFAULT TRUE,
    FechaCreacion   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FechaModifica   DATETIME
);

CREATE TABLE Estudiante(
    IdEstudiante      INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPeriodo         INTEGER NOT NULL,
    Nombre            VARCHAR(15) NOT NULL,
    Apellido          VARCHAR(15) NOT NULL,
    Cedula            VARCHAR(10) NOT NULL UNIQUE,
    CodigoUnico       VARCHAR(9) NOT NULL UNIQUE,
    FechaNacimiento   TEXT NOT NULL,
    Sexo              TEXT NOT NULL CHECK (Sexo IN ('M','F')),
    Estado            VARCHAR(1) NOT NULL DEFAULT 'A',
    FechaCreacion     DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModifica     DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FOREIGN KEY (IdPeriodo) REFERENCES Periodo(IdPeriodo)
);

CREATE TABLE QRAcceso (
    IdQRAcceso       INTEGER PRIMARY KEY AUTOINCREMENT,
    IdUsuario        INTEGER NOT NULL,
    CodigoQR         VARCHAR(255) UNIQUE NOT NULL,
    Estado           INTEGER DEFAULT 1,
    FechaGeneracion  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FechaExpiracion  DATETIME,
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario)
);

CREATE TABLE RegistroIngreso(
    IdRegistroIngreso INTEGER PRIMARY KEY AUTOINCREMENT,
    IdQRAcceso        INTEGER NOT NULL,
    FechaHora         TEXT NOT NULL,
    Resultado         TEXT NOT NULL CHECK (Resultado IN ('Autorizado','Rechazado','Inválido')),
    FOREIGN KEY (IdQRAcceso) REFERENCES QRAcceso (IdQRAcceso)
);

-- 1. Insertar un Periodo Académico
INSERT INTO Periodo (Carrera, Semestre, Nombre, FechaInicio, FechaFin)
VALUES ('Software', 6, '2025-B', '2025-10-01', '2026-03-01');

-- 2. Insertar un Usuario (quien genera/valida el acceso)
INSERT INTO Usuario (Nombre, Apellido, Correo, Contrasena, Rol)
VALUES ('Danny', 'Lanchimba', 'danny.lanchimba@epn.edu.ec', '1234', 'Admin');

-- 3. Insertar un Estudiante vinculado al periodo
INSERT INTO Estudiante (IdPeriodo, Nombre, Apellido, Cedula, CodigoUnico, FechaNacimiento, Sexo)
VALUES (1, 'Danny', 'Lanchimba', '1700000000', '202210000', '2000-01-01', 'M');

-- 4. Insertar el QR de prueba vinculado al Usuario 1
INSERT INTO QRAcceso (IdUsuario, CodigoQR, Estado)
VALUES (1, 'EPN-2026-ABC', 1);