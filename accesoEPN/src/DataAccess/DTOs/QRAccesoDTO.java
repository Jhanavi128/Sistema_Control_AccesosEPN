package DataAccess.DTOs;

public class QRAccesoDTO {

    private Integer IdQRAcceso;
    private Integer IdUsuario;
    private String  CodigoQR;
    private Integer Estado;
    private String  FechaGeneracion;
    private String  FechaExpiracion;

    public QRAccesoDTO() {}

    public QRAccesoDTO(Integer idQRAcceso, String codigoQR) {
        IdQRAcceso = idQRAcceso;
        CodigoQR = codigoQR;
    }

    public QRAccesoDTO(Integer idQRAcceso, Integer idUsuario, String codigoQR,
                       Integer estado, String fechaGeneracion, String fechaExpiracion) {
        IdQRAcceso = idQRAcceso;
        IdUsuario = idUsuario;
        CodigoQR = codigoQR;
        Estado = estado;
        FechaGeneracion = fechaGeneracion;
        FechaExpiracion = fechaExpiracion;
    }

    
    public Integer getIdQRAcceso() { return IdQRAcceso; }
    public void setIdQRAcceso(Integer idQRAcceso) { IdQRAcceso = idQRAcceso; }

    public Integer getIdUsuario() { return IdUsuario; }
    public void setIdUsuario(Integer idUsuario) { IdUsuario = idUsuario; }

    public String getCodigoQR() { return CodigoQR; }
    public void setCodigoQR(String codigoQR) { CodigoQR = codigoQR; }

    public Integer getEstado() { return Estado; }
    public void setEstado(Integer estado) { Estado = estado; }

    public String getFechaGeneracion() { return FechaGeneracion; }
    public void setFechaGeneracion(String fechaGeneracion) { FechaGeneracion = fechaGeneracion; }

    public String getFechaExpiracion() { return FechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { FechaExpiracion = fechaExpiracion; }

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdQRAcceso     : " + getIdQRAcceso()
        + "\n CodigoQR       : " + getCodigoQR()
        + "\n Estado         : " + getEstado();
    }

}
