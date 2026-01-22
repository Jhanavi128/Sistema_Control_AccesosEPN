package service;

import DataAccess.DAOs.QRAccesoDAO;
import DataAccess.DAOs.RegistroIngresoDAO;
import DataAccess.DTOs.QRAccesoDTO;

public class ControlAccesoService {
    private QRAccesoDAO qrDao;
    private RegistroIngresoDAO registroDao;

    public ControlAccesoService() {
        try {
            this.qrDao = new QRAccesoDAO();
            this.registroDao = new RegistroIngresoDAO();
        } catch (Exception e) {
            System.out.println("Error al inicializar DAOs: " + e.getMessage());
        }
    }

    public String procesarAcceso(String codigoQR) {
        try {
            QRAccesoDTO qr = qrDao.readByCodigo(codigoQR); 

            if (qr == null) {
                return "ERROR: Código QR no reconocido en la base de datos.";
            }

            // Comparamos contra 1 porque es INTEGER en tu tabla
            if (qr.getEstado() == null || qr.getEstado() != 1) { 
                return "ACCESO DENEGADO: El código está desactivado.";
            }

            return "ACCESO CONCEDIDO: ¡Bienvenido!";
            
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}