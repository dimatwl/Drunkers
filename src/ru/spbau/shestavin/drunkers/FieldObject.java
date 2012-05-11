package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/10/12
 * Time: 11:09 PM
 */
public abstract class FieldObject {
    private Cell myCell;

    public FieldObject(Cell inpCell){
        myCell = inpCell;
    }

    public Cell getPosition(){
        return this.myCell;
    }

    abstract public void doTurn();

    abstract public char getSymbolRepresentation();

    public boolean isOnField(){
        return this.myCell != null;
    }

    public void removeFromField(){
        this.myCell = null;
    }

    protected void moveTo(Cell inpCell) {
        if (this.myCell != null && inpCell != null){
            this.myCell.removeObject();
            inpCell.removeObject();
            inpCell.putObject(this);
            this.myCell = inpCell;
        }
    }
}
