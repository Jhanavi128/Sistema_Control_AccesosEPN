package DataAccess.DTOs;

public class PeriodoDTO {
    private Integer IdPeriodo;
    private String  Carrera;
    private Integer  Semestre;
    private String  Nombre;
    private String  FechaInicio;
    private String  FechaFin;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public PeriodoDTO(Integer idPeriodo, String carrera, Integer semestre, String nombre) {
        IdPeriodo = idPeriodo;
        Carrera = carrera;
        Semestre = semestre;
        Nombre = nombre;

    }


    public PeriodoDTO(Integer idPeriodo, String carrera, Integer semestre, String nombre, String fechaInicio,
            String fechaFin, String estado, String fechaCreacion, String fechaModifica) {
        IdPeriodo = idPeriodo;
        Carrera = carrera;
        Semestre = semestre;
        Nombre = nombre;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }


    public PeriodoDTO(){}

    
    public Integer getIdPeriodo() {
        return IdPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        IdPeriodo = idPeriodo;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public Integer getSemestre() {
        return Semestre;
    }

    public void setSemestre(Integer semestre) {
        Semestre = semestre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
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
        + "\n IdPeriodo                      : "+ getIdPeriodo           ()
        + "\n Carrera                        : "+ getCarrera             ()
        + "\n Semestre                       : "+ getSemestre            ()
        + "\n Nombre                         : "+ getNombre              ()  
        + "\n FechaInicio                    : "+ getFechaInicio         ()
        + "\n FechaFin                       : "+ getFechaFin            ()
        + "\n Estado                         : "+ getEstado              ()
        + "\n FechaCreacion                  : "+ getFechaCreacion       ()
        + "\n FechaModifica                  : "+ getFechaModifica       ();
    }
    

    
}
