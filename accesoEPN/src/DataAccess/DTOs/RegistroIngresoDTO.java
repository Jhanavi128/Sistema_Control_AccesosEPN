package DataAccess.DTOs;

public class RegistroIngresoDTO {

    private Integer IdRegistroIngreso;
    private Integer IdUsuario;     // El Guardia que escanea
    private Integer IdEstudiante;  // El Estudiante que ingresa
    private String  Resultado;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public RegistroIngresoDTO() {}

    // 👉 Constructor para INSERT (Sin IDs autoincrementales ni fechas automáticas)
    public RegistroIngresoDTO(Integer idUsuario, 
                              Integer idEstudiante, 
                              String resultado) {
        IdUsuario    = idUsuario;
        IdEstudiante = idEstudiante;
        Resultado    = resultado;
    }

    // 👉 Constructor completo (Para lectura - READ)
    public RegistroIngresoDTO(Integer idRegistroIngreso, 
                              Integer idUsuario, 
                              Integer idEstudiante, 
                              String resultado, 
                              String estado, 
                              String fechaCreacion, 
                              String fechaModifica) {
        IdRegistroIngreso = idRegistroIngreso;
        IdUsuario         = idUsuario;
        IdEstudiante      = idEstudiante;
        Resultado         = resultado;
        Estado            = estado;
        FechaCreacion     = fechaCreacion;
        FechaModifica     = fechaModifica;
    }

    // Getters y Setters
    public Integer getIdRegistroIngreso() {
        return IdRegistroIngreso;
    }

    public void setIdRegistroIngreso(Integer idRegistroIngreso) {
        IdRegistroIngreso = idRegistroIngreso;
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

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
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
            + "\n IdRegistroIngreso : " + IdRegistroIngreso
            + "\n IdUsuario         : " + IdUsuario
            + "\n IdEstudiante      : " + IdEstudiante
            + "\n Resultado         : " + Resultado
            + "\n Estado            : " + Estado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModifica     : " + FechaModifica;
    }
} 