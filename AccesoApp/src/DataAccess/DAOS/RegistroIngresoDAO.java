public class RegistroIngresoDAO extends DataHelperSQLiteDAO <RegistroIngresoDTO>{
    public RegistroIngresoDAO() throws AppException {
        super(RegistroIngresoDTO.class, "RegistroIngreso", "IdIngreso");
    }
}
