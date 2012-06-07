package ru.spbau.shestavin.drunkers.fields;


import ru.spbau.shestavin.drunkers.core.Field;
import ru.spbau.shestavin.drunkers.core.FieldFactory;
import ru.spbau.shestavin.drunkers.core.FieldObject;
import ru.spbau.shestavin.drunkers.core.NoSuchPositionException;

import java.awt.*;
import java.util.Map;

public class HexFieldFactory extends FieldFactory {

    @Override
    public Field createEmptyField(Integer inpRowSize, Integer inpColSize) {
        return new HexField(inpRowSize, inpColSize);
    }

    @Override
    public Field createRegularField() {
        return super.configureRegular(new HexField(15, 15));
    }

    @Override
    public Field createCustomField(Integer inpRowSize, Integer inpColSize, Map<FieldObject, Point> inpObjectsOnField, Map<FieldObject, Point> inpObjectsOnBorder) throws NoSuchPositionException {
        return super.configure(new HexField(inpRowSize, inpColSize), inpObjectsOnField, inpObjectsOnBorder);
    }
}
