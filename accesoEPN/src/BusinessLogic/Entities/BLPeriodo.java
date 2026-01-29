package BusinessLogic.Entities;

import java.util.List;
import BusinessLogic.FactoryBL;
import DataAccess.DAOs.PeriodoDAO;
import DataAccess.DTOs.PeriodoDTO;
import Infrastructure.AppException;

public class BLPeriodo {
    // Creamos el factory específico para Periodo
    private FactoryBL<PeriodoDTO> factory;

    public BLPeriodo() {
        // Inicializamos el factory con el DAO de Periodo
        this.factory = new FactoryBL<>(PeriodoDAO.class);
    }

    public PeriodoDTO getById(Integer id) throws AppException {
        // Ahora usamos getBy, que internamente llamará a readBy del DataHelper
        return factory.getBy(id);
    }

    public List<PeriodoDTO> getAll() throws AppException {
        return factory.getAll();
    }
}