package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 1:15 AM
 */
public class Bar implements FieldObject {
    private Cell myCell;

    public Bar(Cell inpCell){
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
        return 'T';
    }
}
