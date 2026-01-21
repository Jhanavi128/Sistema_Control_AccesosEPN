import java.util.List;

public class PeriodoBL {
    private PeriodoDTO prDTO;
    private PeriodoDAO prDAO;

    public PeriodoBL() throws Exception {
        prDAO = new PeriodoDAO();
    }


    public List<PeriodoDTO> getAll() throws Exception{
       List<PeriodoDTO> lst = prDAO.readAll();
       for(PeriodoDTO periodoDTO : lst){
            prDTO.setNombre(periodoDTO.getNombre().toUpperCase());
       }
       return lst;
    }
    public PeriodoDTO getBy(int idReg) throws Exception{
        prDTO = prDAO.readBy(idReg);
        return prDTO;
    }

    public boolean create(PeriodoDTO regDTO) throws Exception{
        return prDAO.create(regDTO);
    }

    public boolean update (PeriodoDTO regDTO) throws Exception{
        return prDAO.update(regDTO);
    }

    public boolean delete (int idReg) throws Exception{
        return prDAO.delete(idReg);
    } 

    public Integer getMaxReg() throws Exception{
        return prDAO.getMaxReg();
    }
}
