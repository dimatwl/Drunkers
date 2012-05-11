package ru.spbau.shestavin.drunkers;

import java.util.Random;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/21/12
 * Time: 4:42 PM
 */
public class Drunker extends FieldObject {
    private DrunkerState myState = DrunkerState.WITH_BOTTLE_ACTIVE;
    private static final Random myRandom = new Random();

    private enum DrunkerState {
        WITH_BOTTLE_ACTIVE, NO_BOTTLE_ACTIVE, STANDING_SLEEPING, LAYING_SLEEPING
    }

    public Drunker(Cell inpCell) {
        super(inpCell);
    }


    boolean isStandingSleeping() {
        return this.myState == DrunkerState.STANDING_SLEEPING;
    }

    public boolean isIllegal() {
        return this.myState == DrunkerState.STANDING_SLEEPING || this.myState == DrunkerState.LAYING_SLEEPING;
    }

    @Override
    public void doTurn() {
        if (this.myState == DrunkerState.WITH_BOTTLE_ACTIVE || this.myState == DrunkerState.NO_BOTTLE_ACTIVE) {
            Cell cellToMove = this.getPosition().getNeighbours().get(myRandom.nextInt(this.getPosition().getNeighbours().size()));
            if (cellToMove.isEmpty()) {
                this.moveTo(cellToMove);
            } else if (cellToMove.getObject() instanceof Pillar) {
                this.myState = DrunkerState.STANDING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Bottle) {
                this.moveTo(cellToMove);
                this.myState = DrunkerState.LAYING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Drunker && ((Drunker) cellToMove.getObject()).isStandingSleeping()) {
                this.myState = DrunkerState.STANDING_SLEEPING;
            } else if (cellToMove.getObject() instanceof Drunker) {
            } else {
                boolean isThereEmptyCell = false;
                for (Cell neighbour : this.getPosition().getNeighbours()){
                    if (neighbour.isEmpty()){
                        isThereEmptyCell = true;
                    }
                }
                if (isThereEmptyCell){
                    this.doTurn();
                }
            }
        }
    }

    @Override
    protected void moveTo(Cell inpCell) {
        Cell prevCell = this.getPosition();
        super.moveTo(inpCell);
        if (this.myState == DrunkerState.WITH_BOTTLE_ACTIVE && this.timeToDropBottle()) {
            prevCell.putObject(new Bottle(prevCell));
            this.myState = DrunkerState.NO_BOTTLE_ACTIVE;
        }
    }

    private boolean timeToDropBottle() {
        return myRandom.nextInt(30) == myRandom.nextInt(30);
    }

    @Override
    public char getSymbolRepresentation() {
        char symbolRepresentation = ' ';
        switch (this.myState) {
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
