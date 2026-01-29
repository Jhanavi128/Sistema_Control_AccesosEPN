package BusinessLogic.Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.QRAccesoDAO;
import DataAccess.DTOs.QRAccesoDTO;
import Infrastructure.AppException;

public class BLValidarQR {

    private final FactoryBL<QRAccesoDTO> factory =
            new FactoryBL<>(QRAccesoDAO.class);

    public QRAccesoDTO validar(String codigoQR) throws AppException {

        QRAccesoDTO qr = factory.getAll()
                .stream()
                .filter(q -> q.getCodigoQR().equals(codigoQR))
                .findFirst()
                .orElse(null);

        if (qr == null) return null;

        // Estado
        if (qr.getEstado() == 0) return null;

        // Expiración
        if (qr.getFechaExpiracion() != null && !qr.getFechaExpiracion().isEmpty()) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime fechaExp =
                    LocalDateTime.parse(qr.getFechaExpiracion(), formatter);

            if (fechaExp.isBefore(LocalDateTime.now())) {
                return null;
            }
        }

        return qr;
    }
}
