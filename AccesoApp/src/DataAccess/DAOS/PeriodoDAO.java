package DataAccess.DAOs;

import DataAccess.DTOs.PeriodoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class PeriodoDAO extends DataHelperSQLiteDAO<PeriodoDTO> {
    public PeriodoDAO() throws AppException {
        super(PeriodoDTO.class, "Periodo", "IdPeriodo");
    }
}
