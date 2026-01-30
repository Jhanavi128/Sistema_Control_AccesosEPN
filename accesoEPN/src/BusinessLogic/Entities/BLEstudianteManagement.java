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
    private UsuarioDAO userDAO;
    private EstudianteDAO estDAO;

    public BLEstudianteManagement() {
        try {
            this.estFactory = new FactoryBL<>(EstudianteDAO.class);
            this.userFactory = new FactoryBL<>(UsuarioDAO.class);
            this.estDAO = new EstudianteDAO();
            this.userDAO = new UsuarioDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearEstudiante(EstudianteDTO estudiante, String pin) throws AppException {
        // 1. VALIDACIÓN PRINCIPAL: Verificar que no exista estudiante activo con ese
        // código
        if (estDAO.existsByCodigoUnicoActive(estudiante.getCodigoUnico(), null)) {
            throw new AppException("El Código Único ya está registrado por otro estudiante.", null, getClass(),
                    "crearEstudiante");
        }

        // 2. VALIDACIÓN: Verificar que no exista estudiante activo con esa cédula
        if (estDAO.existsByCedulaActive(estudiante.getCedula(), null)) {
            throw new AppException("La Cédula ya está registrada por otro estudiante.", null, getClass(),
                    "crearEstudiante");
        }

        // 3. Verificar si ya existe Usuario (Activo o Inactivo)
        UsuarioDTO existingUser = userDAO.getByCodigoUnicoAnyStatus(estudiante.getCodigoUnico());
        Integer userId;

        if (existingUser != null) {
            // Usuario existe, reutilizar o reactivar
            if ("A".equals(existingUser.getEstado())) {
                // Usuario activo, solo actualizar contraseña
                userId = existingUser.getIdUsuario();
                existingUser.setContrasena(pin);
                existingUser.setFechaModifica(getNow());
                userFactory.upd(existingUser);
            } else {
                // Reactivar Usuario inactivo
                existingUser.setEstado("A");
                existingUser.setContrasena(pin);
                existingUser.setFechaModifica(getNow());
                if (!userFactory.upd(existingUser)) {
                    throw new AppException("Error al reactivar el usuario existente.", null, getClass(),
                            "crearEstudiante");
                }
                userId = existingUser.getIdUsuario();
            }
        } else {
            // 4. Crear Usuario Nuevo
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

            userId = userFactory.getAll().stream()
                    .filter(u -> u.getCodigoUnico().equals(estudiante.getCodigoUnico()) && "A".equals(u.getEstado()))
                    .findFirst()
                    .map(UsuarioDTO::getIdUsuario)
                    .orElseThrow(
                            () -> new AppException("No se pudo recuperar el ID del usuario creado.", null, getClass(),
                                    "crearEstudiante"));
        }

        // 5. Verificar/Crear Estudiante
        EstudianteDTO existingEst = estDAO.getByCodigoUnicoAnyStatus(estudiante.getCodigoUnico());

        estudiante.setIdUsuario(userId);
        estudiante.setEstado("A");
        estudiante.setFechaModifica(getNow());

        if (existingEst != null) {
            // Actualizar/Reactivar Estudiante existente
            estudiante.setIdEstudiante(existingEst.getIdEstudiante()); // Mantener ID original
            estudiante.setFechaCreacion(existingEst.getFechaCreacion());

            if (!estFactory.upd(estudiante)) {
                throw new AppException("Error al actualizar/reactivar datos del estudiante.", null, getClass(),
                        "crearEstudiante");
            }
        } else {
            // Crear Nuevo Estudiante
            estudiante.setFechaCreacion(getNow());
            if (!estFactory.add(estudiante)) {
                throw new AppException("Error al guardar datos del estudiante.", null, getClass(), "crearEstudiante");
            }
        }
    }

    public void actualizarEstudiante(EstudianteDTO estudiante, String pin) throws AppException {
        // Validar que el estudiante existe
        if (estudiante.getIdEstudiante() == null) {
            throw new AppException("Debe seleccionar un estudiante para editar.", null, getClass(),
                    "actualizarEstudiante");
        }

        // Verificar que el código único no esté en uso por otro estudiante
        if (estDAO.existsByCodigoUnicoActive(estudiante.getCodigoUnico(), estudiante.getIdEstudiante())) {
            throw new AppException("El Código Único ya está registrado por otro estudiante.", null, getClass(),
                    "actualizarEstudiante");
        }

        // Verificar que la cédula no esté en uso por otro estudiante
        if (estDAO.existsByCedulaActive(estudiante.getCedula(), estudiante.getIdEstudiante())) {
            throw new AppException("La Cédula ya está registrada por otro estudiante.", null, getClass(),
                    "actualizarEstudiante");
        }

        // Actualizar datos del estudiante
        estudiante.setFechaModifica(getNow());
        if (!estFactory.upd(estudiante)) {
            throw new AppException("Error al actualizar estudiante.", null, getClass(), "actualizarEstudiante");
        }

        // Actualizar PIN si se proporcionó
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
            return estDAO.searchByText(""); // Use search which includes JOINs
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
