package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 1:17 AM
 */
public class PoliceStation implements FieldObject {
    private Cell myCell;

    public PoliceStation(Cell inpCell){
        this.myCell = inpCell;
    }

    @Override
    public Cell getPosition(){
        return this.myCell;
    }

    @Override
    public void doTurn(){

    }

    @Override
    public char getSymbol(){
        return 'П';
    }
}
