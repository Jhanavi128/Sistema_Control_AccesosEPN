package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;

public class StaticHandler implements HttpHandler {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
            .getLogger(StaticHandler.class.getName());
    private final String rootPath;

    public StaticHandler(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        // 1. Manejar la ruta raíz: si piden "/", servimos "login.html"
        if (requestPath.equals("/")) {
            requestPath = "/login.html";
        }

        // 2. Construir la ruta al archivo físico
        File file = new File(rootPath, requestPath);

        // DEBUG: Log instead of System.out
        LOGGER.info("🔍 Buscando: " + file.getAbsolutePath());

        if (file.exists() && !file.isDirectory()) {
            // 3. Detectar el tipo de contenido (Content-Type)
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                // Corrección manual para archivos comunes si probeContentType falla
                if (requestPath.endsWith(".css"))
                    contentType = "text/css";
                else if (requestPath.endsWith(".js"))
                    contentType = "application/javascript";
                else
                    contentType = "text/plain";
            }

            // 4. Enviar el archivo
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());

            try (OutputStream os = exchange.getResponseBody();
                    FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        } else {
            // 5. Error 404 personalizado
            LOGGER.warning("❌ Archivo no encontrado: " + file.getAbsolutePath());
            String response = "404 (Not Found): El archivo " + requestPath + " no existe en el servidor.";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}