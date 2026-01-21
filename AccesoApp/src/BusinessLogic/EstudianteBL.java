import java.util.List;

public class EstudianteBL {
    private EstudianteDTO esDTO;
    private EstudianteDAO esDAO;

    public EstudianteBL() throws Exception {
        esDAO = new EstudianteDAO();
    }


    public List<EstudianteDTO> getAll() throws Exception{
       List<EstudianteDTO> lst = esDAO.readAll();
       for(EstudianteDTO estudianteDTO : lst){
            esDTO.setNombre(estudianteDTO.getNombre().toUpperCase());
       }
       return lst;
    }
    public EstudianteDTO getBy(int idReg) throws Exception{
        esDTO = esDAO.readBy(idReg);
        return esDTO;
    }

    public boolean create(EstudianteDTO regDTO) throws Exception{
        return esDAO.create(regDTO);
    }

    public boolean update (EstudianteDTO regDTO) throws Exception{
        return esDAO.update(regDTO);
    }

    public boolean delete (int idReg) throws Exception{
        return esDAO.delete(idReg);
    } 

    public Integer getMaxReg() throws Exception{
        return esDAO.getMaxReg();
    }
}
