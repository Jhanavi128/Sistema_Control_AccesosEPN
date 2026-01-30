package Web.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import BusinessLogic.Entities.BLEstudiante;
import BusinessLogic.Entities.BLRegistroIngreso;
import DataAccess.DTOs.EstudianteDTO;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GuardiaHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. Cabeceras CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            // 2. Obtener el ID del Guardia (viene de la URL: ?idGuardia=2)
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            int idGuardia = params.containsKey("idGuardia")
                    ? Integer.parseInt(params.get("idGuardia"))
                    : 2; // ID por defecto si no se envía

            // 3. Leer el código QR del cuerpo
            String codigoQR = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            codigoQR = codigoQR.replace("\"", "").trim();
            System.out.println("📌 Escaneando: " + codigoQR + " | Guardia ID: " + idGuardia);

            // 4. Lógica de validación y registro
            BLEstudiante blEst = new BLEstudiante();
            BLRegistroIngreso blReg = new BLRegistroIngreso();
            EstudianteDTO est = blEst.getByCodigoUnico(codigoQR);

            String jsonResponse;
            if (est != null && "A".equals(est.getEstado())) {
                // ✅ ACCESO VÁLIDO: Guardar en historial
                blReg.registrarIngreso(idGuardia, est.getIdEstudiante(), "Autorizado");

                jsonResponse = String.format(
                        "{\"status\":\"success\", \"mensaje\":\"ACCESO AUTORIZADO\", \"nombre\":\"%s %s\", \"carrera\":\"%s\", \"foto\":\"%s\"}",
                        est.getNombre(), est.getApellido(), (est.getCarrera() != null ? est.getCarrera() : "N/A"),
                        (est.getFotoPath() != null && !est.getFotoPath().isEmpty() ? est.getFotoPath()
                                : "default.png"));
            } else {
                // ❌ ACCESO INVÁLIDO: Si el estudiante existe, registrar el rechazo
                String resultado = (est == null) ? "Invalido" : "Rechazado";
                if (est != null) {
                    blReg.registrarIngreso(idGuardia, est.getIdEstudiante(), resultado);
                }

                jsonResponse = String.format(
                        "{\"status\":\"error\", \"mensaje\":\"ACCESO DENEGADO: %s\"}",
                        (est == null ? "Código no reconocido" : "Estudiante inactivo"));
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
            byte[] errorBytes = error.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, errorBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorBytes);
            }
        }
    }

    // Utilidad para extraer parámetros de la URL
    private Map<String, String> queryToMap(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1)
                    map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}