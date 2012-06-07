package ru.spbau.shestavin.drunkers.buildings;


import ru.spbau.shestavin.drunkers.characters.Beggar;
import ru.spbau.shestavin.drunkers.characters.Bottle;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;

public class PointForGlass extends FieldObject {
    private boolean beggarOnDuty = false;
    private int numberOfTurnsBeggarNotOnDuty = 0;
    private final static int numberOfTurnsBeggarRest = 40;

    public PointForGlass() {
        super();
    }

    @Override
    public void doTurn() {
        if (!this.beggarOnDuty && this.timeToDropBeggar()) {
            Cell freeCell = findFreeCell();
            if (freeCell != null) {
                freeCell.putObject(new Beggar(freeCell, this));
                this.beggarOnDuty = true;
                this.numberOfTurnsBeggarNotOnDuty = 0;
            }
        } else if (!this.beggarOnDuty) {
            ++this.numberOfTurnsBeggarNotOnDuty;
        }
        this.removeBottleNearDoor();
    }

    public void beggarReturned() {
        this.beggarOnDuty = false;
    }


    private Cell findFreeCell() {
        Cell freeCell = null;
        for (Cell candidate : this.getPosition().getNeighbours()) {
            if (candidate.isEmpty()) {
                freeCell = candidate;
                break;
            }
        }
        return freeCell;
    }

    private void removeBottleNearDoor() {
        for (Cell candidate : this.getPosition().getNeighbours()) {
            if (candidate.getObject() instanceof Bottle) {
                candidate.removeObject();
                break;
            }
        }
    }

    private boolean timeToDropBeggar() {
        return this.numberOfTurnsBeggarNotOnDuty >= numberOfTurnsBeggarRest;
    }

    @Override
    public char getSymbolRepresentation() {
        return 'B';
    }
}
