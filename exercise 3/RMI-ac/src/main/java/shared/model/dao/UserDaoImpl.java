package shared.model.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import javafx.util.Pair;
import server.HashingService;
import shared.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

    public UserDaoImpl(Class<User> uClass) throws SQLException {
        super(uClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }

    public UserDaoImpl(ConnectionSource connectionSource, Class<User> uClass) throws SQLException {
        super(connectionSource, uClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
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
