package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.RegistroIngresoDAO;
import DataAccess.DTOs.RegistroIngresoDTO;
import Infrastructure.AppException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BLRegistroIngreso {

    private final FactoryBL<RegistroIngresoDTO> factory = 
            new FactoryBL<>(RegistroIngresoDAO.class);

    /**
     * Registra un ingreso tras la validación web.
     * Aquí se garantiza la integridad de los datos (Estado y Fecha).
     */
    public void registrarIngreso(Integer idUsuario, 
                                 Integer idEstudiante, 
                                 String resultado) throws AppException {
        try {
            // 1. Creamos el objeto con la información del evento
            RegistroIngresoDTO dto = new RegistroIngresoDTO(idUsuario, idEstudiante, resultado);
            
            // 2. Sellamos el registro con los metadatos necesarios
            // Seteamos 'A' para que sea visible en las consultas activas (como el AdminPanel)
            dto.setEstado("A"); 
            
            // Generamos la marca de tiempo exacta del ingreso
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            dto.setFechaCreacion(timestamp);
            dto.setFechaModifica(timestamp);

            // 3. Persistimos en la base de datos a través del factory
            factory.add(dto);
            
        } catch (Exception e) {
            throw new AppException("Error al procesar el registro de ingreso", e, BLRegistroIngreso.class, "registrarIngreso");
        }
    }

    /**
     * Recupera el historial completo para el Administrador (Desktop).
     */
    public List<RegistroIngresoDTO> obtenerTodos() throws AppException {
        try {
            // Este método usa el 'WHERE Estado = A' heredado en el DataHelper
            return factory.getAll();
        } catch (Exception e) {
            throw new AppException("Error al recuperar el historial de ingresos", e, BLRegistroIngreso.class, "obtenerTodos()");
        }
    }
}