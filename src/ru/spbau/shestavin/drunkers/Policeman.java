package ru.spbau.shestavin.drunkers;

import java.util.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/28/12
 * Time: 1:18 PM
 */
public class Policeman extends FieldObject {
    private final Drunker myDestination;
    private final PoliceStation mySource;
    private Queue<Cell> myPath;
    private PolicemanState myState = PolicemanState.GOING_FOR_DRUNKER;

    private enum PolicemanState {
        GOING_FOR_DRUNKER, GOING_TO_POLICE_STATION
    }

    public Policeman(Cell inpCell, Drunker inpDestination, PoliceStation inpSource) {
        super(inpCell);
        this.myDestination = inpDestination;
        this.mySource = inpSource;
        this.myPath = findPath(this.getPosition(), this.myDestination.getPosition());
    }

    @Override
    public void doTurn() {
        if (this.myPath == null && this.myState == PolicemanState.GOING_FOR_DRUNKER) {
            this.myPath = findPath(this.getPosition(), this.myDestination.getPosition());
        } else if (this.myPath == null && this.myState == PolicemanState.GOING_TO_POLICE_STATION) {
            this.myPath = findPath(this.getPosition(), this.mySource.getPosition());
        } else if (this.myPath != null) {
            if (!this.myPath.isEmpty()) {
                Cell cellToMove = this.myPath.poll();
                if (cellToMove != this.myDestination.getPosition() && cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
                } else if (cellToMove == this.mySource.getPosition() && this.myState == PolicemanState.GOING_TO_POLICE_STATION) {
                    this.mySource.policemanReturned();
                    this.getPosition().removeObject();
                } else if (cellToMove != this.myDestination.getPosition() && !cellToMove.isEmpty()) {
                    this.myPath = null;
                } else if (cellToMove == this.myDestination.getPosition() && this.myState == PolicemanState.GOING_FOR_DRUNKER) {
                    myDestination.getPosition().removeObject();
                    this.myState = PolicemanState.GOING_TO_POLICE_STATION;
                    this.myPath = null;
                    this.moveTo(cellToMove);
                }
            } else {
                //Nothing to do. Just stay
                this.myPath = null;
            }
        }
    }


    @Override
    public char getSymbolRepresentation() {
        return '!';
    }

    private Queue<Cell> findPath(Cell inpSource, Cell inpDestination) {
        Queue<Cell> q = new LinkedList<Cell>();
        Set<Cell> used = new HashSet<Cell>();
        //Map<Cell, Integer> dist = new HashMap<Cell, Integer>();
        Map<Cell, Cell> prev = new HashMap<Cell, Cell>();
        q.offer(inpSource);
        used.add(inpSource);
        //dist.put(inpSource, 0);
        prev.put(inpSource, null);
        while (!q.isEmpty()) {
            Cell v = q.poll();
            for (Cell neighbor : v.getNeighbours()) {
                if (!used.contains(neighbor) && (neighbor.isEmpty() || neighbor == inpDestination)) {
                    used.add(neighbor);
                    q.offer(neighbor);
                    //dist.put(neighbor, dist.get(v) + 1);
                    prev.put(neighbor, v);
                }
            }
        }
        if (!used.contains(inpDestination)) {
            return null;
        } else {
            LinkedList<Cell> path = new LinkedList<Cell>();
            for (Cell v = inpDestination; prev.get(v) != null; v = prev.get(v)) {
                path.add(v);
            }
            Collections.reverse(path);
            return path;
        }
    }
}
