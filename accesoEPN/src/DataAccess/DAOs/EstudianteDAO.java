package DataAccess.DAOs;

import DataAccess.DTOs.EstudianteDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class EstudianteDAO extends DataHelperSQLiteDAO<EstudianteDTO> {
    public EstudianteDAO() throws AppException {
        // Tabla: Estudiante, PK: IdEstudiante
        super(EstudianteDTO.class, "Estudiante", "IdEstudiante");
    }
}