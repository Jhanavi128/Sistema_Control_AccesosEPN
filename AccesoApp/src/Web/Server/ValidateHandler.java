package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.URI;

public class ValidateHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            URI uri = exchange.getRequestURI();
            String query = uri.getQuery(); // codigo=XXX
            String codigo = query.split("=")[1];

            // AQUÍ luego llamas a BusinessLogic
            String respuesta = "CODIGO RECIBIDO: " + codigo;

            exchange.sendResponseHeaders(200, respuesta.length());
            OutputStream os = exchange.getResponseBody();
            os.write(respuesta.getBytes());
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
