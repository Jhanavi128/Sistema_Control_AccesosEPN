package DataAccess.DTOs;

public class RegistroIngresoDTO {
    private Integer IdIngreso;
    private Integer IdQRAcceso;
    private String  FechaHora;
    private String  Resultado;

    public RegistroIngresoDTO(){}

    public RegistroIngresoDTO(String fechaHora, String resultado) {
        IdIngreso = 0;
        IdQRAcceso = 0;
        FechaHora = fechaHora;
        Resultado = resultado;
    }

     public Integer getIdIngreso() {
        return IdIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        IdIngreso = idIngreso;
    }

    public Integer getIdQRAcceso() {
        return IdQRAcceso;
    }

    public void setIdQRAcceso(Integer idQRAcceso) {
        IdQRAcceso = idQRAcceso;
    }

    public String getFechaHora() {
        return FechaHora;
    }

    public void setFechaHora(String fechaHora) {
        FechaHora = fechaHora;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    @Override
    public String toString() {
        return getClass().getName()
        + "\n IdIngreso                      : "+ getIdIngreso           ()
        + "\n IdQRAcceso                     : "+ getIdQRAcceso          ()
        + "\n FechaHora                      : "+ getFechaHora           ()
        + "\n Resultado                      : "+ getResultado           ();
    }


    
}
