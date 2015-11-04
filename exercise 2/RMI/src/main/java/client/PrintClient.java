package client;

import shared.PrintJob;
import shared.PrintJobQueue;
import shared.PrintService;
import model.User;

import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PrintClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, UnsupportedEncodingException, NoSuchAlgorithmException, LoginException {

        PrintService printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");

        User user = new User("admin", "password");

        printService.login(user);
        printService.print("first.txt", "printer1");
        printService.print("second.txt", "printer1");
        printService.print("third.txt", "printer2");

        HashMap<Integer, PrintJob> queue = printService.queue();

        for (Map.Entry<Integer, PrintJob> job : queue.entrySet()) {
            System.out.println(Integer.toString(job.getKey()) + " : " + job.getValue().getPrinter() + " : " + job.getValue().getFilename());
        }
    }
}
