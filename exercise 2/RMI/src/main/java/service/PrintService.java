package service;

import service.model.PrintJobQueue;
import java.rmi.Remote;

public interface PrintService extends Remote {

    /**
     * Prints file filename on the specified printer
     * @param filename
     * @param printer
     */
    void print(String filename, String printer);

    /**
     * @return the print queue on the user's display in lines of the form <job number> <file name>
     */
    PrintJobQueue queue();

    /**
     * Moves job to the top of the queue
     * @param jobId Job number
     */
    void topQueue(int jobId);

    /**
     * Starts the print server
     */
    void start();

    /**
     * Stops the print server
     */
    void stop();

    /**
     * Stops the print server, clears the print queue and starts the print server again
     */
    void restart();

    /**
     * @return the status of printer on the user's display
     */
    String status();

    /**
     * @param parameter the parameter to display
     * @return the value of the parameter on the user's display
     */
    String readConfig(String parameter);

    /**
     * Sets the parameter to value
     * @param parameter Parameter to update
     * @param value Parameter value
     */
    void setConfig(String parameter, String value);
}
