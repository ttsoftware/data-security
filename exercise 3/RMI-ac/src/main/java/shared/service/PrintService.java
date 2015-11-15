package shared.service;

import shared.PrintJob;
import shared.exception.UserAuthenticationException;
import shared.exception.UserPermissionException;
import shared.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface PrintService extends Remote {

    /**
     * Prints file filename on the specified printer
     * @param filename
     * @param printer
     * @throws RemoteException
     */
    void print(String filename, String printer, String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * @return the print queue on the user's display in lines of the form <job number> <file name>
     * @throws RemoteException
     */
    HashMap<Integer, PrintJob> queue(String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * Moves job to the top of the queue
     * @param jobId Job number
     * @throws RemoteException
     */
    void topQueue(int jobId, String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * Starts the print server
     * @throws RemoteException
     */
    void start(String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * Stops the print server
     * @throws RemoteException
     */
    void stop(String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * Stops the print server, clears the print queue and starts the print server again
     * @throws RemoteException
     */
    void restart(String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * @return the status of printer on the user's display
     * @throws RemoteException
     */
    String status(String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * @param parameter the parameter to display
     * @return the value of the parameter on the user's display
     * @throws RemoteException
     */
    String readConfig(String parameter, String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;

    /**
     * Sets the parameter to value
     * @param parameter Parameter to update
     * @param value Parameter value
     * @throws RemoteException
     */
    void setConfig(String parameter, String value, String username, String password) throws RemoteException, UserAuthenticationException, UserPermissionException;
}
