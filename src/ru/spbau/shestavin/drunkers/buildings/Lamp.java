package ru.spbau.shestavin.drunkers.buildings;


import ru.spbau.shestavin.drunkers.core.FieldObject;

public class Lamp extends FieldObject {
    private final Integer illuminationRadius;

    public Lamp(Integer inpIlluminationRadus) {
        super();
        this.illuminationRadius = inpIlluminationRadus;
    }

    public Integer getIlluminationRadius() {
        return this.illuminationRadius;
    }

    @Override
    public void doTurn() {

    }

    @Override
    public char getSymbolRepresentation() {
        return 'Ф';
    }
}