package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class UsuarioDAO extends DataHelperSQLiteDAO<UsuarioDTO> {

    public UsuarioDAO() throws AppException {

        super(UsuarioDTO.class, "Usuario", "IdUsuario");
    }

    public UsuarioDTO getByCodigoUnicoAnyStatus(String codigo) throws AppException {
        String sql = "SELECT * FROM Usuario WHERE CodigoUnico = ?";
        try (java.sql.PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (Exception e) {
            throw new AppException("Error buscando usuario por codigo", e, getClass(), "getByCodigoUnicoAnyStatus");
        }
        return null;
    }
}
