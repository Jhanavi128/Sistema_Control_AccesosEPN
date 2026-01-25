package Web.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ValidateHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            URI uri = exchange.getRequestURI();
            String query = uri.getQuery(); // codigo=XXX

            if (query == null || !query.startsWith("codigo=")) {
                String error = "Codigo no recibido";
                exchange.sendResponseHeaders(400, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.getResponseBody().close();
                return;
            }

            String codigo = query.split("=", 2)[1];
            //DECODIFICA QR
            codigo = URLDecoder.decode(codigo, StandardCharsets.UTF_8);

            // 🔥 AQUÍ LLEGA EL QR A JAVA Y LO IMPRIME EN CONSOLA
            System.out.println("QR RECIBIDO EN JAVA: " + codigo);

            // Aquí luego llamas a la lógica de negocios
            // BusinessLogic.validarCodigo(codigo);

            String respuesta = "Codigo recibido correctamente";

            exchange.sendResponseHeaders(200, respuesta.length());
            OutputStream os = exchange.getResponseBody();
            os.write(respuesta.getBytes());
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
