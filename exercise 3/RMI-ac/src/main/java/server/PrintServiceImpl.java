package server;

import server.service.LoginService;
import shared.exception.UserAuthenticationException;
import shared.exception.UserPermissionException;
import server.model.UserPermission;
import shared.service.PrintService;
import shared.PrintJob;
import shared.PrintJobQueue;
import server.model.User;

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
        parameters = new HashMap<>();
        printers = new HashSet<>();

        this.name = name;
        this.registry = registry;
    }

    public void print(String filename, String printer, String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_PRINT)) {
            printQueue.add(new PrintJob(filename, printer));
            printers.add(printer);
        }
    }

    public HashMap<Integer, PrintJob> queue(String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_READ_QUEUE)) {
            return printQueue.getJobs();
        }
        throw new UserAuthenticationException();
    }

    public void topQueue(int jobId, String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_EDIT_QUEUE)) {
            try {
                printQueue.prioritize(jobId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_START)) {
            try {
                registry.bind(name, this);
            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_STOP)) {
            printQueue.clear();
            try {
                registry.unbind(name);
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void restart(String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_RESTART)) {
            printQueue.clear();
            try {
                registry.rebind(name, this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String status(String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_READ_STATUS)) {
            return null;
        }
        throw new UserAuthenticationException(); 
    }

    public String readConfig(String parameter, String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_READ_CONFIG)) {
            return parameters.get(parameter);
        }
        throw new UserAuthenticationException(); 
    }

    public void setConfig(String parameter, String value, String username, String password) throws
            UserAuthenticationException,
            UserPermissionException
    {
        User user = LoginService.login(username, password);

        if (user.hasPermission(UserPermission.CAN_WRITE_CONFIG)) {
            parameters.put(parameter, value);
        }
    }
}
