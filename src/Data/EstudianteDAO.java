package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public boolean insertar(String codigo, String pin, String nombre, String carrera, String foto) {
        String sql = "INSERT INTO Usuarios(user, pass, rol, nombre, carrera, foto_url) VALUES(?, ?, 'ESTUDIANTE', ?, ?, ?)";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.setString(2, pin);
            pstmt.setString(3, nombre);
            pstmt.setString(4, carrera);
            pstmt.setString(5, foto);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(String codigo, String pin, String nombre, String carrera, String foto) {
        String sql = "UPDATE Usuarios SET pass = ?, nombre = ?, carrera = ?, foto_url = ? WHERE user = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pin);
            pstmt.setString(2, nombre);
            pstmt.setString(3, carrera);
            pstmt.setString(4, foto);
            pstmt.setString(5, codigo);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM Usuarios WHERE user = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> listar(String filtro) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE rol = 'ESTUDIANTE'";

        if (filtro != null && !filtro.isEmpty()) {
            sql += " AND (user LIKE ? OR nombre LIKE ?)";
        }

        try (Connection conn = ConexionBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (filtro != null && !filtro.isEmpty()) {
                pstmt.setString(1, "%" + filtro + "%");
                pstmt.setString(2, "%" + filtro + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new String[] {
                        rs.getString("user"),
                        rs.getString("pass"),
                        rs.getString("nombre"),
                        rs.getString("carrera"),
                        rs.getString("foto_url")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
