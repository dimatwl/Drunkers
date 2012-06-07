package ru.spbau.shestavin.drunkers.fields;

import ru.spbau.shestavin.drunkers.core.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HexField extends Field {

    public HexField(Integer inpRowSize, Integer inpColSize) {
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
                if (i < super.colSizeReal - 1 && j > 0) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i + 1, j - 1));
                }
                if (i > 0 && j < super.rowSizeReal - 1) {
                    super.cellAt(i, j).addNeighbour(super.cellAt(i - 1, j + 1));
                }
            }
        }
    }

    @Override
    public List<String> getTextRepresentation() {
        List<String> result = new ArrayList<String>();
        final int fieldWidth = 15 * 6 + 2 * 7;
        for (int layerNum = 0; layerNum < super.colSizeReal * 2; ++layerNum) {
            int layerWidth = 0;
            int i = 0;
            int j = 0;
            if (layerNum < super.colSizeReal) {
                layerWidth = layerNum + 1;
                i = layerNum;
                j = 0;
                if (layerNum == super.colSizeReal - 1) {
                    ++layerNum;
                }
            } else {
                layerWidth = super.colSizeReal * 2 - layerNum;
                i = super.colSizeReal - 1;
                j = super.rowSizeReal - layerWidth;
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
                sndStr += ".  " + super.cellAt(i, j).getSymbol() + "  ";
            }
            fstStr += '.';
            sndStr += '.';
            result.add(fstStr);
            result.add(sndStr);
            if (layerNum == super.colSizeReal * 2 - 1) {
                String lastStr = new String(spaces);
                lastStr += "   .";
                result.add(lastStr);
            }
        }
        return result;
    }
}

