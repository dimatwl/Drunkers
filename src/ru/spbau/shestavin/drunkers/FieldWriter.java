package ru.spbau.shestavin.drunkers;

import java.io.IOException;
import java.util.List;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/11/12
 * Time: 8:04 AM
 */
public interface FieldWriter {

    public void writeField(List<String> inpFieldStrList) throws IOException;

    public void flush() throws IOException;

    public void close();
}
