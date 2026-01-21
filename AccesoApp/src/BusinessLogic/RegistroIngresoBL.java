import java.util.List;

public class RegistroIngresoBL {
    private RegistroIngresoDTO riDTO;
    private RegistroIngresoDAO riDAO;

    public RegistroIngresoBL() throws Exception {
        riDAO = new RegistroIngresoDAO();
    }


    public List<RegistroIngresoDTO> getAll() throws Exception{
       List<RegistroIngresoDTO> lst = riDAO.readAll();
       for(RegistroIngresoDTO registroIngresoDTO : lst){
            riDTO.setFechaHora(registroIngresoDTO.getFechaHora().toUpperCase());
       }
       return lst;
    }
    public RegistroIngresoDTO getBy(int idReg) throws Exception{
        riDTO = riDAO.readBy(idReg);
        return riDTO;
    }

    public boolean create(RegistroIngresoDTO regDTO) throws Exception{
        return riDAO.create(regDTO);
    }

    public boolean update (RegistroIngresoDTO regDTO) throws Exception{
        return riDAO.update(regDTO);
    }

    public boolean delete (int idReg) throws Exception{
        return riDAO.delete(idReg);
    } 

    public Integer getMaxReg() throws Exception{
        return riDAO.getMaxReg();
    }
}
