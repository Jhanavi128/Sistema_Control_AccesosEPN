package service;

import dao.QRAccesoDAO;
import dao.RegistroIngresoDAO;
import model.QRAcceso;

public class ControlAccesoService {
    private QRAccesoDAO qrDao = new QRAccesoDAO();
    private RegistroIngresoDAO registroDao = new RegistroIngresoDAO();

    public String procesarAcceso(String codigoQR) {
        QRAcceso qr = qrDao.buscarPorCodigo(codigoQR);

        if (qr == null) {
            // No podemos registrar en la BD porque no hay IdQRAcceso, 
            // pero avisamos al usuario.
            return "ERROR: Código QR no reconocido.";
        }

        if (qr.getEstado() != 1) {
            // Usamos 'Rechazado' para cumplir con el CHECK del SQL
            registroDao.registrar(qr.getIdQRAcceso(), "Rechazado");
            return "ACCESO DENEGADO: El código está desactivado.";
        }

        // Usamos 'Autorizado' para cumplir con el CHECK del SQL
        boolean registrado = registroDao.registrar(qr.getIdQRAcceso(), "Autorizado");
        
        if (registrado) {
            return "ACCESO CONCEDIDO: ¡Bienvenido!";
        } else {
            return "ERROR: No se pudo guardar el registro por restricción de datos.";
        }
    }
}