package dao;

import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.QRAcceso;

public class QRAccesoDAO {

    public QRAcceso buscarPorCodigo(String codigo) {
        // Consulta con los nombres de columnas de tu script SQL
        String sql = "SELECT IdQRAcceso, IdUsuario, CodigoQR, Estado FROM QRAcceso WHERE CodigoQR = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retornamos el objeto con el orden exacto de tu modelo
                    return new QRAcceso(
                        rs.getInt("IdQRAcceso"),
                        rs.getInt("IdUsuario"),
                        rs.getString("CodigoQR"),
                        rs.getInt("Estado")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error en QRAccesoDAO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}