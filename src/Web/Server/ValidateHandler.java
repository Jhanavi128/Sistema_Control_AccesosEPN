package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Data.ConexionBD;

public class ValidateHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Handle CORS Preflight
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers",
                        "Content-Type, ngrok-skip-browser-warning");
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            URI uri = exchange.getRequestURI();
            String query = uri.getQuery(); // codigo=XXX

            if (query == null || !query.startsWith("codigo=")) {
                sendJson(exchange, 400, "{\"valid\": false, \"mensaje\": \"Codigo no recibido\"}");
                return;
            }

            String[] parts = query.split("=", 2);
            String codigo = "";
            if (parts.length > 1) {
                codigo = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            }

            // DEBUG LOGGING
            System.out.println("Processing QR Raw: [" + codigo + "]");

            // Clean input
            if (codigo != null) {
                codigo = codigo.replaceAll("[^0-9]", "");
            } else {
                codigo = "";
            }

            System.out.println("Processing QR Cleaned: [" + codigo + "]");

            String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String jsonResp;

            // Database Logic
            String nombreResult = null;
            String carreraResult = "Software";
            String photoStub = "/assets/perfil_john.jpg";
            boolean isValid = false;

            String sql = "SELECT * FROM Usuarios WHERE user = ?";
            try (java.sql.Connection conn = Data.ConexionBD.conectar();
                    java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, codigo);
                try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String rol = rs.getString("rol");
                        if ("ESTUDIANTE".equals(rol)) {
                            isValid = true;
                            nombreResult = rs.getString("nombre");
                            carreraResult = rs.getString("carrera");
                            String p = rs.getString("foto_url");
                            if (p != null && !p.isEmpty())
                                photoStub = "/assets/" + p;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isValid) {
                jsonResp = String.format(
                        "{" +
                                "  \"valid\": true," +
                                "  \"nombre\": \"%s\"," +
                                "  \"carrera\": \"%s\"," +
                                "  \"foto\": \"%s\"," +
                                "  \"hora\": \"%s\"," +
                                "  \"mensaje\": \"ACCESO PERMITIDO\"" +
                                "}",
                        nombreResult, carreraResult, photoStub, horaActual);
            }
            // Guard Logic could be refined but basic idea is here
            else if ("202421165".equals(codigo)) {
                jsonResp = String.format(
                        "{" +
                                "  \"valid\": false," +
                                "  \"hora\": \"%s\"," +
                                "  \"mensaje\": \"Código de Guardia (No Acceso)\"" +
                                "}",
                        horaActual);
            } else {
                // Failure
                jsonResp = String.format(
                        "{" +
                                "  \"valid\": false," +
                                "  \"hora\": \"%s\"," +
                                "  \"mensaje\": \"DENEGADO (Leído: %s)\"" +
                                "}",
                        horaActual, codigo);
            }

            sendJson(exchange, 200, jsonResp);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Return simple error JSON on crash
                String error = "{\"valid\": false, \"mensaje\": \"Error Interno del Servidor\"}";
                exchange.sendResponseHeaders(500, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.getResponseBody().close();
            } catch (Exception ex) {
            }
        }
    }

    private void sendJson(HttpExchange exchange, int statusCode, String json) throws java.io.IOException {
        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Allow CORS for ngrok
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
