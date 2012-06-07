package ru.spbau.shestavin.drunkers.characters;


import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;

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
