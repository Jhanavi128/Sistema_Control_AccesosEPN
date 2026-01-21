package dao;

import config.ConexionBD;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    public Usuario buscarPorId(int id) {
        String sql = "SELECT IdUsuario, Nombre, Apellido, Correo, Contrasena, Rol, Estado FROM Usuario WHERE IdUsuario = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("IdUsuario"), rs.getString("Nombre"),
                        rs.getString("Apellido"), rs.getString("Correo"),
                        rs.getString("Contrasena"), rs.getString("Rol"), rs.getInt("Estado")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error en UsuarioDAO: " + e.getMessage());
        }
        return null;
    }
}