package Web.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import BusinessLogic.Entities.BLEstudiante;
import DataAccess.DTOs.EstudianteDTO;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GuardiaHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. Cabeceras CORS (OBLIGATORIO para que el escáner no de error de red)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        // Responder a peticiones pre-flight (OPTIONS)
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        // 2. Solo aceptamos POST
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            // 3. Leer el cuerpo (body) que envía el escáner
            String codigoQR = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("📌 Guardia escaneó código: " + codigoQR);

            // Limpiar el código por si acaso llega con comillas o espacios
            codigoQR = codigoQR.replace("\"", "").trim();

            // 4. Validar con la nueva base de datos (Usando el campo CodigoUnico)
            BLEstudiante bl = new BLEstudiante();
            EstudianteDTO est = bl.getByCodigoUnico(codigoQR);

            String jsonResponse;
            if (est != null && "A".equals(est.getEstado())) {
                jsonResponse = String.format(
                    "{\"status\":\"success\", \"mensaje\":\"ACCESO AUTORIZADO\", \"nombre\":\"%s %s\"}",
                    est.getNombre(), est.getApellido()
                );
            } else {
                jsonResponse = "{\"status\":\"error\", \"mensaje\":\"ACCESO DENEGADO: Estudiante no encontrado o inactivo\"}";
            }

            // 5. Enviar respuesta
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String error = "{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}";
            exchange.sendResponseHeaders(500, error.length());
            exchange.getResponseBody().write(error.getBytes());
        }
    }
}