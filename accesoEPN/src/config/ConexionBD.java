    package config;

    import java.sql.Connection;
    import java.sql.DriverManager;

    public class ConexionBD {

        private static final String URL = "jdbc:sqlite:Storage/DataBase/bd_acceso_epn.sqlite";

        public static Connection conectar() {
            try {
                System.out.println("Directorio de ejecución: " + System.getProperty("user.dir"));
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection(URL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
