package shared.model;

import com.j256.ormlite.dao.Dao;
import shared.DatabaseTest;
import shared.exception.UserPermissionException;
import shared.model.dao.UserDao;
import shared.service.DatabaseService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserTest extends DatabaseTest {

    /**
     * Ensure persistance works with ormlite
     */
    public void testPersistence() throws
            SQLException,
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            UserPermissionException
    {

        UserDao userDao = DatabaseService.getDao(User.class);

        UserRole newUserRole = new UserRole();
        newUserRole.setName("manager");

        DatabaseService.getDao(UserRole.class).create(newUserRole);

        newUserRole.addPermission(UserPermission.CAN_RESTART);
        newUserRole.addPermission(UserPermission.CAN_START);
        newUserRole.addPermission(UserPermission.CAN_STOP);
        newUserRole.addPermission(UserPermission.CAN_PRINT);
        newUserRole.addPermission(UserPermission.CAN_READ_CONFIG);
        newUserRole.addPermission(UserPermission.CAN_WRITE_CONFIG);
        newUserRole.addPermission(UserPermission.CAN_READ_STATUS);
        newUserRole.addPermission(UserPermission.CAN_READ_QUEUE);
        newUserRole.addPermission(UserPermission.CAN_EDIT_QUEUE);

        User newUser = new User();
        newUser.setName("troels");
        newUser.setPassword("password");
        newUser.setRole(newUserRole);

        userDao.create(newUser);

        List<User> userList = userDao.queryForEq("name", "troels");
        User user = userList.get(0);

        assertEquals(userList.size(), 1);
        assertEquals("troels", user.getName());

        assertTrue(user.getRole().hasPermission(UserPermission.CAN_STOP));
    }
}
