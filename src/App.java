import Web.Server.WebServer;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // Start Database (AdminPanel does it too, but safe to do early)
            Data.ConexionBD.inicializar();

            // Start Web Server
            WebServer.start();

            // Launch GUI
            javax.swing.SwingUtilities.invokeLater(() -> {
                new Desktop.AdminPanel().setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
