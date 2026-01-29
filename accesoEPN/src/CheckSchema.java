
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckSchema {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:accesoEPN/Storage/DataBase/database.db"; // Adjust path if needed
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("PRAGMA table_info(Estudiante);");
            System.out.println("Columns in Estudiante:");
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("type"));
            }

            rs = stmt.executeQuery("PRAGMA table_info(Usuario);");
            System.out.println("Columns in Usuario:");
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
