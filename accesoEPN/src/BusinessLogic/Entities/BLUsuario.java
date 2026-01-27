package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.UsuarioDAO;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;

public class BLUsuario {

    private FactoryBL<UsuarioDTO> factory =
            new FactoryBL<>(UsuarioDAO.class);

    public UsuarioDTO validar(Integer idUsuario, String password)
            throws AppException {

        UsuarioDTO user = factory.getBy(idUsuario);

        if (user == null) return null;

        if (!user.getContrasena().equals(password)) {
            return null;
        }

        return user;
    }
}
