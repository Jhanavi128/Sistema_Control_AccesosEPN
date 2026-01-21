public class EstudianteDTO {
    private Integer IdEstudiante;
    private Integer IdPeriodo;
    private String  Nombre;
    private String  Apellido;
    private String  Cedula;
    private String  CodigoUnico;
    private String  FechaNacimiento;
    private String  Sexo;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public EstudianteDTO(){}

    public EstudianteDTO(String nombre, String apellido, String cedula, String codigoUnico, String fechaNacimiento,
            String sexo) {
        IdEstudiante = 0;
        IdPeriodo = 0;
        Nombre = nombre;
        Apellido = apellido;
        Cedula = cedula;
        CodigoUnico = codigoUnico;
        FechaNacimiento = fechaNacimiento;
        Sexo = sexo;
    }

    public EstudianteDTO(Integer idEstudiante, Integer idPeriodo, String nombre, String apellido, String cedula,
            String codigoUnico, String fechaNacimiento, String sexo, String estado, String fechaCreacion,
            String fechaModifica) {
        IdEstudiante = idEstudiante;
        IdPeriodo = idPeriodo;
        Nombre = nombre;
        Apellido = apellido;
        Cedula = cedula;
        CodigoUnico = codigoUnico;
        FechaNacimiento = fechaNacimiento;
        Sexo = sexo;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

     public Integer getIdEstudiante() {
        return IdEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        IdEstudiante = idEstudiante;
    }

    public Integer getIdPeriodo() {
        return IdPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        IdPeriodo = idPeriodo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getCodigoUnico() {
        return CodigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        CodigoUnico = codigoUnico;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
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
        + "\n IdEstudiante                   : "+ getIdEstudiante        ()
        + "\n IdPeriodo                      : "+ getIdPeriodo           ()
        + "\n Nombre                         : "+ getNombre              ()
        + "\n Apellido                       : "+ getApellido            ()  
        + "\n Cedula                         : "+ getCedula              ()
        + "\n CodigoUnico                    : "+ getCodigoUnico         ()
        + "\n FechaNacimiento                : "+ getFechaNacimiento     ()
        + "\n Sexo                           : "+ getSexo                ()
        + "\n Estado                         : "+ getEstado              ()
        + "\n FechaCreacion                  : "+ getFechaCreacion       ()
        + "\n FechaModifica                  : "+ getFechaModifica       ();
    }


    
}
