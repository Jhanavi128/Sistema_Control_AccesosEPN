package DataAccess.Helpers;

import Infrastructure.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SchemaMigrator {

    public static void checkAndMigrate() {
        String url = AppConfig.DATABASE;
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Verificar si existe la columna FotoPath en Estudiante
            boolean exists = false;
            try (ResultSet rs = stmt.executeQuery("PRAGMA table_info(Estudiante)")) {
                while (rs.next()) {
                    String colName = rs.getString("name");
                    if ("FotoPath".equalsIgnoreCase(colName)) {
                        exists = true;
                        break;
                    }
                }
            }

            if (!exists) {
                System.out.println("Migrando base de datos: Agregando columna FotoPath a Estudiante...");
                stmt.execute("ALTER TABLE Estudiante ADD COLUMN FotoPath TEXT DEFAULT 'default.png'");
                System.out.println("Migración exitosa.");
            } else {
                System.out.println("Base de datos al día: FotoPath ya existe.");
            }

        } catch (Exception e) {
            System.err.println("Error en migración de esquema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        checkAndMigrate();
    }
}
