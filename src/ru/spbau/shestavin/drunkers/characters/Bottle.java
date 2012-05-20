package ru.spbau.shestavin.drunkers.characters;


import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;

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
