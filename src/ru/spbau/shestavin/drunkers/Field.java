package ru.spbau.shestavin.drunkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Classname:
 * User: dimatwl
 * Date: 3/10/12
 * Time: 11:09 PM
 */
public class Field {
    private List<Cell> myCells = new ArrayList<Cell>(15 * 15);
    //private List<FieldObject> myObjects = new ArrayList<FieldObject>();

    public Field(){
        //Создать объекты и поместить в соответствующие клетки
    }

    public void doTurn(){
        for (Cell c : this.myCells){
            if (c.getObject() != null){
                c.getObject().doTurn();
            }
        }
    }

}
