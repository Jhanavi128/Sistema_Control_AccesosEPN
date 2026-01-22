package BusinessLogic.Entities;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String rol;
    private int estado;

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String contrasena, String rol, int estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public int getEstado() { return estado; }
}