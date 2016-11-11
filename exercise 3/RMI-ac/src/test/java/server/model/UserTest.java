package server.model;

import server.model.dao.UserDaoImpl;
import server.service.DatabaseService;
import shared.DatabaseTest;
import shared.exception.UserPermissionException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserTest extends DatabaseTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        UserDaoImpl userDao = DatabaseService.getDao(User.class);

        UserRole newUserRole = new UserRole();
        newUserRole.setName("admin");

        DatabaseService.getDao(UserRole.class).create(newUserRole);

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
    }

    /**
     * Ensure persistance works with ormlite
     */
    public void testPersistence() throws
            SQLException,
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            UserPermissionException
    {

        UserDaoImpl userDao = DatabaseService.getDao(User.class);

        List<User> userList = userDao.queryForEq("name", "troels");
        User user = userList.get(0);

        assertEquals(userList.size(), 1);
        assertEquals("troels", user.getName());
    }

    public void testHasPermission() throws
            SQLException,
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            UserPermissionException
    {

        UserDaoImpl userDao = DatabaseService.getDao(User.class);

        List<User> userList = userDao.queryForEq("name", "troels");
        User user = userList.get(0);

        user.addPermission(UserPermission.CAN_RESTART);
        user.addPermission(UserPermission.CAN_START);
        user.addPermission(UserPermission.CAN_STOP);
        userDao.update(user);

        assertTrue(user.hasPermission(UserPermission.CAN_STOP));
        assertTrue(user.hasPermission(UserPermission.CAN_PRINT));
        assertTrue(user.hasPermission(UserPermission.CAN_RESTART));

        user.removePermission(UserPermission.CAN_RESTART);
        userDao.update(user);

        try {
            assertFalse(user.hasPermission(UserPermission.CAN_RESTART));
        }
        catch (UserPermissionException e) {
            // Success!
        }
    }
}
