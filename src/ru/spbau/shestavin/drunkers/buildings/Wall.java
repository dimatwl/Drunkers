package ru.spbau.shestavin.drunkers.buildings;


import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;

public class Wall extends FieldObject {

    public Wall(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return '0';
    }
}
