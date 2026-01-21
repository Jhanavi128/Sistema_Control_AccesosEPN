package dao;

import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistroIngresoDAO {
    public boolean registrar(int idQR, String resultado) {
        String sql = "INSERT INTO RegistroIngreso (IdQRAcceso, FechaHora, Resultado) VALUES (?, datetime('now','localtime'), ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idQR);
            ps.setString(2, resultado);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        }
    }
}