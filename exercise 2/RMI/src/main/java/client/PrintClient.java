package client;

import model.User;
import shared.PrintJob;
import shared.PrintService;

import javax.security.auth.login.LoginException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class PrintClient implements Runnable {

    public void run() {
        PrintService printService = null;

        try {
            printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");

            System.out.println("We try to sign in with a valid account");

            User admin = new User("admin", "password");

            printService.print("first.txt", "printer1", admin);
            printService.print("second.txt", "printer1", admin);
            printService.print("third.txt", "printer2", admin);

            HashMap<Integer, PrintJob> queue = printService.queue(admin);

            for (Map.Entry<Integer, PrintJob> job : queue.entrySet()) {
                System.out.println(Integer.toString(job.getKey()) + " : " + job.getValue().getPrinter() + " : " + job.getValue().getFilename());
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        System.out.println("We try to sign in with an invalid account");

        User badGuy = new User("admin", "password123");
        try {
            HashMap<Integer, PrintJob> queue = printService.queue(badGuy);
        }
        catch (LoginException e) {
            e.printStackTrace();
            System.out.println("We could not sign in with invalid account.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrintClient().run();
    }
}
