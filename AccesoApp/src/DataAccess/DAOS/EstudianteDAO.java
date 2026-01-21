public class EstudianteDAO extends DataHelperSQLiteDAO <EstudianteDTO> {
    public EstudianteDAO() throws AppException {
        super(EstudianteDTO.class, "Estudiante", "IdEstudiante");
    }
}
