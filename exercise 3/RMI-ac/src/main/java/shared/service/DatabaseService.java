package shared.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseService {

    private static final String DATABASE_URL = "jdbc:sqlite:printservice.db";

    private static ConnectionSource connectionSource = getConnection();

    private static ConnectionSource getConnection() {
        if (connectionSource == null) {
            try {
                connectionSource = new JdbcPooledConnectionSource(DATABASE_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connectionSource;
    }

    public static <D extends Dao<T, ?>, T> D getDao(Class<T> tClass) throws SQLException {
        return DaoManager.createDao(connectionSource, tClass);
    }

    public static <T, ID> int clearTable(Class<T> tClass) throws SQLException {
        return TableUtils.clearTable(connectionSource, tClass);
    }

    public static <T, ID> int dropTable(Class<T> tClass) throws SQLException {
        return TableUtils.dropTable(connectionSource, tClass, false);
    }

    public static <T, ID> int createTable(Class<T> tClass) throws SQLException {
        return TableUtils.createTableIfNotExists(connectionSource, tClass);
    }
}
