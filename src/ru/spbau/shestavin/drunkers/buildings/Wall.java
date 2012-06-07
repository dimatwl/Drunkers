package ru.spbau.shestavin.drunkers.buildings;


import ru.spbau.shestavin.drunkers.core.FieldObject;

public class Wall extends FieldObject {

    public Wall() {
        super();
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return '0';
    }
}
