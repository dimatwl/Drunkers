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
    private final Pair<Integer, Integer> myCoordinates;
    private FieldObject myObject;
    private final List<Cell> myNeighbours;
    private boolean illumination = false;

    public Cell(Pair<Integer, Integer> inpCoordinates) {
        this.myCoordinates = inpCoordinates;
        this.myNeighbours = new ArrayList<Cell>();
    }

    public void addNeighbour(Cell inpNeighbour) {
        this.myNeighbours.add(inpNeighbour);
    }

    public List<Cell> getNeighbours() {
        return this.myNeighbours;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return this.myCoordinates;
    }

    public FieldObject getObject() {
        return this.myObject;
    }

    public void removeObject() {
        if (this.myObject != null)
            this.myObject.removeFromField();
        this.myObject = null;
    }

    public void putObject(FieldObject inpObject) {
        this.myObject = inpObject;
    }

    public boolean isEmpty() {
        return this.myObject == null;
    }

    public char getSymbol() {
        if (this.myObject != null) {
            return this.myObject.getSymbolRepresentation();
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
