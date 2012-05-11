package ru.spbau.shestavin.drunkers;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/10/12
 * Time: 11:09 PM
 */
public abstract class Field {
    private static final Pair<Integer, Integer> fieldDimensions = new Pair<Integer, Integer>(15 + 2, 15 + 2);
    private static final Pair<Integer, Integer> barPosition = new Pair<Integer, Integer>(0, 9 + 2);
    private static final Pair<Integer, Integer> policeStationPosition = new Pair<Integer, Integer>(3 + 1, 14 + 2);
    private static final Pair<Integer, Integer> pillarPosition = new Pair<Integer, Integer>(7 + 1, 7 + 1);
    private static final Pair<Integer, Integer> lampPosition = new Pair<Integer, Integer>(3 + 1, 10 + 1);
    private static final Pair<Integer, Integer> pointForGlassPosition = new Pair<Integer, Integer>(14 + 2, 4 + 1);
    private static final Integer illuminationRadius = 3;
    private static final Pair<Integer, Integer> PintForGlassPosition = new Pair<Integer, Integer>(14 + 2, 4 + 1);
    private final List<Cell> myCells = new ArrayList<Cell>(this.getSizeOfRow() * this.getSizeOfCol());


    Field() {
        for (int i = 0; i < this.getSizeOfCol(); ++i) {
            for (int j = 0; j < this.getSizeOfRow(); ++j) {
                Cell newCell = new Cell(new Pair<Integer, Integer>(i, j));
                newCell.setIllumination(isCellIlluminated(new Pair<Integer, Integer>(i, j)));
                this.myCells.add(newCell);
                if (!(new Pair<Integer, Integer>(i, j).equals(barPosition)) &&
                        !(new Pair<Integer, Integer>(i, j).equals(policeStationPosition)) &&
                        !(new Pair<Integer, Integer>(i, j).equals(pillarPosition)) &&
                        !(new Pair<Integer, Integer>(i, j).equals(lampPosition)) &&
                        !(new Pair<Integer, Integer>(i, j).equals(pointForGlassPosition))) {
                    if ((i == 0 || i == 16) || ((j == 0 || j == 16))) {
                        this.cellAt(new Pair<Integer, Integer>(i, j)).putObject(new Wall(this.cellAt(new Pair<Integer, Integer>(i, j))));
                    }
                }
            }
        }
        this.cellAt(barPosition).putObject(new Bar(this.cellAt(barPosition)));
        this.cellAt(policeStationPosition).putObject(new PoliceStation(this.cellAt(policeStationPosition)));
        this.cellAt(pillarPosition).putObject(new Pillar(this.cellAt(pillarPosition)));
        this.cellAt(lampPosition).putObject(new Lamp(this.cellAt(lampPosition)));
        this.cellAt(pointForGlassPosition).putObject(new PointForGlass(this.cellAt(pointForGlassPosition)));
    }

    private boolean isCellIlluminated(Pair<Integer, Integer> inpCellPosition) {
        return (-illuminationRadius <= (inpCellPosition.fst - lampPosition.fst)) && (illuminationRadius >= (inpCellPosition.fst - lampPosition.fst)) &&
                (-illuminationRadius <= (inpCellPosition.snd - lampPosition.snd)) && (illuminationRadius >= (inpCellPosition.snd - lampPosition.snd));
    }

    Cell cellAt(Pair<Integer, Integer> inpPosition) {
        return myCells.get(inpPosition.fst * this.getSizeOfRow() + inpPosition.snd);
    }

    public void doTurn() {                   // Следует делать цикл по объектам, а не по клеткам.
        List<FieldObject> allObjectsOnField = this.getObjects();
        for (FieldObject obj : allObjectsOnField) {
            obj.doTurn();
        }
    }

    private List<FieldObject> getObjects() {
        List<FieldObject> allObjectsOnField = new ArrayList<FieldObject>();
        for (Cell c : this.myCells) {
            if (c.getObject() != null) {
                allObjectsOnField.add(c.getObject());
            }
        }
        return allObjectsOnField;
    }


    int getSizeOfRow() {
        return fieldDimensions.fst;
    }

    int getSizeOfCol() {
        return fieldDimensions.snd;
    }

    public abstract List<String> getTextRepresentation();

}
