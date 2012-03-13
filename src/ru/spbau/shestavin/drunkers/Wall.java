package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/14/12
 * Time: 12:30 AM
 */
public class Wall implements FieldObject{
    private Cell myCell;

    public Wall(Cell inpCell){
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
        return ' ';
    }
}
