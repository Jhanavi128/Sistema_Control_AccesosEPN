package DataAccess.Helpers;

import DataAccess.Interfaces.IDAO;
import Infrastructure.AppConfig;
import Infrastructure.AppException;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class DataHelperSQLiteDAO<T> implements IDAO<T> {
    protected final Class<T> DTOClass;
    protected final String tableName;
    protected final String tablePK;
    private static Connection conn = null;

    /**
     * Constructor del Helper
     * 
     * @param dtoClass  La clase del DTO (ej: QRAccesoDTO.class)
     * @param tableName Nombre de la tabla en SQLite
     * @param tablePK   Nombre de la clave primaria
     */
    public DataHelperSQLiteDAO(Class<T> dtoClass, String tableName, String tablePK) {
        this.DTOClass = dtoClass;
        this.tableName = tableName;
        this.tablePK = tablePK;
    }

    protected static synchronized Connection openConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(AppConfig.DATABASE);
        }
        return conn;
    }

    protected String getDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public List<T> readAll() throws AppException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE Estado = 'A'";
        try (Statement stmt = openConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            throw new AppException("Error al leer todos los registros", e, getClass(), "readAll");
        }
        return list;
    }

    public T readBy(Integer id) throws AppException {
        String sql = "SELECT * FROM " + tableName + " WHERE Estado = 'A' AND " + tablePK + " = " + id;
        try (Statement stmt = openConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next())
                return mapResultSetToEntity(rs);
        } catch (Exception e) {
            throw new AppException("Error al leer por ID", e, getClass(), "readBy");
        }
        return null;
    }

    public boolean create(T entity) throws AppException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Field field : DTOClass.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isTransient(field.getModifiers())
                    && !field.getName().equalsIgnoreCase(tablePK)) {
                field.setAccessible(true);
                try {
                    columns.append(field.getName()).append(",");
                    Object val = field.get(entity);
                    values.append("'").append(val == null ? "" : val).append("',");
                } catch (Exception e) {
                }
            }
        }
        String sql = "INSERT INTO " + tableName + " (" + columns.substring(0, columns.length() - 1) +
                ") VALUES (" + values.substring(0, values.length() - 1) + ")";
        try (Statement stmt = openConnection().createStatement()) {
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new AppException("Error al crear registro", e, getClass(), "create");
        }
    }

    public boolean update(T entity) throws AppException {
        StringBuilder setClause = new StringBuilder();
        Object idValue = null;
        for (Field field : DTOClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (field.getName().equalsIgnoreCase(tablePK)) {
                    idValue = field.get(entity);
                } else {
                    setClause.append(field.getName()).append("='").append(field.get(entity)).append("',");
                }
            } catch (Exception e) {
            }
        }
        String sql = "UPDATE " + tableName + " SET " + setClause.substring(0, setClause.length() - 1) +
                " WHERE " + tablePK + " = " + idValue;
        try (Statement stmt = openConnection().createStatement()) {
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new AppException("Error al actualizar registro", e, getClass(), "update");
        }
    }

    public boolean delete(Integer id) throws AppException {
        String sql = "UPDATE " + tableName + " SET Estado = 'X', FechaModifica = '" + getDateTimeNow() +
                "' WHERE " + tablePK + " = " + id;
        try (Statement stmt = openConnection().createStatement()) {
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new AppException("Error al eliminar (borrado lógico)", e, getClass(), "delete");
        }
    }

    public Integer getMaxReg() throws AppException {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE Estado = 'A'";
        try (Statement stmt = openConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new AppException("Error al contar registros", e, getClass(), "getMaxReg");
        }
    }

    protected T mapResultSetToEntity(ResultSet rs) throws Exception {
        T instance = DTOClass.getDeclaredConstructor().newInstance();
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String colName = meta.getColumnLabel(i);
            try {
                Field field = DTOClass.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(instance, rs.getObject(i));
            } catch (NoSuchFieldException e) {
            }
        }
        return instance;
    }
}