package BusinessLogic.Entities;

public class Estudiante {
    private int idEstudiante;
    private int idPeriodo;
    private String nombre;
    private String apellido;
    private String cedula;
    private String codigoUnico;
    private String sexo;
    private String estado;

    public Estudiante(int idEstudiante, int idPeriodo, String nombre, String apellido, String cedula, String codigoUnico, String sexo, String estado) {
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.codigoUnico = codigoUnico;
        this.sexo = sexo;
        this.estado = estado;
    }

    public int getIdEstudiante() { return idEstudiante; }
    public int getIdPeriodo() { return idPeriodo; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedula() { return cedula; }
    public String getCodigoUnico() { return codigoUnico; }
    public String getSexo() { return sexo; }
    public String getEstado() { return estado; }
}