package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProfileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Handle CORS Preflight
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers",
                        "Content-Type, ngrok-skip-browser-warning, X-User-Code");
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Get Cookie or Header or Header
            List<String> cookies = exchange.getRequestHeaders().get("Cookie");
            List<String> authHeaders = exchange.getRequestHeaders().get("X-User-Code");
            String userCode = null;

            // Priority 1: Header (More reliable for API/Ngrok)
            if (authHeaders != null && !authHeaders.isEmpty()) {
                userCode = authHeaders.get(0);
            }

            // Priority 2: Cookie
            if (userCode == null && cookies != null) {
                for (String cookieHeader : cookies) {
                    for (String cookie : cookieHeader.split("; ")) {
                        if (cookie.startsWith("user_code=")) {
                            userCode = cookie.substring("user_code=".length());
                        }
                    }
                }
            }

            if (userCode == null) {
                sendJson(exchange, 401, "{}"); // Unauthorized
                return;
            }

            // Mock Data Retrieval
            String json = getUserData(userCode);
            sendJson(exchange, 200, json);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exchange.sendResponseHeaders(500, 0);
                exchange.getResponseBody().close();
            } catch (Exception ex) {
            }
        }
    }

    private String getUserData(String code) {
        String sql = "SELECT * FROM Usuarios WHERE user = ?";

        String name = "Desconocido";
        String carrera = "";
        String periodo = "2025-B";
        String photo = "/assets/perfil_john.jpg";

        try (java.sql.Connection conn = Data.ConexionBD.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);

            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("nombre");
                    carrera = rs.getString("carrera");
                    String dbPhoto = rs.getString("foto_url");
                    if (dbPhoto != null && !dbPhoto.isEmpty()) {
                        photo = "/assets/" + dbPhoto; // Assuming assets/ folder
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format(
                "{" +
                        "  \"nombre\": \"%s\"," +
                        "  \"carrera\": \"%s\"," +
                        "  \"periodo\": \"%s\"," +
                        "  \"codigo\": \"%s\"," +
                        "  \"foto\": \"%s\"" +
                        "}",
                name, carrera, periodo, code, photo);
    }

    private void sendJson(HttpExchange exchange, int statusCode, String json) throws java.io.IOException {
        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
