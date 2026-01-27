package DataAccess.DAOs;

import DataAccess.DTOs.EstudianteDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteDAO extends DataHelperSQLiteDAO<EstudianteDTO> {

    public EstudianteDAO() throws AppException {
        super(EstudianteDTO.class, "Estudiante", "IdEstudiante");
    }

    public EstudianteDTO getByUsuarioId(Integer idUsuario) throws AppException {
        // Usamos JOIN para traer datos de la tabla Periodo usando el IdPeriodo del estudiante
        String sql = "SELECT e.*, " +
                     "       p.Carrera as carrera, " +           // Alias igual al nombre en el DTO
                     "       p.Nombre  as nombrePeriodo " +      // Alias igual al nombre en el DTO
                     "FROM Estudiante e " +
                     "INNER JOIN Periodo p ON e.IdPeriodo = p.IdPeriodo " +
                     "WHERE e.IdUsuario = ? AND e.Estado = 'A'";

        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // mapResultSetToEntity llenará automáticamente carrera y nombrePeriodo
                    // porque ahora forman parte del ResultSet gracias al JOIN
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (Exception e) {
            throw new AppException("Error al leer estudiante con su periodo", e, getClass(), "getByUsuarioId");
        }
        return null;
    }
}