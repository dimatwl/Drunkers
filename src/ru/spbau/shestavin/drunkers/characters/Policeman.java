package ru.spbau.shestavin.drunkers.characters;

import ru.spbau.shestavin.drunkers.buildings.PoliceStation;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;

import java.util.Queue;


public class Policeman extends FieldObject {
    private final Drunker destination;
    private final PoliceStation source;
    private Queue<Cell> path;
    private PolicemanState state = PolicemanState.GOING_FOR_DRUNKER;

    private enum PolicemanState {
        GOING_FOR_DRUNKER, GOING_TO_POLICE_STATION
    }

    public Policeman(Cell inpCell, Drunker inpDestination, PoliceStation inpSource) {
        super(inpCell);
        this.destination = inpDestination;
        this.source = inpSource;
        this.path = findPath(this.getPosition(), this.destination.getPosition());
    }

    @Override
    public void doTurn() {
        if (this.path == null && this.state == PolicemanState.GOING_FOR_DRUNKER) {
            this.path = findPath(this.getPosition(), this.destination.getPosition());
        } else if (this.path == null && this.state == PolicemanState.GOING_TO_POLICE_STATION) {
            this.path = findPath(this.getPosition(), this.source.getPosition());
        } else if (this.path != null) {
            if (!this.path.isEmpty()) {
                Cell cellToMove = this.path.poll();
                if (cellToMove != this.destination.getPosition() && cellToMove.isEmpty()) {
                    this.moveTo(cellToMove);
                } else if (cellToMove == this.source.getPosition() && this.state == PolicemanState.GOING_TO_POLICE_STATION) {
                    this.source.policemanReturned();
                    this.getPosition().removeObject();
                } else if (cellToMove != this.destination.getPosition() && !cellToMove.isEmpty()) {
                    this.path = null;
                } else if (cellToMove == this.destination.getPosition() && this.state == PolicemanState.GOING_FOR_DRUNKER) {
                    this.state = PolicemanState.GOING_TO_POLICE_STATION;
                    this.path = null;
                    this.moveTo(cellToMove);
                }
            } else {
                //Nothing to do. Just stay
                this.path = null;
            }
        }
    }


    @Override
    public char getSymbolRepresentation() {
        return '!';
    }
}
