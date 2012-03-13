package ru.spbau.shestavin.drunkers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 12:16 AM
 */
public class FileFieldWriter implements FieldWriter{
    private PrintWriter myOutputWriter;
    private boolean isClosed = false;
    private Integer numberOfField = 0;

    public FileFieldWriter(String inpFileName) throws IOException {
        this.myOutputWriter = new PrintWriter(new FileWriter(inpFileName));
    }

    @Override
    public void writeField(List<String> inpFieldStrList) throws IOException {
        if (this.isClosed) {
            throw new IOException("Can't write to closed stream.");
        } else {
            this.myOutputWriter.println("Turn number " + this.numberOfField.toString());
            for (String outputString : inpFieldStrList) {
                this.myOutputWriter.println(outputString);
            }
            this.myOutputWriter.println("");
            ++this.numberOfField;
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.isClosed) {
            throw new IOException("Can't flush closed stream.");
        } else {
            this.myOutputWriter.flush();
        }
    }

    @Override
    public void close() {
        if (!isClosed) {
            this.myOutputWriter.close();
        }
        this.isClosed = true;
    }
}
