package ru.spbau.shestavin.drunkers.core;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Cell {
    private final Point coordinates;
    private FieldObject object = null;
    private final List<Cell> neighbours = new ArrayList<Cell>();
    private boolean illumination = false;

    public Cell(Point inpCoordinates) {
        this.coordinates = inpCoordinates;
    }

    public Cell(Integer fst, Integer snd) {
        this.coordinates = new Point(fst, snd);
    }

    public void addNeighbour(Cell inpNeighbour) {
        this.neighbours.add(inpNeighbour);
    }

    public List<Cell> getNeighbours() {
        return this.neighbours;
    }

    public Point getCoordinates() {
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
        return Math.abs(this.coordinates.x - inpCell.coordinates.x) + Math.abs(this.coordinates.y - inpCell.coordinates.y);
    }
}
