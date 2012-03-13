package ru.spbau.shestavin.drunkers;


/**
 * Classname:
 * User: dimatwl
 * Date: 3/10/12
 * Time: 11:13 PM
 */
public class Cell {
    private int x;
    private int y;
    private FieldObject myObject;
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }

    public FieldObject getObject(){
        return this.myObject;
    }

    public void removeObject(){
        this.myObject = null;
    }

    public void putObject(FieldObject inpObject){
        this.myObject = inpObject;
    }

    public boolean isEmpty(){
        return this.myObject == null;
    }

    public char getSymbol(){
        if (this.myObject != null){
            return this.myObject.getSymbol();
        }else{
            return '0';
        }
    }
}
