package ru.spbau.shestavin.drunkers;

import java.util.HashSet;
import java.util.Set;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/30/12
 * Time: 6:42 PM
 */
public class PointForGlass extends FieldObject {
    private final static Set<Drunker> illegalDrunkers = new HashSet<Drunker>();
    private boolean beggarOnDuty = false;
    private int numberOfTurnsBeggarNotOnDuty = 0;
    private static int numberOfTurnsBeggarRest = 40;

    public PointForGlass(Cell inpCell) {
        super(inpCell);
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
        } else if (!this.beggarOnDuty){
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
