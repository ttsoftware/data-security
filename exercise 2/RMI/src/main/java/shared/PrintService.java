package shared;

import model.User;

import javax.security.auth.login.LoginException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface PrintService extends Remote {

    /**
     * Authenticates user on current service instance
     * @param user
     */
    void login(User user) throws RemoteException, LoginException;

    /**
     * Prints file filename on the specified printer
     * @param filename
     * @param printer
     * @throws RemoteException
     */
    void print(String filename, String printer) throws RemoteException, LoginException;

    /**
     * @return the print queue on the user's display in lines of the form <job number> <file name>
     * @throws RemoteException
     */
    HashMap<Integer, PrintJob> queue() throws RemoteException, LoginException;

    /**
     * Moves job to the top of the queue
     * @param jobId Job number
     * @throws RemoteException
     */
    void topQueue(int jobId) throws RemoteException, LoginException;

    /**
     * Starts the print server
     * @throws RemoteException
     */
    void start() throws RemoteException, LoginException;

    /**
     * Stops the print server
     * @throws RemoteException
     */
    void stop() throws RemoteException, LoginException;

    /**
     * Stops the print server, clears the print queue and starts the print server again
     * @throws RemoteException
     */
    void restart() throws RemoteException, LoginException;

    /**
     * @return the status of printer on the user's display
     * @throws RemoteException
     */
    String status() throws RemoteException, LoginException;

    /**
     * @param parameter the parameter to display
     * @return the value of the parameter on the user's display
     * @throws RemoteException
     */
    String readConfig(String parameter) throws RemoteException, LoginException;

    /**
     * Sets the parameter to value
     * @param parameter Parameter to update
     * @param value Parameter value
     * @throws RemoteException
     */
    void setConfig(String parameter, String value) throws RemoteException, LoginException;
}
