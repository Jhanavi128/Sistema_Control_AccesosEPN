import App.DesktopApp.Forms.AdminPanel;
import Web.Server.WebServer;
import javax.swing.SwingUtilities;
import Infrastructure.Tools.CMDColor;

public class App {
     public static void main(String[] args) throws Exception {
        System.out.println(CMDColor.BLUE + "--- Iniciando Sistema de Control de Accesos EPN ---" + CMDColor.RESET);

        // 1. Iniciamos el Servidor Web en un hilo independiente
        // Esto permite que el servidor corra de fondo sin bloquear la interfaz gráfica
        Thread webThread = new Thread(() -> {
            try {
                System.out.println(CMDColor.GREEN + "[Web] Servidor iniciando en puerto 8080..." + CMDColor.RESET);
                WebServer.start();
            } catch (Exception e) {
                System.err.println(CMDColor.RED + "[Web Error] No se pudo iniciar el servidor: " + e.getMessage() + CMDColor.RESET);
            }
        });
        
        // Marcamos el hilo como 'Daemon' para que se cierre automáticamente al cerrar la GUI
        webThread.setDaemon(true); 
        webThread.start();

        // 2. Iniciamos la Interfaz Gráfica de Administrador
        // SwingUtilities.invokeLater asegura que la GUI se ejecute de forma segura
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println(CMDColor.GREEN + "[Desktop] Abriendo Panel de Administrador..." + CMDColor.RESET);
                AdminPanel admin = new AdminPanel();
                admin.setVisible(true);
            } catch (Exception e) {
                System.err.println(CMDColor.RED + "[Desktop Error] Error al abrir la interfaz: " + e.getMessage() + CMDColor.RESET);
            }
        });
    }
}
// import Web.Server.WebServer;

// public class App {
//     public static void main(String[] args) throws Exception {
//         try {
//             WebServer.start();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }