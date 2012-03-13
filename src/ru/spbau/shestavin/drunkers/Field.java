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
    private int sizeX = 15 + 2;
    private int sizeY = 15 + 2;
    private List<Cell> myCells = new ArrayList<Cell>(sizeX * sizeY);

    private static Field ourInstance = new Field();

    private Field() {

    }

    public static Field getInstance() {
        return ourInstance;
    }

    public void doTurn(){
        for (Cell c : this.myCells){
            if (c.getObject() != null){
                c.getObject().doTurn();
            }
        }
    }

    public List<String> GetTextRepresentation(){
        List<String> result = new ArrayList<String>();
        for (int j = 0; j < this.sizeY; ++j){
            String tmpStr = "";
            for (int i = 0; i < this.sizeX; ++i){
                tmpStr += this.myCells.get(j * this.sizeX + i).getSymbol();
            }
            result.add(tmpStr);
        }
        return result;
    }

}
