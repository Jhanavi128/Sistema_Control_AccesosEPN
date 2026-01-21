package dao;

import config.ConexionBD;
import model.Periodo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PeriodoDAO {
    public Periodo buscarPorId(int id) {
        String sql = "SELECT IdPeriodo, Carrera, Semestre, Nombre, FechaInicio, FechaFin, Estado FROM Periodo WHERE IdPeriodo = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Periodo(
                        rs.getInt("IdPeriodo"), rs.getString("Carrera"),
                        rs.getInt("Semestre"), rs.getString("Nombre"),
                        rs.getString("FechaInicio"), rs.getString("FechaFin"),
                        rs.getString("Estado")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error en PeriodoDAO: " + e.getMessage());
        }
        return null;
    }
}