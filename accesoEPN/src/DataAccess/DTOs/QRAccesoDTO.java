package DataAccess.DTOs;

public class QRAccesoDTO {
    private Integer IdQRAcceso;
    private Integer IdUsuario;
    private String  Codigoqr;
    private String  FechaGeneracion;
    private String  FechaExpiracion;       
    private Integer Estado;       

    public QRAccesoDTO() {}

    // Constructor completo
    public QRAccesoDTO(Integer idQRAcceso, Integer idUsuario, String codigoqr, String fechaGeneracion,
                       String fechaExpiracion, String usado, Integer estado, String fechaCreacion, String fechaModifica) {
        this.IdQRAcceso = idQRAcceso;
        this.IdUsuario = idUsuario;
        this.Codigoqr = codigoqr;
        this.FechaGeneracion = fechaGeneracion;
        this.FechaExpiracion = fechaExpiracion;
        this.Estado = estado;
    }

    // GETTERS Y SETTERS
    public Integer getIdQRAcceso() { return IdQRAcceso; }
    public void setIdQRAcceso(Integer idQRAcceso) { this.IdQRAcceso = idQRAcceso; }

    public Integer getIdUsuario() { return IdUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.IdUsuario = idUsuario; }

    public String getCodigoqr() { return Codigoqr; }
    public void setCodigoqr(String codigoqr) { this.Codigoqr = codigoqr; }

    public String getFechaGeneracion() { return FechaGeneracion; }
    public void setFechaGeneracion(String fechaGeneracion) { this.FechaGeneracion = fechaGeneracion; }

    public String getFechaExpiracion() { return FechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { this.FechaExpiracion = fechaExpiracion; }

    public Integer getEstado() { return Estado; }
    public void setEstado(Integer estado) { this.Estado = estado; }

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdQRAcceso                     : "+ getIdQRAcceso          ()
        + "\n IdUsuario                      : "+ getIdUsuario           ()
        + "\n Codigoqr                       : "+ getCodigoqr            ()
        + "\n FechaGeneracion                : "+ getFechaGeneracion     ()  
        + "\n FechaExpiracion                : "+ getFechaExpiracion     ()
        + "\n Estado                         : "+ getEstado             ();
    }

}
