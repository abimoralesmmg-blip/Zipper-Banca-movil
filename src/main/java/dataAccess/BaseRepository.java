package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseRepository<T> implements IBaseRepository<T> {
    protected static final String urlConexion = "jdbc:mysql://localhost:3306/BancaMejorada";
    protected static final String user = "root";
    protected static final String clave = "1234";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlConexion, user, clave);
    }
}
