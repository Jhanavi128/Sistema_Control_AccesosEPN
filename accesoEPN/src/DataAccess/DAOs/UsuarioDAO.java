package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class UsuarioDAO extends DataHelperSQLiteDAO<UsuarioDTO> {

    public UsuarioDAO() throws AppException {
        
        super(UsuarioDTO.class, "Usuario", "IdUsuario");
    }
}
