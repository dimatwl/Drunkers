package ru.spbau.shestavin.drunkers.abstraction;


import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class Cell {
    private final Pair<Integer, Integer> coordinates;
    private FieldObject object = null;
    private final List<Cell> neighbours = new ArrayList<Cell>();
    private boolean illumination = false;

    public Cell(Pair<Integer, Integer> inpCoordinates) {
        this.coordinates = inpCoordinates;
    }

    public void addNeighbour(Cell inpNeighbour) {
        this.neighbours.add(inpNeighbour);
    }

    public List<Cell> getNeighbours() {
        return this.neighbours;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return this.coordinates;
    }

    public FieldObject getObject() {
        return this.object;
    }

    public void removeObject() {
        if (this.object != null)
            this.object.removeFromField();
        this.object = null;
    }

    public void putObject(FieldObject inpObject) {
        if (this.object != null) {
            this.removeObject();
        }
        this.object = inpObject;
        inpObject.putOnField(this);
    }

    public boolean isEmpty() {
        return this.object == null;
    }

    public char getSymbol() {
        if (this.object != null) {
            return this.object.getSymbolRepresentation();
        } else {
            return ' ';
        }
    }

    public boolean isIlluminated() {
        return this.illumination;
    }

    public void setIllumination(boolean inpIllumination) {
        this.illumination = inpIllumination;
    }

    public int distTo(Cell inpCell) {
        return Math.abs(this.coordinates.fst - inpCell.coordinates.fst) + Math.abs(this.coordinates.snd - inpCell.coordinates.snd);
    }
}
