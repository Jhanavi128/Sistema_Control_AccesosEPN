package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    // Assuming SQLite for portability.
    // If using MySQL/MariaDB: "jdbc:mysql://localhost:3306/acceso_db"
    private static final String URL = "jdbc:sqlite:acceso.db";

    public static Connection conectar() throws SQLException {
        try {
            // Ensure the driver is loaded
            // Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("Error conectando a BD: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public static void inicializar() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user TEXT NOT NULL UNIQUE," +
                "pass TEXT NOT NULL," +
                "rol TEXT NOT NULL," +
                "foto_url TEXT," +
                "carrera TEXT," +
                "nombre TEXT" +
                ");";

        try (Connection conn = conectar();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuarios);
            System.out.println("Tabla Usuarios verificada.");
            insertarUsuariosPrueba(conn);
        } catch (SQLException e) {
            System.err.println("Error inicializando BD: " + e.getMessage());
        }
    }

    private static void insertarUsuariosPrueba(Connection conn) throws SQLException {
        // Use INSERT OR IGNORE (SQLite specific) to add default users without error if
        // they exist
        String sql = "INSERT OR IGNORE INTO Usuarios(user, pass, rol, nombre, carrera, foto_url) VALUES(?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // John
            pstmt.setString(1, "202421164");
            pstmt.setString(2, "1234");
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "John López");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "perfil_john.jpg");
            pstmt.addBatch();

            // Jhanavi
            pstmt.setString(1, "202421163");
            pstmt.setString(2, "5656");
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "Jhanavi Molina");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "perfil_john.jpg");
            pstmt.addBatch();

            // Diego
            pstmt.setString(1, "202421162");
            pstmt.setString(2, "1212");
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "Diego Lima");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "perfil_john.jpg");
            pstmt.addBatch();

            // Mathias
            pstmt.setString(1, "202421161");
            pstmt.setString(2, "3434");
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "Mathias Mejía");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "perfil_john.jpg");
            pstmt.addBatch();

            // Danny
            pstmt.setString(1, "202421160");
            pstmt.setString(2, "7878");
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "Danny Lanchimba");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "perfil_john.jpg");
            pstmt.addBatch();

            // Guardia
            pstmt.setString(1, "202421165");
            pstmt.setString(2, "4321");
            pstmt.setString(3, "GUARDIA");
            pstmt.setString(4, "Guardia Principal");
            pstmt.setString(5, "Seguridad");
            pstmt.setString(6, "");
            pstmt.addBatch();

            // Nuevo Usuario (Recuperado)
            pstmt.setString(1, "202421166");
            pstmt.setString(2, "2222"); // Password inferido de los logs
            pstmt.setString(3, "ESTUDIANTE");
            pstmt.setString(4, "Estudiante Nuevo");
            pstmt.setString(5, "Software");
            pstmt.setString(6, "202421166.jpg");
            pstmt.addBatch();

            pstmt.executeBatch();
            System.out.println("Usuarios de prueba verificados/insertados.");
        }
    }
}
