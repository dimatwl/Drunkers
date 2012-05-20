package ru.spbau.shestavin.drunkers.abstraction;

import com.sun.tools.javac.util.Pair;
import ru.spbau.shestavin.drunkers.buildings.*;

import java.util.ArrayList;
import java.util.List;


public abstract class Field {
    private static final Pair<Integer, Integer> fieldDimensions = new Pair<Integer, Integer>(15, 15);
    private static final Pair<Integer, Integer> barPosition = new Pair<Integer, Integer>(0, 9);
    private static final Pair<Integer, Integer> policeStationPosition = new Pair<Integer, Integer>(3, 14);
    private static final Pair<Integer, Integer> pillarPosition = new Pair<Integer, Integer>(7, 7);
    private static final Pair<Integer, Integer> lampPosition = new Pair<Integer, Integer>(3, 10);
    private static final Pair<Integer, Integer> pointForGlassPosition = new Pair<Integer, Integer>(14, 4);
    private static final Integer illuminationRadius = 3;
    private final List<Cell> cells = new ArrayList<Cell>(this.getSizeOfRow() * this.getSizeOfCol());


    public void putObjectOnField(Pair<Integer, Integer> inpPosition, FieldObject inpObject) throws NoSuchPositionException {
        if (inpPosition.fst < 0 || inpPosition.snd < 0 || inpPosition.fst > 14 || inpPosition.snd > 14) {
            throw new NoSuchPositionException("There is no position (" + inpPosition.fst + "," + inpPosition.snd + ") on field.");
        } else {
            this.cellAt(new Pair<Integer, Integer>(inpPosition.fst + 1, inpPosition.snd + 1)).putObject(inpObject);
        }
    }

    public void putObjectOutsideField(Pair<Integer, Integer> inpNeighbourPosition, FieldObject inpObject) throws NoSuchPositionException {
        if (inpNeighbourPosition.fst < 0 || inpNeighbourPosition.snd < 0 || inpNeighbourPosition.fst > 14 || inpNeighbourPosition.snd > 14) {
            throw new NoSuchPositionException("There is no position (" + inpNeighbourPosition.fst + "," + inpNeighbourPosition.snd + ") on field.");
        } else {
            if (inpNeighbourPosition.fst == 0) {
                this.cellAt(new Pair<Integer, Integer>(inpNeighbourPosition.fst, inpNeighbourPosition.snd + 1)).putObject(inpObject);
            } else if (inpNeighbourPosition.fst == 14) {
                this.cellAt(new Pair<Integer, Integer>(inpNeighbourPosition.fst + 2, inpNeighbourPosition.snd + 1)).putObject(inpObject);
            } else if (inpNeighbourPosition.snd == 0) {
                this.cellAt(new Pair<Integer, Integer>(inpNeighbourPosition.fst + 1, inpNeighbourPosition.snd)).putObject(inpObject);
            } else if (inpNeighbourPosition.snd == 14) {
                this.cellAt(new Pair<Integer, Integer>(inpNeighbourPosition.fst + 1, inpNeighbourPosition.snd + 2)).putObject(inpObject);
            }
        }
    }

    public Field() {
        for (int i = 0; i < this.getSizeOfCol() + 2; ++i) {
            for (int j = 0; j < this.getSizeOfRow() + 2; ++j) {
                Cell newCell = new Cell(new Pair<Integer, Integer>(i, j));
                newCell.setIllumination(isCellIlluminated(new Pair<Integer, Integer>(i, j)));
                this.cells.add(newCell);
                if (i == 0 || i == this.getSizeOfCol() + 1 || j == 0 || j == this.getSizeOfRow() + 1) {
                    this.cellAt(new Pair<Integer, Integer>(i, j)).putObject(new Wall());
                }
            }
        }
        try {
            this.putObjectOutsideField(barPosition, new Bar());
            this.putObjectOutsideField(policeStationPosition, new PoliceStation());
            this.putObjectOnField(pillarPosition, new Pillar());
            this.putObjectOnField(lampPosition, new Lamp());
            this.putObjectOutsideField(pointForGlassPosition, new PointForGlass());
        } catch (NoSuchPositionException e) {
            System.err.println("That's can't be!");
        }
    }


    private boolean isCellIlluminated(Pair<Integer, Integer> inpCellPosition) {
        return (-illuminationRadius <= (inpCellPosition.fst - lampPosition.fst)) && (illuminationRadius >= (inpCellPosition.fst - lampPosition.fst)) &&
                (-illuminationRadius <= (inpCellPosition.snd - lampPosition.snd)) && (illuminationRadius >= (inpCellPosition.snd - lampPosition.snd));
    }

    protected Cell cellAt(Pair<Integer, Integer> inpPosition) {
        return cells.get(inpPosition.fst * (this.getSizeOfRow() + 2) + inpPosition.snd);
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
        return fieldDimensions.fst;
    }

    protected int getSizeOfCol() {
        return fieldDimensions.snd;
    }

    public abstract List<String> getTextRepresentation();

}
