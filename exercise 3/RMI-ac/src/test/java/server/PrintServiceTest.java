package server;

import server.model.User;
import server.model.UserPermission;
import server.model.UserRole;
import server.model.dao.UserDaoImpl;
import server.service.DatabaseService;
import shared.DatabaseTest;
import shared.PrintJob;
import shared.exception.UserAuthenticationException;
import shared.exception.UserPermissionException;
import shared.service.PrintService;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

public class PrintServiceTest extends DatabaseTest {

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

        newUser = new User();
        newUser.setName("David");
        newUser.setPassword("password");
        newUser.setRole(newUserRole);

        userDao.create(newUser);
    }

    public void testPrint() throws
            SQLException,
            UnsupportedEncodingException,
            NoSuchAlgorithmException,
            RemoteException,
            MalformedURLException,
            NotBoundException,
            UserAuthenticationException,
            UserPermissionException
    {

        PrintServer server = new PrintServer();
        server.run();

        PrintService printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");

        printService.print("testfile", "printer1", "troels", "password");

        HashMap<Integer, PrintJob> queue = printService.queue("troels", "password");

        PrintJob job = queue.get(0);

        // file was actually added to the queue
        assertEquals(job.getFilename(), "testfile");
        assertEquals(job.getPrinter(), "printer1");
    }

    /**
     * Make sure users with different policy cannot perform operations outside of their role
     */
    public void testAccessPolicy() throws
            RemoteException,
            NotBoundException,
            MalformedURLException,
            UserAuthenticationException,
            UserPermissionException
    {

        PrintServer server = new PrintServer();
        server.run();

        PrintService printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");

        try {
            // David is only allowed to print and view the print queue.
            printService.restart("David", "password");
        }
        catch (UserPermissionException e) {
            assertEquals("User did not meet permission requirements for operation: CAN_RESTART", e.getMessage());
        }
    }
}
