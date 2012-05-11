package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/21/12
 * Time: 2:50 PM
 */
public class Pillar extends FieldObject {

    public Pillar(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return '#';
    }
}
