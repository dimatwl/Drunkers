package ru.spbau.shestavin.drunkers;

import java.io.IOException;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/21/12
 * Time: 4:30 PM
 */
public class Main {
    public static void main(String[] args) {
        Field myField = new HexField();
        try {
            FieldWriter myWriter = new FileFieldWriter("test.txt");
            myWriter.writeField(myField.getTextRepresentation());
            for (int i = 0; i < 1000; ++i) {
                myField.doTurn();
                myWriter.writeField(myField.getTextRepresentation());
            }
            myWriter.close();
        } catch (IOException e) {
            System.err.println("Sorry IO eror occured." + e.getMessage());
        }
    }
}
