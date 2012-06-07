package ru.spbau.shestavin.drunkers.fields;

import ru.spbau.shestavin.drunkers.core.Field;

import java.util.ArrayList;
import java.util.List;

public class SquareField extends Field {

    public SquareField(Integer inpRowSize, Integer inpColSize) {
        super(inpRowSize, inpColSize);
        for (int i = 0; i < super.colSizeReal; ++i) {
            for (int j = 0; j < super.rowSizeReal; ++j) {
                if (i > 0) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i - 1, j));
                }
                if (i < super.colSizeReal - 1) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i + 1, j));
                }
                if (j > 0) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i, j - 1));
                }
                if (j < super.rowSizeReal - 1) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i, j + 1));
                }
            }
        }
    }

    @Override
    public List<String> getTextRepresentation() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < super.colSizeReal; ++i) {
            String tmpStr = "";
            for (int j = 0; j < super.rowSizeReal; ++j) {
                tmpStr += super.cellAt(i, j).getSymbol() + " ";
            }
            result.add(tmpStr);
        }
        return result;
    }
}
