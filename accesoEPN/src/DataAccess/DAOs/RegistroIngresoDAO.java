package DataAccess.DAOs;

import DataAccess.DTOs.RegistroIngresoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.sql.*;

public class RegistroIngresoDAO extends DataHelperSQLiteDAO<RegistroIngresoDTO> {

    public RegistroIngresoDAO() throws AppException {
        // Tabla: RegistroIngreso, Llave Primaria: IdRegistroIngreso
        super(RegistroIngresoDTO.class, "RegistroIngreso", "IdRegistroIngreso");
    }

    /**
     * Este método es el que usa tu Service para guardar el historial.
     * @param idQR El ID del código que se escaneó.
     * @param estadoAcceso 'Autorizado' o 'Rechazado'.
     */
    public boolean registrar(Integer idQR, String estadoAcceso) {
        // Usamos la función datetime('now') de SQLite para que la fecha sea automática
        String sql = "INSERT INTO RegistroIngreso (IdQRAcceso, EstadoAcceso, FechaIngreso) VALUES (?, ?, datetime('now'))";
        
        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idQR);
            pstmt.setString(2, estadoAcceso);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Si hay error (como que el ID no existe), lo atrapamos aquí
            System.out.println("Error al registrar en la base: " + e.getMessage());
            return false;
        }
    }
}