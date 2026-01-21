public class QRAccesoDTO {
    private Integer IdQRAcceso;
    private Integer IdUsuario;
    private String  Codigoqr;
    private String  FechaGeneracion;
    private String  FechaExpiracion;
    private String  Usado;
    private String  Estado;
    private String  FechaCreacion;
    private String  FechaModifica;

    public QRAccesoDTO(){}

    public QRAccesoDTO(String codigoqr, String fechaGeneracion, String fechaExpiracion, String usado) {
        IdQRAcceso = 0;
        IdUsuario = 0;
        Codigoqr = codigoqr;
        FechaGeneracion = fechaGeneracion;
        FechaExpiracion = fechaExpiracion;
        Usado = usado;
    }

    public QRAccesoDTO(Integer idQRAcceso, Integer idUsuario, String codigoqr, String fechaGeneracion,
            String fechaExpiracion, String usado, String estado, String fechaCreacion, String fechaModifica) {
        IdQRAcceso = idQRAcceso;
        IdUsuario = idUsuario;
        Codigoqr = codigoqr;
        FechaGeneracion = fechaGeneracion;
        FechaExpiracion = fechaExpiracion;
        Usado = usado;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdQRAcceso() {
        return IdQRAcceso;
    }

    public void setIdQRAcceso(Integer idQRAcceso) {
        IdQRAcceso = idQRAcceso;
    }

    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getCodigoqr() {
        return Codigoqr;
    }

    public void setCodigoqr(String codigoqr) {
        Codigoqr = codigoqr;
    }

    public String getFechaGeneracion() {
        return FechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        FechaGeneracion = fechaGeneracion;
    }

    public String getFechaExpiracion() {
        return FechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        FechaExpiracion = fechaExpiracion;
    }

    public String getUsado() {
        return Usado;
    }

    public void setUsado(String usado) {
        Usado = usado;
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
        + "\n IdQRAcceso                     : "+ getIdQRAcceso          ()
        + "\n IdUsuario                      : "+ getIdUsuario           ()
        + "\n Codigoqr                       : "+ getCodigoqr            ()
        + "\n FechaGeneracion                : "+ getFechaGeneracion     ()  
        + "\n FechaExpiracion                : "+ getFechaExpiracion     ()
        + "\n Usado                          : "+ getUsado               ()
        + "\n Estado                         : "+ getEstado             ()
        + "\n FechaCreacion                  : "+ getFechaCreacion      ()
        + "\n FechaModifica                  : "+ getFechaModifica      ();
    }

}
