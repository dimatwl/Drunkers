package ru.spbau.shestavin.drunkers;

import java.io.IOException;


public interface FieldWriter {

    public void writeField() throws IOException;

    public void flush() throws IOException;

    public void close();
}
