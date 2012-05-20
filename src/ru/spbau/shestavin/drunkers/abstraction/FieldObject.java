package ru.spbau.shestavin.drunkers.abstraction;


import java.util.*;


public abstract class FieldObject {
    private Cell cell;

    protected FieldObject(Cell inpCell) {
        this.cell = inpCell;
    }

    public FieldObject() {
        this.cell = null;
    }

    public Cell getPosition() {
        return this.cell;
    }

    abstract public void doTurn();

    abstract public char getSymbolRepresentation();

    public boolean isOnField() {
        return this.cell != null;
    }

    void putOnField(Cell inpCell) {
        this.cell = inpCell;
    }

    public void removeFromField() {
        this.cell = null;
    }


    protected void moveTo(Cell inpCell) {
        if (this.cell != null && inpCell != null) {
            this.cell.removeObject();
            inpCell.removeObject();
            inpCell.putObject(this);
            this.cell = inpCell;
        }
    }

    protected static Queue<Cell> findPath(Cell inpSource, Cell inpDestination) {
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
                    //dist.put(neighbor, dist.getLine(v) + 1);
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
