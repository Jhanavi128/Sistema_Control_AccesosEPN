package BusinessLogic.Entities;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.QRAccesoDAO;
import DataAccess.DTOs.QRAccesoDTO;

public class QRAcceso {
    protected FactoryBL<QRAccesoDTO> factory = new FactoryBL<>(QRAccesoDAO.class);
    public QRAccesoDTO data = new QRAccesoDTO();
    // private int idQRAcceso;
    // private int idUsuario;
    // private String codigoQR;
    // private int estado;

    // public QRAcceso(int idQRAcceso, int idUsuario, String codigoQR, int estado) {
    //     this.idQRAcceso = idQRAcceso;
    //     this.idUsuario = idUsuario;
    //     this.codigoQR = codigoQR;
    //     this.estado = estado;
    // }

    // public int getIdQRAcceso() { return idQRAcceso; }
    // public int getIdUsuario() { return idUsuario; }
    // public String getCodigoQR() { return codigoQR; }
    // public int getEstado() { return estado; }
}