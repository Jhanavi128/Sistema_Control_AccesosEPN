package model;

public class RegistroIngreso {
    private int idRegistroIngreso;
    private int idQRAcceso;
    private String fechaHora;
    private String resultado;

    public RegistroIngreso(int idRegistroIngreso, int idQRAcceso, String fechaHora, String resultado) {
        this.idRegistroIngreso = idRegistroIngreso;
        this.idQRAcceso = idQRAcceso;
        this.fechaHora = fechaHora;
        this.resultado = resultado;
    }

    public int getIdRegistroIngreso() { return idRegistroIngreso; }
    public int getIdQRAcceso() { return idQRAcceso; }
    public String getFechaHora() { return fechaHora; }
    public String getResultado() { return resultado; }
}