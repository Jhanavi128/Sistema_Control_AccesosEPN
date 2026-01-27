package DataAccess.DTOs;

public class UsuarioDTO {

    private Integer IdUsuario;
    private String  CodigoUnico;
    private String  Contrasena;
    private String  Rol;
    private String  Estado; // MODIFICADO: De Integer a String para aceptar 'A'
    private String  FechaCreacion;
    private String  FechaModifica;

    // ======================
    // CONSTRUCTORES
    // ======================
    public UsuarioDTO() {}

    public UsuarioDTO(Integer idUsuario, String codigoUnico, String contrasena, String rol) {
        IdUsuario   = idUsuario;
        CodigoUnico = codigoUnico;
        Contrasena  = contrasena;
        Rol         = rol;
    }

    public UsuarioDTO(Integer idUsuario, String codigoUnico, String contrasena,
                      String rol, String estado, // MODIFICADO: String
                      String fechaCreacion, String fechaModifica) {
        IdUsuario      = idUsuario;
        CodigoUnico    = codigoUnico;
        Contrasena     = contrasena;
        Rol            = rol;
        Estado         = estado;
        FechaCreacion  = fechaCreacion;
        FechaModifica  = fechaModifica;
    }

    // ======================
    // GETTERS Y SETTERS
    // ======================
    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getCodigoUnico() {
        return CodigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        CodigoUnico = codigoUnico;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getEstado() { // MODIFICADO: String
        return Estado;
    }

    public void setEstado(String estado) { // MODIFICADO: String
        Estado = estado;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaModifica() {
        return FechaModifica;
    }

    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }

    // ======================
    // TO STRING
    // ======================
    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdUsuario     : " + getIdUsuario()
        + "\n CodigoUnico   : " + getCodigoUnico()
        + "\n Rol           : " + getRol()
        + "\n Estado        : " + getEstado()
        + "\n FechaCreacion : " + getFechaCreacion()
        + "\n FechaModifica : " + getFechaModifica();
    }
}