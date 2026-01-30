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
        String sql = "SELECT e.*, " +
                "       p.Carrera as carrera, " +
                "       p.Nombre  as nombrePeriodo " +
                "FROM Estudiante e " +
                "INNER JOIN Periodo p ON e.IdPeriodo = p.IdPeriodo " +
                "WHERE e.IdUsuario = ? AND e.Estado = 'A'";

        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (Exception e) {
            throw new AppException("Error al leer estudiante con su periodo", e, getClass(), "getByUsuarioId");
        }
        return null;
    }

    public java.util.List<EstudianteDTO> searchByText(String text) throws AppException {
        String sql = "SELECT e.*, p.Carrera as carrera, p.Nombre as nombrePeriodo " +
                "FROM Estudiante e " +
                "LEFT JOIN Periodo p ON e.IdPeriodo = p.IdPeriodo " + // LEFT JOIN just in case
                "WHERE (e.Nombre LIKE ? OR e.Apellido LIKE ? OR e.CodigoUnico LIKE ?) AND e.Estado = 'A'";

        java.util.List<EstudianteDTO> list = new java.util.ArrayList<>();
        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            String q = "%" + text + "%";
            pstmt.setString(1, q);
            pstmt.setString(2, q);
            pstmt.setString(3, q);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (Exception e) {
            throw new AppException("Error buscando estudiantes", e, getClass(), "searchByText");
        }
        return list;
    }

    public EstudianteDTO getByCodigoUnicoAnyStatus(String codigo) throws AppException {
        String sql = "SELECT * FROM Estudiante WHERE CodigoUnico = ?";
        try (java.sql.PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (Exception e) {
            throw new AppException("Error buscando estudiante por codigo", e, getClass(), "getByCodigoUnicoAnyStatus");
        }
        return null;
    }

    public boolean existsByCodigoUnicoActive(String codigoUnico, Integer excludeId) throws AppException {
        String sql;
        if (excludeId != null) {
            sql = "SELECT COUNT(*) FROM Estudiante WHERE CodigoUnico = ? AND Estado = 'A' AND IdEstudiante != ?";
        } else {
            sql = "SELECT COUNT(*) FROM Estudiante WHERE CodigoUnico = ? AND Estado = 'A'";
        }

        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setString(1, codigoUnico);
            if (excludeId != null) {
                pstmt.setInt(2, excludeId);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            throw new AppException("Error verificando código único", e, getClass(), "existsByCodigoUnicoActive");
        }
        return false;
    }

    public boolean existsByCedulaActive(String cedula, Integer excludeId) throws AppException {
        String sql;
        if (excludeId != null) {
            sql = "SELECT COUNT(*) FROM Estudiante WHERE Cedula = ? AND Estado = 'A' AND IdEstudiante != ?";
        } else {
            sql = "SELECT COUNT(*) FROM Estudiante WHERE Cedula = ? AND Estado = 'A'";
        }

        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            if (excludeId != null) {
                pstmt.setInt(2, excludeId);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            throw new AppException("Error verificando cédula", e, getClass(), "existsByCedulaActive");
        }
        return false;
    }
}
