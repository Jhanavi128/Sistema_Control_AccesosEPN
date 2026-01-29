package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class AssetHandler implements HttpHandler {
    private final String rootDir;

    public AssetHandler(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        // Remove "/assets/" prefix to get relative path
        String relativePath = path.replace("/assets/", "");
        // Look in CWD/rootDir (e.g., ./assets/image.png)
        File file = new File(rootDir + "/" + relativePath);

        if (file.exists() && !file.isDirectory()) {
            String mimeType = "application/octet-stream";
            if (path.endsWith(".css"))
                mimeType = "text/css";
            else if (path.endsWith(".js"))
                mimeType = "application/javascript";
            else if (path.endsWith(".png"))
                mimeType = "image/png";
            else if (path.endsWith(".jpg") || path.endsWith(".jpeg"))
                mimeType = "image/jpeg";

            exchange.getResponseHeaders().add("Content-Type", mimeType);
            exchange.sendResponseHeaders(200, file.length());

            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        } else {
            String response = "404 (Not Found)\n";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
