package DataAccess.DAOs;

import DataAccess.DTOs.EstudianteDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteDAO extends DataHelperSQLiteDAO<EstudianteDTO> {

    public EstudianteDAO() throws AppException {
        // Tabla: Estudiante, PK: IdEstudiante
        super(EstudianteDTO.class, "Estudiante", "IdEstudiante");
    }

    /**
     * Obtiene los datos del estudiante y luego su código único de la tabla Usuario.
     * Esta técnica evita errores de mapeo en JOINs complejos.
     */
    public EstudianteDTO getByUsuarioId(Integer idUsuario) throws AppException {
    String sql = "SELECT * FROM Estudiante WHERE IdUsuario = ? AND Estado = 'A'";
    try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
        pstmt.setInt(1, idUsuario);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                // La magia de tu DataHelper ahora funcionará solita 
                // porque existe la columna "CodigoUnico" en el ResultSet
                return mapResultSetToEntity(rs);
            }
        }
    } catch (Exception e) {
        throw new AppException("Error al leer estudiante", e, getClass(), "getByUsuarioId");
    }
    return null;
}
}