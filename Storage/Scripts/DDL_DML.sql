-- database: storage\Databases\bd_acceso_epn.sqlite
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS QRAcceso;
CREATE TABLE Usuario (
    IdUsuario       INT PRIMARY KEY AUTO_INCREMENT,
    Nombre          VARCHAR(100) NOT NULL,
    Apellido        VARCHAR(100) NOT NULL,
    Correo          VARCHAR(100) UNIQUE NOT NULL,
    Contrasena      VARCHAR(255) NOT NULL,
    Rol             VARCHAR(50)  NOT NULL,
    Estado          BOOLEAN      DEFAULT TRUE,
    FechaCreacion   DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FechaModifica   DATETIME
);
CREATE TABLE QRAcceso (
    IdQRAcceso      INT PRIMARY KEY AUTO_INCREMENT,
    IdUsuario       INT NOT NULL,
    CodigoQR        VARCHAR(255) UNIQUE NOT NULL,
    Estado          BOOLEAN      DEFAULT TRUE,
    FechaGeneracion DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FechaExpiracion DATETIME,
    FOREIGN KEY (IdUsuario) REFERENCES Usuario(IdUsuario)
);
