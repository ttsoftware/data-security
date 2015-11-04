package server;

import shared.PrintService;
import shared.PrintJob;
import shared.PrintJobQueue;
import model.User;

import javax.security.auth.login.LoginException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PrintServant extends UnicastRemoteObject implements PrintService {

    private boolean isAuthenticated;

    Registry registry;
    String name;

    PrintJobQueue printQueue;
    HashMap<String, String> parameters;
    HashSet<String> printers;

    protected PrintServant(String name, Registry registry) throws RemoteException {
        super();

        isAuthenticated = false;

        printQueue = new PrintJobQueue();
        parameters = new HashMap<String, String>();
        printers = new HashSet<String>();

        this.name = name;
        this.registry = registry;
    }

    public void login(User user) throws LoginException {
        isAuthenticated = UserLoginService.getInstance().login(user);
    }

    public void print(String filename, String printer) throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        printQueue.add(new PrintJob(filename, printer));
    }

    public HashMap<Integer, PrintJob> queue() throws LoginException {
        if (isAuthenticated) {
            return printQueue.getJobs();
        }
        throw new LoginException();
    }

    public void topQueue(int jobId) throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        try {
            printQueue.prioritize(jobId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        try {
            registry.bind(name, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        printQueue.clear();
        try {
            registry.unbind(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void restart() throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        printQueue.clear();
        try {
            registry.rebind(name, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String status() throws LoginException {
        if (isAuthenticated) {
            return null;
        }
        throw new LoginException();
    }

    public String readConfig(String parameter) throws LoginException {
        if (isAuthenticated) {
            return parameters.get(parameter);
        }
        throw new LoginException();
    }

    public void setConfig(String parameter, String value) throws LoginException {
        if (!isAuthenticated) {
            throw new LoginException();
        }
        parameters.put(parameter, value);
    }
}
