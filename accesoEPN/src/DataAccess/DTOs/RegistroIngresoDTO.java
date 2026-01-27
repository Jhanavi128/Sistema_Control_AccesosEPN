package DataAccess.DTOs;

public class RegistroIngresoDTO {

    private Integer IdRegistroIngreso;
    private Integer IdUsuario;
    private Integer IdQRAcceso;

    private String  Resultado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public RegistroIngresoDTO() {}

    // 👉 constructor para INSERT
    public RegistroIngresoDTO(Integer idUsuario,
                              Integer idQRAcceso,
                              String resultado) {
        IdUsuario  = idUsuario;
        IdQRAcceso = idQRAcceso;
        Resultado  = resultado;
    }

    // 👉 constructor completo (READ)
    public RegistroIngresoDTO(Integer idRegistroIngreso,
                              Integer idUsuario,
                              Integer idQRAcceso,
                              String resultado,
                              String fechaCreacion,
                              String fechaModifica) {
        IdRegistroIngreso = idRegistroIngreso;
        IdUsuario = idUsuario;
        IdQRAcceso = idQRAcceso;
        Resultado = resultado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

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

    public Integer getIdQRAcceso() {
        return IdQRAcceso;
    }

    public void setIdQRAcceso(Integer idQRAcceso) {
        IdQRAcceso = idQRAcceso;
    
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
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
            + "\n IdQRAcceso        : " + IdQRAcceso
            + "\n Resultado         : " + Resultado
            + "\n FechaCreacion     : " + FechaCreacion
            + "\n FechaModifica     : " + FechaModifica;
    }

}
