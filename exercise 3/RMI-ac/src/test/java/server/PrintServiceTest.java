package server;

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
}
