public class PeriodoDAO extends DataHelperSQLiteDAO <PeriodoDTO> {
    public PeriodoDAO() throws AppException {
        super(PeriodoDTO.class, "Periodo", "IdPeriodo");
    }
}
