package service.implementation.server;

import service.PrintService;
import service.model.PrintJob;
import service.model.PrintJobQueue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PrintServant extends UnicastRemoteObject implements PrintService {

    Registry registry;
    String name;

    PrintJobQueue printQueue;
    Set<String> printers;
    HashMap<String, String> parameters;

    protected PrintServant(String name, Registry registry) throws RemoteException {
        super();

        this.name = name;
        this.registry = registry;
    }

    public void print(String filename, String printer) {
        printQueue.add(new PrintJob(filename, printer));
    }

    public PrintJobQueue queue() {
        return printQueue;
    }

    public void topQueue(int jobId) {
        try {
            printQueue.prioritize(jobId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            registry.bind(name, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        printQueue.clear();
        try {
            registry.unbind(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        printQueue.clear();
        try {
            registry.rebind(name, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String status() {
        return null;
    }

    public String readConfig(String parameter) {
        return parameters.get(parameter);
    }

    public void setConfig(String parameter, String value) {
        parameters.put(parameter, value);
    }
}
