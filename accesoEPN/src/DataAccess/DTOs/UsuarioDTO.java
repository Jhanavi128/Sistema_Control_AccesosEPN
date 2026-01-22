package DataAccess.DTOs;

public class UsuarioDTO {
    private Integer IdUsuario;
    private Integer IdEstudiante;
    private String  Usuario;
    private String  Clave;
    private String  Huella;
    private String  Rol;
    private String  CorreoInstitucional;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public UsuarioDTO(){}

    public UsuarioDTO(String usuario, String clave, String huella, String rol, String correoInstitucional) {
        IdUsuario = 0;
        IdEstudiante = 0;
        Usuario = usuario;
        Clave = clave;
        Huella = huella;
        Rol = rol;
        CorreoInstitucional = correoInstitucional;
    }

    public UsuarioDTO(Integer idUsuario, Integer idEstudiante, String usuario, String clave, String huella, String rol,
            String correoInstitucional, String estado, String fechaCreacion, String fechaModifica) {
        IdUsuario = idUsuario;
        IdEstudiante = idEstudiante;
        Usuario = usuario;
        Clave = clave;
        Huella = huella;
        Rol = rol;
        CorreoInstitucional = correoInstitucional;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        IdUsuario = idUsuario;
    }

    public Integer getIdEstudiante() {
        return IdEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        IdEstudiante = idEstudiante;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getHuella() {
        return Huella;
    }

    public void setHuella(String huella) {
        Huella = huella;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getCorreoInstitucional() {
        return CorreoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        CorreoInstitucional = correoInstitucional;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
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

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdUsuario                     : "+ getIdUsuario          ()
        + "\n IdEstudiante                  : "+ getIdEstudiante       ()
        + "\n Usuario                       : "+ getUsuario            ()
        + "\n Clave                         : "+ getClave              ()  
        + "\n Huella                        : "+ getHuella             ()
        + "\n Rol                           : "+ getRol                ()
        + "\n CorreoInstitucional           : "+ getCorreoInstitucional()             
        + "\n Estado                        : "+ getEstado             ()
        + "\n FechaCreacion                 : "+ getFechaCreacion      ()
        + "\n FechaModifica                 : "+ getFechaModifica      ();
    }

}
