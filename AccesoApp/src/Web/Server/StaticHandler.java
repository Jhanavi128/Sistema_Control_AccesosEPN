package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            byte[] html = Files.readAllBytes(
                Paths.get("src/Web/Public/index.html")
            );

            exchange.getResponseHeaders()
                    .add("Content-Type", "text/html");

            exchange.sendResponseHeaders(200, html.length);

            OutputStream os = exchange.getResponseBody();
            os.write(html);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
