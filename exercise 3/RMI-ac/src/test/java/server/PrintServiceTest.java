package server;

import shared.DatabaseTest;
import shared.exception.UserAuthenticationException;
import shared.exception.UserPermissionException;
import shared.model.User;
import shared.service.PrintService;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class PrintServiceTest extends DatabaseTest {

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

        User newUser = new User();
        newUser.setName("troels");
        newUser.setPassword("password");

        PrintServer server = new PrintServer();
        server.run();

        PrintService printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");

        printService.print("testfile", "printer1", newUser);
    }
}
