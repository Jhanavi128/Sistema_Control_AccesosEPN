public class QRAccesoDAO extends DataHelperSQLiteDAO <QRAccesoDTO> {
    public QRAccesoDAO() throws AppException {
        super(QRAccesoDTO.class, "QRAcceso", "IdQRAcceso");
    }
}
