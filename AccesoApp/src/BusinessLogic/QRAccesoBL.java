import java.util.List;

public class QRAccesoBL {
    private QRAccesoDTO qrDTO;
    private QRAccesoDAO qrDAO;

    public QRAccesoBL() throws Exception {
        qrDAO = new QRAccesoDAO();
    }


    public List<QRAccesoDTO> getAll() throws Exception{
       List<QRAccesoDTO> lst = qrDAO.readAll();
       for(QRAccesoDTO qrAccesoDTO : lst){
            qrDTO.setCodigoqr(qrAccesoDTO.getCodigoqr().toUpperCase());
       }
       return lst;
    }
    public QRAccesoDTO getBy(int idReg) throws Exception{
        qrDTO = qrDAO.readBy(idReg);
        return qrDTO;
    }

    public boolean create(QRAccesoDTO regDTO) throws Exception{
        return qrDAO.create(regDTO);
    }

    public boolean update (QRAccesoDTO regDTO) throws Exception{
        return qrDAO.update(regDTO);
    }

    public boolean delete (int idReg) throws Exception{
        return qrDAO.delete(idReg);
    } 

    public Integer getMaxReg() throws Exception{
        return qrDAO.getMaxReg();
    }
}
