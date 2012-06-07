package ru.spbau.shestavin.drunkers.core;

import ru.spbau.shestavin.drunkers.buildings.Lamp;
import ru.spbau.shestavin.drunkers.buildings.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Field {
    protected final Integer rowSizeForUser;
    protected final Integer colSizeForUser;
    protected final Integer rowSizeReal;
    protected final Integer colSizeReal;

    private final List<Cell> cells = new ArrayList<Cell>();


    public void putObjectOnField(Point inpPoint, FieldObject inpObject) throws NoSuchPositionException {
        if (inpPoint.x < 0 || inpPoint.y < 0 || inpPoint.x > rowSizeForUser - 1 || inpPoint.y > colSizeForUser - 1) {
            throw new NoSuchPositionException("There is no position (" + inpPoint.x + "," + inpPoint.y + ") on field.");
        } else {
            this.cellAt(inpPoint.x + 1, inpPoint.y + 1).putObject(inpObject);
            if (inpObject instanceof Lamp) {
                this.setIllumination((Lamp) inpObject);
            }
        }
    }

    private void setIllumination(Lamp inpLamp) {
        Integer radius = inpLamp.getIlluminationRadius();

        Integer x = inpLamp.getPosition().getCoordinates().x;
        Integer y = inpLamp.getPosition().getCoordinates().y;

        Integer lx = x - radius;
        Integer rx = x + radius;

        Integer ly = y - radius;
        Integer ry = y + radius;

        if (lx < 0) lx = 0;
        if (rx > this.colSizeReal - 1) rx = this.colSizeReal - 1;

        if (ly < 0) ly = 0;
        if (ry > this.rowSizeReal - 1) ry = this.rowSizeReal - 1;
        for (int i = lx; i <= lx; ++i) {
            for (int j = ly; j <= ly; ++j) {
                this.cellAt(i, j).setIllumination(true);
            }
        }
    }

    public void putObjectOnBorder(Point inpNeighbourPoint, FieldObject inpObject) throws NoSuchPositionException {
        if (inpNeighbourPoint.x < 0 || inpNeighbourPoint.y < 0 || inpNeighbourPoint.x > rowSizeForUser - 1 || inpNeighbourPoint.y > colSizeForUser - 1) {
            throw new NoSuchPositionException("There is no position (" + inpNeighbourPoint.x + "," + inpNeighbourPoint.y + ") on field.");
        } else {
            if (inpNeighbourPoint.x == 0) {
                this.cellAt(inpNeighbourPoint.x, inpNeighbourPoint.y + 1).putObject(inpObject);
            } else if (inpNeighbourPoint.x == rowSizeForUser - 1) {
                this.cellAt(inpNeighbourPoint.x + 2, inpNeighbourPoint.y + 1).putObject(inpObject);
            } else if (inpNeighbourPoint.y == 0) {
                this.cellAt(inpNeighbourPoint.x + 1, inpNeighbourPoint.y).putObject(inpObject);
            } else if (inpNeighbourPoint.y == colSizeForUser - 1) {
                this.cellAt(inpNeighbourPoint.x + 1, inpNeighbourPoint.y + 2).putObject(inpObject);
            }
        }
    }

    public Field(Integer inpRowSize, Integer inpColSize) {
        this.rowSizeForUser = inpRowSize;
        this.colSizeForUser = inpColSize;

        this.rowSizeReal = this.rowSizeForUser + 2;
        this.colSizeReal = this.colSizeForUser + 2;

        for (int i = 0; i < colSizeReal; ++i) {
            for (int j = 0; j < rowSizeReal; ++j) {
                Cell newCell = new Cell(new Point(i, j));
                if (i == 0 || i == colSizeReal - 1 || j == 0 || j == rowSizeReal - 1) {
                    newCell.putObject(new Wall());
                }
                this.cells.add(newCell);
            }
        }
    }

    protected Cell cellAt(Point inpPoint) {
        return cells.get(inpPoint.x * rowSizeReal + inpPoint.y);
    }

    protected Cell cellAt(Integer fst, Integer snd) {
        return this.cellAt(new Point(fst, snd));
    }

    public void doTurn() {                   // Следует делать цикл по объектам, а не по клеткам.
        List<FieldObject> allObjectsOnField = this.getObjects();
        for (FieldObject obj : allObjectsOnField) {
            obj.doTurn();
        }
    }

    private List<FieldObject> getObjects() {
        List<FieldObject> allObjectsOnField = new ArrayList<FieldObject>();
        for (Cell c : this.cells) {
            if (c.getObject() != null) {
                allObjectsOnField.add(c.getObject());
            }
        }
        return allObjectsOnField;
    }


    protected int getSizeOfRow() {
        return rowSizeForUser;
    }

    protected int getSizeOfCol() {
        return colSizeForUser;
    }

    public abstract List<String> getTextRepresentation();

}
