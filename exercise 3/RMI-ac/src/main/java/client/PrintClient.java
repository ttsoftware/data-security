package client;

import shared.service.PrintService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PrintClient implements Runnable {

    public void run() {
        PrintService printService = null;

        try {
            printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");
        }
        catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrintClient().run();
    }
}
