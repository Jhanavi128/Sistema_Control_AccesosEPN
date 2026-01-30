package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.EstudianteDAO;
import DataAccess.DTOs.EstudianteDTO;

public abstract class Estudiante {

    protected FactoryBL<EstudianteDTO> factory =
            new FactoryBL<>(EstudianteDAO.class);

    public EstudianteDTO data = new EstudianteDTO();
}
