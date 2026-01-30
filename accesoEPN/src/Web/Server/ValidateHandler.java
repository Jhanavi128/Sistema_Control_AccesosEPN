package Web.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import BusinessLogic.Entities.BLValidarQR;
import DataAccess.DTOs.QRAccesoDTO;
import Infrastructure.AppException;

public class ValidateHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Solo permitir GET
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        // Obtener parámetros de la URL
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();

        String codigoQR = null;

        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2 && pair[0].equals("codigo")) {
                    codigoQR = pair[1];
                    break;
                }
            }
        }

        String response;

        if (codigoQR == null || codigoQR.isEmpty()) {
            response = "CODIGO QR NO ENVIADO";
        } else {

            BLValidarQR bl = new BLValidarQR();
            QRAccesoDTO qr;

            try {
                qr = bl.validar(codigoQR);
            } catch (AppException e) {
                exchange.sendResponseHeaders(500, -1);
                return;
            }

            if (qr != null) {
                response = "QR VALIDO";
            } else {
                response = "QR INVALIDO";
            }
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
