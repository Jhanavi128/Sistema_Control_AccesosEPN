import java.util.List;

public class UsuarioBL {
    private UsuarioDTO usDTO;
    private UsuarioDAO usDAO;

    public UsuarioBL() throws Exception {
        usDAO = new UsuarioDAO();
    }


    public List<UsuarioDTO> getAll() throws Exception{
       List<UsuarioDTO> lst = usDAO.readAll();
       for(UsuarioDTO usuarioDTO : lst){
            usDTO.setUsuario(usuarioDTO.getUsuario().toUpperCase());
       }
       return lst;
    }
    public UsuarioDTO getBy(int idReg) throws Exception{
        usDTO = usDAO.readBy(idReg);
        return usDTO;
    }

    public boolean create(UsuarioDTO regDTO) throws Exception{
        return usDAO.create(regDTO);
    }

    public boolean update (UsuarioDTO regDTO) throws Exception{
        return usDAO.update(regDTO);
    }

    public boolean delete (int idReg) throws Exception{
        return usDAO.delete(idReg);
    } 

    public Integer getMaxReg() throws Exception{
        return usDAO.getMaxReg();
    }
}
