package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.RegistroIngresoDAO;
import DataAccess.DTOs.RegistroIngresoDTO;
import Infrastructure.AppException;

public class BLRegistroIngreso {

    private final FactoryBL<RegistroIngresoDTO> factory =
            new FactoryBL<>(RegistroIngresoDAO.class);

    public void registrarIngreso(Integer idUsuario,
                                 Integer idQRAcceso,
                                 String resultado) throws AppException {

        RegistroIngresoDTO dto =
                new RegistroIngresoDTO(idUsuario, idQRAcceso, resultado);

        factory.add(dto);
    }
}
