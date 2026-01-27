package Web.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import BusinessLogic.Entities.BLEstudiante;
import DataAccess.DTOs.EstudianteDTO;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EstudianteHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        try {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String idStr = params.get("idUsuario");

            if (idStr == null || idStr.isEmpty()) {
                sendResponse(exchange, 400, "{\"error\":\"idUsuario es requerido\"}");
                return;
            }

            BLEstudiante bl = new BLEstudiante();
            EstudianteDTO est = bl.getByUsuarioId(Integer.parseInt(idStr));

            if (est == null) {
                sendResponse(exchange, 404, "{\"error\":\"Estudiante no encontrado\"}");
                return;
            }

            String codigo = (est.getCodigoUnico() != null) ? est.getCodigoUnico() : "SIN_CODIGO";
            String carrera = (est.getCarrera() != null) ? est.getCarrera() : "Carrera no asignada";
            String periodo = (est.getNombrePeriodo() != null) ? est.getNombrePeriodo() : "N/A";

            String json = String.format(
                "{\"nombre\":\"%s\",\"apellido\":\"%s\",\"codigoUnico\":\"%s\",\"carrera\":\"%s\",\"periodo\":\"%s\"}",
                est.getNombre(), 
                est.getApellido(), 
                codigo,
                carrera,
                periodo
            );

            sendResponse(exchange, 200, json);

        } catch (Exception e) {
            e.printStackTrace(); 
            sendResponse(exchange, 500, "{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1) map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}