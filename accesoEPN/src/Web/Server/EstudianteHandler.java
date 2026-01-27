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
        // Configuramos cabeceras para permitir CORS y definir JSON como respuesta
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        try {
            // Extraer parámetros de la URL
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String idStr = params.get("idUsuario");

            if (idStr == null || idStr.isEmpty()) {
                sendResponse(exchange, 400, "{\"error\":\"idUsuario es requerido\"}");
                return;
            }

            // Llamada a la lógica de negocio
            BLEstudiante bl = new BLEstudiante();
            EstudianteDTO est = bl.getByUsuarioId(Integer.parseInt(idStr));

            if (est == null) {
                sendResponse(exchange, 404, "{\"error\":\"Estudiante no encontrado\"}");
                return;
            }

            // Validamos que el CodigoUnico no sea nulo antes de enviar
            String codigo = est.getCodigoUnico();
            if (codigo == null || codigo.isEmpty()) {
                codigo = "SIN_CODIGO_ASIGNADO";
            }

            // Construcción del JSON final
            String json = String.format(
                "{\"nombre\":\"%s\",\"apellido\":\"%s\",\"codigoUnico\":\"%s\",\"carrera\":\"Software\",\"periodo\":\"2025-B\"}",
                est.getNombre(), 
                est.getApellido(), 
                codigo
            );

            sendResponse(exchange, 200, json);

        } catch (Exception e) {
            // Imprimimos el error en la consola del servidor para debuggear
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