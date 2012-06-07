package ru.spbau.shestavin.drunkers.characters;

import ru.spbau.shestavin.drunkers.buildings.Pillar;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;

import java.util.List;
import java.util.Random;


public class Drunker extends FieldObject {
    private DrunkerState state = DrunkerState.WITH_BOTTLE_ACTIVE;
    private static final Random random = new Random();

    private enum DrunkerState {
        WITH_BOTTLE_ACTIVE, NO_BOTTLE_ACTIVE, STANDING_SLEEPING, LAYING_SLEEPING
    }

    public Drunker(Cell inpCell) {
        super(inpCell);
    }


    public boolean isStandingSleeping() {
        return this.state == DrunkerState.STANDING_SLEEPING;
    }

    public boolean isIllegal() {
        return this.state == DrunkerState.STANDING_SLEEPING || this.state == DrunkerState.LAYING_SLEEPING;
    }

    @Override
    public void doTurn() {
        if (this.state == DrunkerState.WITH_BOTTLE_ACTIVE || this.state == DrunkerState.NO_BOTTLE_ACTIVE) {
            List<Cell> neighbours = this.getPosition().getNeighbours();
            Cell cellToMove = neighbours.get(random.nextInt(neighbours.size()));
            if (cellToMove.isEmpty()) {
                this.moveTo(cellToMove);
            } else if (cellToMove.getObject() instanceof Pillar) {
                this.state = DrunkerState.STANDING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Bottle) {
                this.moveTo(cellToMove);
                this.state = DrunkerState.LAYING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Drunker && ((Drunker) cellToMove.getObject()).isStandingSleeping()) {
                this.state = DrunkerState.STANDING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Drunker) {
            } else {
                boolean isThereEmptyCell = false;
                for (Cell neighbour : this.getPosition().getNeighbours()) {
                    if (neighbour.isEmpty()) {
                        isThereEmptyCell = true;
                    }
                }
                if (isThereEmptyCell) {
                    this.doTurn();
                } else {
                    //Just stay.
                }
            }
        }
    }

    @Override
    protected void moveTo(Cell inpCell) {
        Cell prevCell = this.getPosition();
        super.moveTo(inpCell);
        if (this.state == DrunkerState.WITH_BOTTLE_ACTIVE && this.timeToDropBottle()) {
            prevCell.putObject(new Bottle(prevCell));
            this.state = DrunkerState.NO_BOTTLE_ACTIVE;
        }
    }

    private boolean timeToDropBottle() {
        return random.nextInt(30) == random.nextInt(30);
    }

    @Override
    public char getSymbolRepresentation() {
        char symbolRepresentation = 'E';
        switch (this.state) {
            case WITH_BOTTLE_ACTIVE:
            case NO_BOTTLE_ACTIVE:
                symbolRepresentation = '@';
                break;
            case STANDING_SLEEPING:
                symbolRepresentation = '1';
                break;
            case LAYING_SLEEPING:
                symbolRepresentation = '&';
                break;
        }
        return symbolRepresentation;
    }
}
