package ru.spbau.shestavin.drunkers;

import java.util.HashSet;
import java.util.Set;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 1:17 AM
 */
public class PoliceStation extends FieldObject {
    private final Set<Drunker> illegalDrunkers = new HashSet<Drunker>();
    private boolean policemanOnDuty = false;

    public PoliceStation(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {
        if (!this.policemanOnDuty && this.timeToDropPoliceman()) {
            Cell freeCell = findFreeCell();
            if (freeCell != null) {
                Drunker drunkerToCarry = getClosestIllegalDrunker();
                freeCell.putObject(new Policeman(freeCell, drunkerToCarry, this));
                this.policemanOnDuty = true;
            }
        }
        this.removeIllegalDrunkerNearDoor();
    }

    public void policemanReturned() {
        this.policemanOnDuty = false;
    }

    Drunker getClosestIllegalDrunker() {
        Drunker closestDrunker = null;
        double minDistance = Double.MAX_VALUE;
        for (Drunker currentDrunker : this.illegalDrunkers) {
            double currentDistance = Math.sqrt(Math.pow(this.getPosition().getCoordinates().fst - currentDrunker.getPosition().getCoordinates().fst, 2) + Math.pow(this.getPosition().getCoordinates().snd - currentDrunker.getPosition().getCoordinates().snd, 2));
            if (currentDistance < minDistance) {
                closestDrunker = currentDrunker;
                minDistance = currentDistance;
            }
        }
        return closestDrunker;
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

    private void removeIllegalDrunkerNearDoor() {
        for (Cell candidate : this.getPosition().getNeighbours()) {
            if (candidate.getObject() instanceof Drunker && ((Drunker) candidate.getObject()).isIllegal()) {
                candidate.removeObject();
                break;
            }
        }
    }

    private boolean timeToDropPoliceman() {
        this.illegalDrunkers.clear();
        this.illegalDrunkerSearch(this.getPosition(), new HashSet<Cell>());
        return !this.illegalDrunkers.isEmpty();
    }

    private void illegalDrunkerSearch(Cell inpStartPosition, Set<Cell> visitedCells) {
        visitedCells.add(inpStartPosition);
        if (inpStartPosition.getObject() instanceof Drunker && ((Drunker) inpStartPosition.getObject()).isIllegal() && inpStartPosition.isIlluminated()) {
            this.illegalDrunkers.add((Drunker) inpStartPosition.getObject());
        } else {
            for (Cell neighbor : inpStartPosition.getNeighbours()) {
                if (!visitedCells.contains(neighbor)) {
                    this.illegalDrunkerSearch(neighbor, visitedCells);
                }
            }
        }
    }

    @Override
    public char getSymbolRepresentation() {
        return 'П';
    }
}
