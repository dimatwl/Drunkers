package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/21/12
 * Time: 5:17 PM
 */
public class Bottle extends FieldObject {

    public Bottle(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return 'B';
    }
}
