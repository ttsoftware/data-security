package shared.model.dao;

import com.j256.ormlite.dao.Dao;
import shared.model.User;

import java.sql.SQLException;

public interface UserDao extends Dao<User, Integer> {

    @Override
    int create(User user) throws SQLException;
}
