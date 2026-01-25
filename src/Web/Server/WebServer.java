package Web.Server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class WebServer {

    public static void start() throws Exception {
        int port = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serve static files
        server.createContext("/", new StaticHandler("assets/login.html"));
        server.createContext("/scanner", new StaticHandler("assets/scanner.html"));
        server.createContext("/student", new StaticHandler("assets/student.html"));
        server.createContext("/assets/", new AssetHandler("assets")); // Handler for CSS/JS/Images

        // API Endpoints
        server.createContext("/auth", new AuthHandler());
        server.createContext("/validar", new ValidateHandler());
        server.createContext("/api/profile", new ProfileHandler()); // New Endpoint

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en puerto " + port);
        System.out.println("Acceda en: http://localhost:" + port + "/");
    }
}
