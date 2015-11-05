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

public class PrintServiceImpl extends UnicastRemoteObject implements PrintService {

    Registry registry;
    String name;

    PrintJobQueue printQueue;
    HashMap<String, String> parameters;
    HashSet<String> printers;

    protected PrintServiceImpl(String name, Registry registry) throws RemoteException {
        super();

        printQueue = new PrintJobQueue();
        parameters = new HashMap<String, String>();
        printers = new HashSet<String>();

        this.name = name;
        this.registry = registry;
    }

    public void print(String filename, String printer, User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            printQueue.add(new PrintJob(filename, printer));
            printers.add(printer);
        }
    }

    public HashMap<Integer, PrintJob> queue(User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            return printQueue.getJobs();
        }
        throw new LoginException();
    }

    public void topQueue(int jobId, User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            try {
                printQueue.prioritize(jobId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            try {
                registry.bind(name, this);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            printQueue.clear();
            try {
                registry.unbind(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void restart(User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            printQueue.clear();
            try {
                registry.rebind(name, this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String status(User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            return null;
        }
        throw new LoginException();
    }

    public String readConfig(String parameter, User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            return parameters.get(parameter);
        }
        throw new LoginException();
    }

    public void setConfig(String parameter, String value, User user) throws LoginException {
        if (UserLoginService.getInstance().login(user)) {
            parameters.put(parameter, value);
        }
    }
}
