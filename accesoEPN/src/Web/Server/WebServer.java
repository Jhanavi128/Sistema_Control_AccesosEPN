package Web.Server;

import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebServer {

    public static void start() throws Exception {
        int port = 8080;
        
        // 1. Ruta directa (Hardcoded para máxima velocidad)
        // Usamos el separador del sistema para evitar problemas de compatibilidad
        String rootPath = new File("accesoEPN/src/Web/Public").getAbsolutePath();

        // 2. Validación rápida
        File dir = new File(rootPath);
        if (!dir.exists()) {
            // Solo si falla la ruta principal, intentamos la alternativa corta
            rootPath = new File("src/Web/Public").getAbsolutePath();
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // 3. Registro de Contextos (Ordenados por frecuencia de uso)
        server.createContext("/login", new LoginHandler());
        server.createContext("/validate", new ValidateHandler()); // El guardia usará mucho este
        server.createContext("/guardia", new GuardiaHandler());
        server.createContext("/api/estudiante", new EstudianteHandler());
        // En tu método main o donde inicies el servidor:
        server.createContext("/validarAcceso", new GuardiaHandler());
        
        // El manejador de estáticos siempre al final para que no interfiera con la API
        server.createContext("/", new StaticHandler(rootPath));

        // 4. Executor con pool pequeño para inicio instantáneo
        server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        
        server.start();

        System.out.println("🚀 WebServer Express listo en: http://localhost:" + port);
        System.out.println("📂 Directorio: " + rootPath);
    }
}