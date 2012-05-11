package ru.spbau.shestavin.drunkers;

import java.util.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/30/12
 * Time: 6:33 PM
 */


public class Beggar extends FieldObject {
    private Bottle myDestination = null;
    private final PointForGlass mySource;
    private Queue<Cell> myPath;
    private BeggarState myState = BeggarState.HANGING_AROUND;
    private static final Random myRandom = new Random();
    private final Set<Bottle> foundedBottles = new HashSet<Bottle>();

    private enum BeggarState {
        HANGING_AROUND ,GOING_FOR_BOTTLE, GOING_TO_POINT_FOR_GLASS
    }

    public Beggar(Cell inpCell, PointForGlass inpSource){
        super(inpCell);
        this.mySource = inpSource;
    }

    @Override
    public void doTurn() {
        if (this.myState == BeggarState.HANGING_AROUND) {
            if (this.timeToGetSomeBottles() && this.getClosestBottle() != null){
                this.myDestination = this.getClosestBottle();
                this.myState = BeggarState.GOING_FOR_BOTTLE;
                this.doTurn();
            } else {
                Cell cellToMove = this.getPosition().getNeighbours().get(myRandom.nextInt(this.getPosition().getNeighbours().size()));
                if (cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
                } else {
                    boolean isThereEmptyCell = false;
                    for (Cell neighbour : this.getPosition().getNeighbours()){
                        if (neighbour.isEmpty()){
                            isThereEmptyCell = true;
                        }
                    }
                    if (isThereEmptyCell){
                        this.doTurn();
                    } else {
                        //Nothing to do. Just stay )
                    }
                }
            }
        } else if (this.myPath == null && this.myState == BeggarState.GOING_FOR_BOTTLE) {
            if (this.myDestination.isOnField()){
                this.myPath = findPath(this.getPosition(), this.myDestination.getPosition());
            } else {
                this.myState = BeggarState.HANGING_AROUND;
                this.myPath = null;
                this.myDestination = null;
                this.doTurn();
            }
        } else if (this.myPath == null && this.myState == BeggarState.GOING_TO_POINT_FOR_GLASS) {
            this.myPath = findPath(this.getPosition(), this.mySource.getPosition());
        } else if (this.myPath != null ) {
            if (this.myState == BeggarState.GOING_FOR_BOTTLE && this.myDestination != null && !this.myDestination.isOnField()){
                this.myState = BeggarState.HANGING_AROUND;
                this.myPath = null;
                this.myDestination = null;
            } else if (!this.myPath.isEmpty()) {
                Cell cellToMove = this.myPath.poll();
                if (cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
                } else if (cellToMove == this.mySource.getPosition() && this.myState == BeggarState.GOING_TO_POINT_FOR_GLASS) {
                    this.mySource.beggarReturned();
                    this.getPosition().removeObject();
                } else if (this.myDestination != null && cellToMove == this.myDestination.getPosition() && this.myState == BeggarState.GOING_FOR_BOTTLE) {
                    myDestination.getPosition().removeObject();
                    this.myState = BeggarState.GOING_TO_POINT_FOR_GLASS;
                    this.myPath = null;
                    this.moveTo(cellToMove);
                    this.myDestination = null;
                } else {
                    this.myPath = null;
                }
            } else {
                this.myState = BeggarState.HANGING_AROUND;
                this.myPath = null;
                this.myDestination = null;
            }
        } else {
            this.myState = BeggarState.HANGING_AROUND;
            this.myPath = null;
            this.myDestination = null;
        }
    }


    @Override
    public char getSymbolRepresentation() {
        return 'Z';
    }

    private Bottle getClosestBottle(){
        Bottle closestBottle = null;
        double minDistance = Double.MAX_VALUE;
        for (Bottle currentBottle : this.foundedBottles) {
            double currentDistance = Math.sqrt(Math.pow(this.getPosition().getCoordinates().fst - currentBottle.getPosition().getCoordinates().fst, 2) + Math.pow(this.getPosition().getCoordinates().snd - currentBottle.getPosition().getCoordinates().snd, 2));
            if (currentDistance < minDistance && this.findPath(this.getPosition(), currentBottle.getPosition()) != null) {
                closestBottle = currentBottle;
                minDistance = currentDistance;
            }
        }
        return closestBottle;
    }

    private boolean timeToGetSomeBottles(){
        this.foundedBottles.clear();
        this.bottlesSearch(this.getPosition(), new HashSet<Cell>());
        return !this.foundedBottles.isEmpty();
    }

    private Queue<Cell> findPath(Cell inpSource, Cell inpDestination){
        Queue<Cell> q = new LinkedList<Cell>();
        Set<Cell> used = new HashSet<Cell>();
        //Map<Cell, Integer> dist = new HashMap<Cell, Integer>();
        Map<Cell, Cell> prev = new HashMap<Cell, Cell>();
        q.offer(inpSource);
        used.add(inpSource);
        //dist.put(inpSource, 0);
        prev.put(inpSource, null);
        while (!q.isEmpty()){
            Cell v = q.poll();
            for (Cell neighbor : v.getNeighbours()){
                if (!used.contains(neighbor) && (neighbor.isEmpty() || neighbor == inpDestination)){
                    used.add(neighbor);
                    q.offer(neighbor);
                    //dist.put(neighbor, dist.get(v) + 1);
                    prev.put(neighbor, v);
                }
            }
        }
        if (!used.contains(inpDestination)){
            return null;
        } else {
            LinkedList<Cell> path = new LinkedList<Cell>();
            for (Cell v = inpDestination; prev.get(v) != null; v = prev.get(v)){
                path.add(v);
            }
            Collections.reverse(path);
            return path;
        }
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

