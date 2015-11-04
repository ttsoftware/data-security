package service.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PrintJobQueue {

    private static int incrementer;
    private static HashMap<Integer, PrintJob> jobs;
    private static Stack<PrintJob> queue;

    public PrintJobQueue() {
        incrementer = 0;
        jobs = new HashMap<Integer, PrintJob>();
        queue = new Stack<PrintJob>();
    }

    public void prioritize(int jobId) throws Exception {
        if (!jobs.containsKey(jobId)) {
            throw new Exception("No print job exists with id: " + Integer.toString(jobId));
        }

        // remove all existing jobs from queue
        queue.clear();
        // add the prioritized job as the first one to the queue
        queue.add(jobs.get(jobId));

        for (PrintJob job : jobs.values()) {
            queue.add(job);
        }
    }

    public synchronized PrintJob pop() {
        // remove object from both collections
        PrintJob job = queue.pop();
        remove(job);

        return job;
    }

    public synchronized void add(PrintJob job) {
        jobs.put(incrementer++, job);
        queue.add(job);
    }

    public synchronized void remove(PrintJob job) {
        for (Map.Entry<Integer, PrintJob> queuedJob : jobs.entrySet()) {
            if (queuedJob.getValue() == job) {
                jobs.remove(queuedJob.getKey());
                queue.remove(job);
            }
        }
    }

    /**
     * Remove all jobs
     */
    public void clear() {
        queue.clear();
        jobs.clear();
    }
}
