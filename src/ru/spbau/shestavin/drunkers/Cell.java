package ru.spbau.shestavin.drunkers;


import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/10/12
 * Time: 11:13 PM
 */
public class Cell {
    private final Pair<Integer, Integer> coordinates;
    private FieldObject object;
    private final List<Cell> neighbours;
    private boolean illumination = false;

    public Cell(Pair<Integer, Integer> inpCoordinates) {
        this.coordinates = inpCoordinates;
        this.neighbours = new ArrayList<Cell>();
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
        this.object = inpObject;
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
}
