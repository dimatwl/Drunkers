package ru.spbau.shestavin.drunkers.buildings;


import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;

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