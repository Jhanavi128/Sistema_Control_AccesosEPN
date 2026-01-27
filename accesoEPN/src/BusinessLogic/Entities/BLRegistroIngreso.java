package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.RegistroIngresoDAO;
import DataAccess.DTOs.RegistroIngresoDTO;
import Infrastructure.AppException;

public class BLRegistroIngreso {

    // Inicialización siguiendo tu estilo de atributo final
    private final FactoryBL<RegistroIngresoDTO> factory = 
            new FactoryBL<>(RegistroIngresoDAO.class);

    /**
     * Registra un ingreso en la base de datos.
     * @param idUsuario     ID del guardia que realiza el escaneo.
     * @param idEstudiante  ID del estudiante que intenta ingresar.
     * @param resultado     Resultado del acceso ('Autorizado', 'Rechazado', 'Invalido').
     */
    public void registrarIngreso(Integer idUsuario, 
                                 Integer idEstudiante, 
                                 String resultado) throws AppException {

        // Usamos el constructor de INSERT del DTO (el que tiene 3 parámetros)
        RegistroIngresoDTO dto = 
                new RegistroIngresoDTO(idUsuario, idEstudiante, resultado);

        // El factory se encarga de llamar al DAO y este al DataHelper para el INSERT
        factory.add(dto);
    }
}