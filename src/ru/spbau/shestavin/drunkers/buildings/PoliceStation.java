package ru.spbau.shestavin.drunkers.buildings;

import ru.spbau.shestavin.drunkers.characters.Drunker;
import ru.spbau.shestavin.drunkers.characters.Policeman;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;

import java.util.HashSet;
import java.util.Set;


public class PoliceStation extends FieldObject {
    private final Set<Drunker> illegalDrunkers = new HashSet<Drunker>();
    private boolean policemanOnDuty = false;

    public PoliceStation() {
        super();
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
            double currentDistance = this.getPosition().distTo(currentDrunker.getPosition());
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
                if (!visitedCells.contains(neighbor) && (neighbor.isEmpty() || neighbor.getObject() instanceof Drunker)) {
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
