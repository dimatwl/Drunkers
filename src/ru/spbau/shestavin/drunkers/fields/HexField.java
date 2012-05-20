package ru.spbau.shestavin.drunkers.fields;

import com.sun.tools.javac.util.Pair;
import ru.spbau.shestavin.drunkers.abstraction.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HexField extends Field {

    public HexField() {
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
                if (i < super.getSizeOfCol() - 1 && j > 0) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i + 1, j - 1)));
                }
                if (i > 0 && j < super.getSizeOfRow() - 1) {
                    super.cellAt(new Pair<Integer, Integer>(i, j)).addNeighbour(super.cellAt(new Pair<Integer, Integer>(i - 1, j + 1)));
                }
            }
        }
    }

    @Override
    public List<String> getTextRepresentation() {
        List<String> result = new ArrayList<String>();
        final int fieldWidth = 15 * 6 + 2 * 7;
        //boolean increase = true;
        for (int layerNum = 0; layerNum < super.getSizeOfCol() * 2; ++layerNum) {
            int layerWidth = 0;
            int i = 0;
            int j = 0;
            if (layerNum < super.getSizeOfCol()) {
                layerWidth = layerNum + 1;
                i = layerNum;
                j = 0;
                if (layerNum == super.getSizeOfCol() - 1) {
                    ++layerNum;
                }
            } else {
                layerWidth = super.getSizeOfCol() * 2 - layerNum;
                i = super.getSizeOfCol() - 1;
                j = super.getSizeOfRow() - layerWidth;
            }
            final int currentOffset = fieldWidth / 2 - 3 * layerWidth + 1;
            char[] spaces = new char[currentOffset];
            Arrays.fill(spaces, ' ');
            if (layerNum == 0) {
                String firstStr = new String(spaces);
                firstStr += "   .";
                result.add(firstStr);
            }
            String fstStr = new String(spaces);
            String sndStr = new String(spaces);
            for (int k = 0; k < layerWidth; ++k, --i, ++j) {
                fstStr += ".     ";
                sndStr += ".  " + super.cellAt(new Pair<Integer, Integer>(i, j)).getSymbol() + "  ";
            }
            fstStr += '.';
            sndStr += '.';
            result.add(fstStr);
            result.add(sndStr);
            if (layerNum == super.getSizeOfCol() * 2 - 1) {
                String lastStr = new String(spaces);
                lastStr += "   .";
                result.add(lastStr);
            }
        }
        return result;
    }
}

