package ru.spbau.shestavin.drunkers;

import ru.spbau.shestavin.drunkers.abstraction.Field;
import ru.spbau.shestavin.drunkers.abstraction.FieldWriter;
import ru.spbau.shestavin.drunkers.fields.FileFieldWriter;
import ru.spbau.shestavin.drunkers.fields.HexField;
import ru.spbau.shestavin.drunkers.fields.SquareField;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        Field sqr = new SquareField();
        Field hex = new HexField();
        try {
            FieldWriter writerForSqr = new FileFieldWriter("sqr.txt", sqr);
            FieldWriter writerForHex = new FileFieldWriter("hex.txt", hex);
            writerForSqr.writeField();
            writerForHex.writeField();
            for (int i = 0; i < 1000; ++i) {
                sqr.doTurn();
                hex.doTurn();
                writerForSqr.writeField();
                writerForHex.writeField();
            }
            writerForSqr.close();
            writerForHex.close();
        } catch (IOException e) {
            System.err.println("Sorry IO eror occured." + e.getMessage());
        }
    }
}
