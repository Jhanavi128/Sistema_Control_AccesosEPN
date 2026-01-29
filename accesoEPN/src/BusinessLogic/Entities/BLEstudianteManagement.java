package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.EstudianteDAO;
import DataAccess.DAOs.UsuarioDAO;
import DataAccess.DTOs.EstudianteDTO;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BLEstudianteManagement {

    private FactoryBL<EstudianteDTO> estFactory;
    private FactoryBL<UsuarioDTO> userFactory;
    private EstudianteDAO estDAO;

    public BLEstudianteManagement() {
        try {
            this.estFactory = new FactoryBL<>(EstudianteDAO.class);
            this.userFactory = new FactoryBL<>(UsuarioDAO.class);
            this.estDAO = new EstudianteDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearEstudiante(EstudianteDTO estudiante, String pin) throws AppException {
        // 1. Validar duplicados
        if (estDAO.searchByText(estudiante.getCodigoUnico()).stream()
                .anyMatch(e -> e.getCodigoUnico().equals(estudiante.getCodigoUnico()))) {
            throw new AppException("El Código Único ya existe.", null, getClass(), "crearEstudiante");
        }

        // 2. Crear Usuario
        UsuarioDTO user = new UsuarioDTO();
        user.setCodigoUnico(estudiante.getCodigoUnico());
        user.setContrasena(pin);
        user.setRol("Estudiante");
        user.setEstado("A");
        user.setFechaCreacion(getNow());
        user.setFechaModifica(getNow());

        if (!userFactory.add(user)) {
            throw new AppException("Error al crear el usuario.", null, getClass(), "crearEstudiante");
        }

        Integer userId = userFactory.getAll().stream()
                .filter(u -> u.getCodigoUnico().equals(estudiante.getCodigoUnico()) && "A".equals(u.getEstado()))
                .findFirst()
                .map(UsuarioDTO::getIdUsuario)
                .orElseThrow(() -> new AppException("No se pudo recuperar el ID del usuario creado.", null, getClass(),
                        "crearEstudiante"));

        estudiante.setIdUsuario(userId);
        estudiante.setEstado("A");
        estudiante.setFechaCreacion(getNow());
        estudiante.setFechaModifica(getNow());

        if (!estFactory.add(estudiante)) {
            userFactory.del(userId);
            throw new AppException("Error al guardar datos del estudiante.", null, getClass(), "crearEstudiante");
        }
    }

    public void actualizarEstudiante(EstudianteDTO estudiante, String pin) throws AppException {
        estudiante.setFechaModifica(getNow());
        if (!estFactory.upd(estudiante)) {
            throw new AppException("Error al actualizar estudiante.", null, getClass(), "actualizarEstudiante");
        }

        if (pin != null && !pin.isEmpty()) {
            UsuarioDTO user = userFactory.getBy(estudiante.getIdUsuario());
            if (user != null) {
                user.setContrasena(pin);
                user.setFechaModifica(getNow());
                userFactory.upd(user);
            }
        }
    }

    public void eliminarEstudiante(Integer idEstudiante) throws AppException {
        EstudianteDTO est = estFactory.getBy(idEstudiante);
        if (est != null) {
            estFactory.del(idEstudiante);

            if (est.getIdUsuario() != null) {
                userFactory.del(est.getIdUsuario());
            }
        }
    }

    public List<EstudianteDTO> buscarEstudiantes(String texto) throws AppException {
        if (texto == null || texto.trim().isEmpty()) {
            return estFactory.getAll();
        }
        return estDAO.searchByText(texto);
    }

    public List<EstudianteDTO> obtenerTodos() throws AppException {
        return estFactory.getAll();
    }

    private String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
