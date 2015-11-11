package shared.model.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import javafx.util.Pair;
import server.HashingService;
import shared.model.User;
import shared.service.DatabaseService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserDao extends BaseDaoImpl<User, Integer> implements Dao<User, Integer> {

    protected UserDao() throws SQLException {
        super(DatabaseService.getConnectionSource(), User.class);
    }

    protected UserDao(Class<User> uClass) throws SQLException {
        super(uClass);
    }

    protected UserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }

    protected UserDao(ConnectionSource connectionSource, Class<User> uClass) throws SQLException {
        super(connectionSource, uClass);
    }

    protected UserDao(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    /**
     * Make sure we hash password before storing
     *
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int create(User user) throws SQLException {

        try {
            Pair<String,String> hash = HashingService.hash(user.getPassword());

            user.setPassword(hash.getKey());
            user.setSalt(hash.getValue());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new SQLException(e);
        }

        return super.create(user);
    }
}
