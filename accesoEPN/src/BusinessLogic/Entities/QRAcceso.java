package BusinessLogic.Entities;

public class QRAcceso {
    private int idQRAcceso;
    private int idUsuario;
    private String codigoQR;
    private int estado;

    public QRAcceso(int idQRAcceso, int idUsuario, String codigoQR, int estado) {
        this.idQRAcceso = idQRAcceso;
        this.idUsuario = idUsuario;
        this.codigoQR = codigoQR;
        this.estado = estado;
    }

    public int getIdQRAcceso() { return idQRAcceso; }
    public int getIdUsuario() { return idUsuario; }
    public String getCodigoQR() { return codigoQR; }
    public int getEstado() { return estado; }
}