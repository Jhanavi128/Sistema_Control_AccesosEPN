package Web.Server;

import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {

    public static void start() throws Exception {
        int port = 8080;
        
        // 1. Localización del directorio de archivos públicos (HTML, CSS, JS)
        String rootPath = new File("accesoEPN/src/Web/Public").getAbsolutePath();

        // Validación de ruta (ajuste para diferentes entornos de ejecución)
        File dir = new File(rootPath);
        if (!dir.exists()) {
            rootPath = new File("src/Web/Public").getAbsolutePath();
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // 2. Registro de Contextos (Endpoints de la API)
        // Mantenemos /api/guardia como la ruta principal para el escáner
        server.createContext("/login",          new LoginHandler());
        server.createContext("/api/guardia",    new GuardiaHandler());
        server.createContext("/api/estudiante", new EstudianteHandler());
        
        // Mantenemos estos por compatibilidad si tienes otros botones usándolos
        server.createContext("/validate",       new ValidateHandler()); 

        // 3. Manejador de Archivos Estáticos
        // Este debe ir al final para que actúe como "catch-all"
        server.createContext("/", new StaticHandler(rootPath));

        // 4. Configuración del Executor para manejo de múltiples hilos
        server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        
        server.start();

        System.out.println("🚀 Servidor EPN corriendo en: http://localhost:" + port);
        System.out.println("📂 Sirviendo archivos desde: " + rootPath);
        System.out.println("📡 API Guardia lista en: /api/guardia");
    }
}