package server.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseService {

    private static String databaseUrl;
    private static ConnectionSource connectionSource = getSource();

    private static ConnectionSource getSource() {
        if (connectionSource == null) {
            try {
                if (getDatabaseUrl() == null) {
                    setDatabaseUrl("jdbc:sqlite:printservice.db");
                }
                connectionSource = new JdbcPooledConnectionSource(getDatabaseUrl());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connectionSource;
    }

    public static void setDatabaseUrl(String dbUrl) {
        databaseUrl = dbUrl;
        connectionSource = null;
        connectionSource = getSource();
    }

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    public static ConnectionSource getConnectionSource() {
        return getSource();
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
