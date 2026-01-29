package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;

public class ConexionBD {
    // Ruta sencilla
    private static final String URL = "jdbc:sqlite:Storage/DataBase/bd_acceso_epn.sqlite";

    public static Connection conectar() {
        try {
            // Validación simple: ¿Existe el archivo en esa ruta?
            File dbFile = new File("Storage/DataBase/bd_acceso_epn.sqlite");
            if (!dbFile.exists()) {
                System.err.println("🚨 ERROR: No se encuentra la DB en: " + dbFile.getAbsolutePath());
                System.err.println("Asegúrate de ejecutar VS Code desde la carpeta 'accesoEPN'");
                return null;
            }

            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}