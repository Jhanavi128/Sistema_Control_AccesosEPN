package BusinessLogic.Entities;

import java.util.List;
import DataAccess.DTOs.EstudianteDTO;
import DataAccess.DTOs.PeriodoDTO;
import Infrastructure.AppException;

public class BLEstudiante extends Estudiante {

    /**
     * Obtiene un estudiante por su ID de tabla (PK)
     */
    public EstudianteDTO getById(Integer id) throws AppException {
        return factory.getBy(id);
    }

    /**
     * Obtiene un estudiante por su ID de usuario y carga los datos de su
     * carrera/periodo
     */
    public EstudianteDTO getByUsuarioId(Integer idUsuario) throws AppException {
        // 1. Buscamos el estudiante base usando el getAll del factory
        EstudianteDTO est = factory.getAll()
                .stream()
                .filter(e -> e.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);

        // 2. Si existe, buscamos los textos de su periodo
        if (est != null && est.getIdPeriodo() != null) {
            try {
                BLPeriodo blPeriodo = new BLPeriodo();
                PeriodoDTO per = blPeriodo.getById(est.getIdPeriodo());

                if (per != null) {
                    // Log para verificar en consola
                    System.out.println(
                            "LOG: Periodo encontrado -> " + per.getNombre() + " Carrera -> " + per.getCarrera());

                    est.setCarrera(per.getCarrera());
                    est.setNombrePeriodo(per.getNombre());
                } else {
                    System.out.println("LOG: No se encontró Periodo con ID: " + est.getIdPeriodo());
                }
            } catch (Exception e) {
                System.out.println("Error crítico al cargar periodo: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return est;
    }

    /**
     * Busca un estudiante por su Código Único (Escaneado por el Guardia)
     */
    public EstudianteDTO getByCodigoUnico(String codigoUnico) throws AppException {
        // Filtramos en la lista de todos los estudiantes
        EstudianteDTO est = factory.getAll()
                .stream()
                .filter(e -> e.getCodigoUnico().equals(codigoUnico))
                .findFirst()
                .orElse(null);

        // Si existe, cargamos la carrera manualemente (Igual que en getByUsuarioId)
        if (est != null && est.getIdPeriodo() != null) {
            try {
                BLPeriodo blPeriodo = new BLPeriodo();
                PeriodoDTO per = blPeriodo.getById(est.getIdPeriodo());
                if (per != null) {
                    est.setCarrera(per.getCarrera());
                    est.setNombrePeriodo(per.getNombre());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return est;
    }

    /**
     * Obtiene la lista completa de estudiantes activos
     */
    public List<EstudianteDTO> getAll() throws AppException {
        return factory.getAll();
    }
}