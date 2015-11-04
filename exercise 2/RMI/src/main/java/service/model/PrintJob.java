package service.model;

public class PrintJob {

    private final String printer;
    private final String filename;

    public PrintJob(String printer, String filename) {
        this.printer = printer;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public String getPrinter() {
        return printer;
    }
}
