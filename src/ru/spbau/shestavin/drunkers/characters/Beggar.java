package ru.spbau.shestavin.drunkers.characters;

import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;
import ru.spbau.shestavin.drunkers.buildings.PointForGlass;

import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


public class Beggar extends FieldObject {
    private Bottle destination = null;
    private final PointForGlass source;
    private Queue<Cell> path;
    private BeggarState state = BeggarState.HANGING_AROUND;
    private static final Random random = new Random();
    private final Set<Bottle> foundedBottles = new HashSet<Bottle>();

    private enum BeggarState {
        HANGING_AROUND, GOING_FOR_BOTTLE, GOING_TO_POINT_FOR_GLASS
    }

    public Beggar(Cell inpCell, PointForGlass inpSource) {
        super(inpCell);
        this.source = inpSource;
    }

    @Override
    public void doTurn() {
        if (this.state == BeggarState.HANGING_AROUND) {
            Bottle closestBottle = this.getClosestBottle();
            if (this.timeToGetSomeBottles() && closestBottle != null) {
                this.destination = closestBottle;
                this.state = BeggarState.GOING_FOR_BOTTLE;
                this.doTurn();
            } else {
                Cell cellToMove = this.getPosition().getNeighbours().get(random.nextInt(this.getPosition().getNeighbours().size()));
                if (cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
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
                        //Nothing to do. Just stay )
                    }
                }
            }
        } else if (this.path == null && this.state == BeggarState.GOING_FOR_BOTTLE) {
            if (this.destination.isOnField()) {
                this.path = findPath(this.getPosition(), this.destination.getPosition());
            } else {
                this.state = BeggarState.HANGING_AROUND;
                this.path = null;
                this.destination = null;
                this.doTurn();
            }
        } else if (this.path == null && this.state == BeggarState.GOING_TO_POINT_FOR_GLASS) {
            this.path = findPath(this.getPosition(), this.source.getPosition());
        } else if (this.path != null) {
            if (this.state == BeggarState.GOING_FOR_BOTTLE && this.destination != null && !this.destination.isOnField()) {
                this.state = BeggarState.HANGING_AROUND;
                this.path = null;
                this.destination = null;
            } else if (!this.path.isEmpty()) {
                Cell cellToMove = this.path.poll();
                if (cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
                } else if (cellToMove == this.source.getPosition() && this.state == BeggarState.GOING_TO_POINT_FOR_GLASS) {
                    this.source.beggarReturned();
                    this.getPosition().removeObject();
                } else if (this.destination != null && cellToMove == this.destination.getPosition() && this.state == BeggarState.GOING_FOR_BOTTLE) {
                    this.state = BeggarState.GOING_TO_POINT_FOR_GLASS;
                    this.path = null;
                    this.moveTo(cellToMove);
                    this.destination = null;
                } else {
                    this.path = null;
                }
            } else {
                this.state = BeggarState.HANGING_AROUND;
                this.path = null;
                this.destination = null;
            }
        } else {
            this.state = BeggarState.HANGING_AROUND;
            this.path = null;
            this.destination = null;
        }
    }


    @Override
    public char getSymbolRepresentation() {
        return 'Z';
    }

    private Bottle getClosestBottle() {
        Bottle closestBottle = null;
        double minDistance = Double.MAX_VALUE;
        for (Bottle currentBottle : this.foundedBottles) {
            double currentDistance = this.getPosition().distTo(currentBottle.getPosition());
            if (currentDistance < minDistance && findPath(this.getPosition(), currentBottle.getPosition()) != null) {
                closestBottle = currentBottle;
                minDistance = currentDistance;
            }
        }
        return closestBottle;
    }

    private boolean timeToGetSomeBottles() {
        this.foundedBottles.clear();
        this.bottlesSearch(this.getPosition(), new HashSet<Cell>());
        return !this.foundedBottles.isEmpty();
    }

    private void bottlesSearch(Cell inpStartPosition, Set<Cell> visitedCells) {
        visitedCells.add(inpStartPosition);
        if (inpStartPosition.getObject() instanceof Bottle) {
            this.foundedBottles.add((Bottle) inpStartPosition.getObject());
        } else {
            for (Cell neighbor : inpStartPosition.getNeighbours()) {
                if (!visitedCells.contains(neighbor)) {
                    this.bottlesSearch(neighbor, visitedCells);
                }
            }
        }
    }
}

