public class UsuarioDAO extends DataHelperSQLiteDAO <UsuarioDTO> {
    public UsuarioDAO() throws AppException {
        super(UsuarioDTO.class, "Usuario", "IdUsuario");
    }
}
