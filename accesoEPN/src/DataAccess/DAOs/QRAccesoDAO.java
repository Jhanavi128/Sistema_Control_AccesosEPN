package DataAccess.DAOs;

import java.sql.*;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import DataAccess.DTOs.QRAccesoDTO;
import Infrastructure.AppException;

public class QRAccesoDAO extends DataHelperSQLiteDAO<QRAccesoDTO> {

    public QRAccesoDAO() throws AppException {
        super(QRAccesoDTO.class, "QRAcceso", "IdQRAcceso");
    }

    public QRAccesoDTO readByCodigo(String codigoQR) throws AppException {
        // SQL basado exactamente en tu CREATE TABLE QRAcceso
        String sql = "SELECT IdQRAcceso, IdUsuario, CodigoQR AS Codigoqr, " +
                     "FechaGeneracion, FechaExpiracion, Estado " +
                     "FROM QRAcceso WHERE CodigoQR = ? AND Estado = 1";
        
        try (PreparedStatement pstmt = openConnection().prepareStatement(sql)) {
            pstmt.setString(1, codigoQR);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (Exception e) {
            throw new AppException("Error al buscar QR por código", e, getClass(), "readByCodigo");
        }
        return null;
    }
}