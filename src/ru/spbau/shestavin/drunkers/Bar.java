package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 1:15 AM
 */
public class Bar extends FieldObject {
    private int turnNumber = 0;
    private int numberOfWaitingDrunkers = 0;
    private static final int turnsBetweenDrunkers = 20;

    public Bar(Cell inpCell) {
        super(inpCell);
    }

    @Override
    public void doTurn() {
        if (this.timeToDropDrunker()) {
            Cell freeCell = findFreeCell();
            if (freeCell != null) {
                freeCell.putObject(new Drunker(freeCell));
            } else {
                ++this.numberOfWaitingDrunkers;
            }
        } else {
            while (this.numberOfWaitingDrunkers > 0) {
                Cell freeCell = findFreeCell();
                if (freeCell != null) {
                    freeCell.putObject(new Drunker(freeCell));
                    --this.numberOfWaitingDrunkers;
                } else {
                    break;
                }
            }
        }
        ++this.turnNumber;
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

    private boolean timeToDropDrunker() {
        return this.turnNumber % turnsBetweenDrunkers == 0;
    }

    @Override
    public char getSymbolRepresentation() {
        return 'T';
    }
}
