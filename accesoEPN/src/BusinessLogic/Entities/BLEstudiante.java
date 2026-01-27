package BusinessLogic.Entities;

import java.util.List;

import DataAccess.DTOs.EstudianteDTO;
import Infrastructure.AppException;

public class BLEstudiante extends Estudiante {

    public EstudianteDTO getById(Integer id) throws AppException {
        data = factory.getBy(id);
        return data;
    }

    public EstudianteDTO getByUsuarioId(Integer idUsuario) throws AppException {
        return factory.getAll()
                .stream()
                .filter(e -> e.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public List<EstudianteDTO> getAll() throws AppException {
        return factory.getAll();
    }
    
    public EstudianteDTO getByCodigoUnico(String codigoUnico) throws AppException {
    return factory.getAll()
            .stream()
            .filter(e -> e.getCodigoUnico().equals(codigoUnico))
            .findFirst()
            .orElse(null);
}

}
