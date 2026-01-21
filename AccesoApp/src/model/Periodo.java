package model;

public class Periodo {
    private int idPeriodo;
    private String carrera;
    private int semestre;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    public Periodo(int idPeriodo, String carrera, int semestre, String nombre, String fechaInicio, String fechaFin, String estado) {
        this.idPeriodo = idPeriodo;
        this.carrera = carrera;
        this.semestre = semestre;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdPeriodo() { return idPeriodo; }
    public String getCarrera() { return carrera; }
    public int getSemestre() { return semestre; }
    public String getNombre() { return nombre; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
}