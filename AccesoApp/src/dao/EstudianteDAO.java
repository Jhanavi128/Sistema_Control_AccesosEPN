package dao;

import config.ConexionBD;
import model.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteDAO {
    public Estudiante buscarPorCedula(String cedula) {
        String sql = "SELECT IdEstudiante, IdPeriodo, Nombre, Apellido, Cedula, CodigoUnico, Sexo, Estado FROM Estudiante WHERE Cedula = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estudiante(
                        rs.getInt("IdEstudiante"), rs.getInt("IdPeriodo"),
                        rs.getString("Nombre"), rs.getString("Apellido"),
                        rs.getString("Cedula"), rs.getString("CodigoUnico"),
                        rs.getString("Sexo"), rs.getString("Estado")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error en EstudianteDAO: " + e.getMessage());
        }
        return null;
    }
}