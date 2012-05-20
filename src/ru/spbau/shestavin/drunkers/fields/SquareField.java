package ru.spbau.shestavin.drunkers.fields;

import com.sun.tools.javac.util.Pair;
import ru.spbau.shestavin.drunkers.abstraction.Field;

import java.util.ArrayList;
import java.util.List;

public class SquareField extends Field {

    public SquareField() {
        super();
        for (int i = 0; i < super.getSizeOfCol(); ++i) {
            for (int j = 0; j < super.getSizeOfRow(); ++j) {
                if (i > 0) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i - 1, j)));
                }
                if (i < super.getSizeOfCol() - 1) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i + 1, j)));
                }
                if (j > 0) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i, j - 1)));
                }
                if (j < super.getSizeOfRow() - 1) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i, j + 1)));
                }
            }
        }
    }

    @Override
    public List<String> getTextRepresentation() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < super.getSizeOfCol(); ++i) {
            String tmpStr = "";
            for (int j = 0; j < super.getSizeOfRow(); ++j) {
                tmpStr += super.cellAt(new Pair<Integer, Integer>(i, j)).getSymbol() + " ";
            }
            result.add(tmpStr);
        }
        return result;
    }
}
