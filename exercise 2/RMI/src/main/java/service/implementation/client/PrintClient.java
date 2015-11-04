package service.implementation.client;

import service.PrintService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PrintClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

        PrintService printService = (PrintService) Naming.lookup("rmi://localhost:30000/printserver");


    }
}
