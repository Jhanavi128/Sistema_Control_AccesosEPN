package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Data.ConexionBD;

public class AuthHandler implements HttpHandler {

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

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                return;
            }

            // Read request body
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = parseFormData(body);

            String codigo = params.get("codigo");
            String pin = params.get("pin");

            System.out.println("Login attempt: " + codigo + " | " + pin);

            // Database Login Logic
            String userCode = null;
            String redirect = null;
            String rol = null;

            String sql = "SELECT rol, user FROM Usuarios WHERE user = ? AND pass = ?";

            try (Connection conn = ConexionBD.conectar();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, codigo);
                pstmt.setString(2, pin);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        rol = rs.getString("rol");
                        userCode = rs.getString("user");

                        if ("ESTUDIANTE".equalsIgnoreCase(rol)) {
                            redirect = "/student";
                        } else if ("GUARDIA".equalsIgnoreCase(rol)) {
                            redirect = "/scanner";
                        }
                    }
                }
            }

            if (userCode != null && redirect != null) {
                sendJson(exchange, 200, true, redirect, userCode);
            } else {
                sendJson(exchange, 401, false, null, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exchange.sendResponseHeaders(500, 0);
                exchange.getResponseBody().close();
            } catch (Exception ex) {
            }
        }
    }

    private void sendJson(HttpExchange exchange, int statusCode, boolean success, String redirectUrl, String userCode)
            throws java.io.IOException {
        String json = String.format("{\"success\": %b, \"redirect\": \"%s\", \"userCode\": \"%s\"}",
                success,
                redirectUrl != null ? redirectUrl : "",
                userCode != null ? userCode : "");

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // CORS for Ngrok

        // Set Cookie if login is successful
        if (success && userCode != null) {
            exchange.getResponseHeaders().add("Set-Cookie", "user_code=" + userCode + "; Path=/; HttpOnly");
        }

        exchange.sendResponseHeaders(statusCode, json.length());
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

    private Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        if (formData == null || formData.isEmpty())
            return map;

        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                try {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    map.put(key, value);
                } catch (Exception e) {
                }
            }
        }
        return map;
    }
}
