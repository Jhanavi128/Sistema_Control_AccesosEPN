package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;

public class ConexionBD {
    // Ruta unificada con AppConfig
    private static final String URL = "jdbc:sqlite:accesoEPN/Storage/DataBase/bd_acceso_epn.sqlite";

    public static Connection conectar() {
        try {
            // Validación: ¿Existe el archivo en esa ruta?
            File dbFile = new File("accesoEPN/Storage/DataBase/bd_acceso_epn.sqlite");
            if (!dbFile.exists()) {
                System.err.println("ERROR: No se encuentra la base de datos en: " + dbFile.getAbsolutePath());
                System.err.println("Asegúrate de ejecutar desde el directorio raíz del proyecto");
                return null;
            }

            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}