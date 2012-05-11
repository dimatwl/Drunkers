package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/21/12
 * Time: 3:49 PM
 */
public class Lamp extends FieldObject {

    public Lamp(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return 'Ф';
    }
}