import Web.Server.WebServer;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            WebServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}