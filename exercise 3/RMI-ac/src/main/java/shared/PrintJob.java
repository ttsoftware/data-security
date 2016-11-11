package shared;

import java.io.Serializable;

public class PrintJob implements Serializable {

    private final String printer;
    private final String filename;

    public PrintJob(String filename, String printer) {
        this.filename = filename;
        this.printer = printer;
    }

    public String getFilename() {
        return filename;
    }

    public String getPrinter() {
        return printer;
    }
}
