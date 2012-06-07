package ru.spbau.shestavin.drunkers.fields;

import ru.spbau.shestavin.drunkers.core.Field;
import ru.spbau.shestavin.drunkers.core.FieldWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileFieldWriter implements FieldWriter {
    private PrintWriter outputWriter;
    private boolean isClosed = false;
    private Integer numberOfField = 0;
    private final Field field;

    public FileFieldWriter(String inpFileName, Field inpField) throws IOException {
        this.outputWriter = new PrintWriter(new FileWriter(inpFileName));
        this.field = inpField;
    }

    @Override
    public void writeField() throws IOException {
        if (this.isClosed) {
            throw new IOException("Can't write to closed stream.");
        } else {
            this.outputWriter.println("Turn number " + this.numberOfField.toString() + ':');
            for (String outputString : this.field.getTextRepresentation()) {
                this.outputWriter.println(outputString);
            }
            this.outputWriter.println("");
            ++this.numberOfField;
            this.flush();
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.isClosed) {
            throw new IOException("Can't flush closed stream.");
        } else {
            this.outputWriter.flush();
        }
    }

    @Override
    public void close() {
        if (!isClosed) {
            this.outputWriter.close();
        }
        this.isClosed = true;
    }
}
