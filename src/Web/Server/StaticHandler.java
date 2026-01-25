package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;

public class StaticHandler implements HttpHandler {

    private final String resourcePath;

    public StaticHandler(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public StaticHandler() {
        this("Web/Public/index.html"); // Default fallback
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Try to find the file in multiple locations
            // 1. Direct path (Environment Production/Dist or compiled) -> assets/login.html
            java.io.File file = new java.io.File(resourcePath);

            // 2. Dev path fallback -> src/assets/login.html
            if (!file.exists()) {
                file = new java.io.File("src/" + resourcePath);
            }

            java.io.InputStream is = null;

            if (file.exists()) {
                is = new java.io.FileInputStream(file);
            }

            if (is == null) {
                String cwd = new java.io.File(".").getAbsolutePath();
                String path1 = new java.io.File(resourcePath).getAbsolutePath();
                String path2 = new java.io.File("src/" + resourcePath).getAbsolutePath();

                System.err.println("Recurso no encontrado: " + resourcePath);
                System.err.println("  CWD: " + cwd);
                System.err.println("  Buscado en: " + path1);

                String error = "<h1>404 Recurso no encontrado</h1>" +
                        "<p>Recurso: " + resourcePath + "</p>" +
                        "<p><b>Debug Info:</b></p>" +
                        "<ul>" +
                        "<li>Directorio Actual (CWD): " + cwd + "</li>" +
                        "<li>Intento 1: " + path1 + " ("
                        + (new java.io.File(resourcePath).exists() ? "Existe" : "No existe") + ")</li>" +
                        "<li>Intento 2: " + path2 + " ("
                        + (new java.io.File("src/" + resourcePath).exists() ? "Existe" : "No existe") + ")</li>" +
                        "</ul>";

                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                exchange.sendResponseHeaders(404, error.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(error.getBytes());
                os.close();
                return;
            }

            byte[] html = is.readAllBytes();
            is.close();

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, html.length);

            OutputStream os = exchange.getResponseBody();
            os.write(html);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exchange.sendResponseHeaders(500, 0);
                exchange.getResponseBody().close();
            } catch (Exception ex) {
                // Ignorar error al enviar error
            }
        }
    }
}
