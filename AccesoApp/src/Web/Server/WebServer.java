package Web.Server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class WebServer {

    public static void start() throws Exception {
        HttpServer server =
            HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new StaticHandler());
        server.createContext("/validar", new ValidateHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en puerto 8080");
    }
}
