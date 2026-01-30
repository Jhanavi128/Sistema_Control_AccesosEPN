package DataAccess.DAOs;

import DataAccess.DTOs.RegistroIngresoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class RegistroIngresoDAO extends DataHelperSQLiteDAO<RegistroIngresoDTO> {

    /**
     * Constructor que inicializa el Helper para la tabla RegistroIngreso
     * 
     * @throws AppException si ocurre un error de acceso a datos
     */
    public RegistroIngresoDAO() throws AppException {
        /**
         * * Parámetros de super:
         * 1. La clase DTO para el mapeo por reflexión
         * 2. El nombre exacto de la tabla en SQLite
         * 3. El nombre exacto de la Primary Key
         */
        super(RegistroIngresoDTO.class, "RegistroIngreso", "IdRegistroIngreso");

    }

    public void deleteAllHistory() throws AppException {
        String sql = "DELETE FROM RegistroIngreso";
        try (java.sql.PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException("Error eliminando historial", e, getClass(), "deleteAllHistory");
        }
    }
}