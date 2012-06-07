package ru.spbau.shestavin.drunkers.core;

import ru.spbau.shestavin.drunkers.buildings.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class FieldFactory {


    public abstract Field createEmptyField(Integer inpRowSize, Integer inpColSize);

    public abstract Field createRegularField();

    public abstract Field createCustomField(Integer inpRowSize, Integer inpColSize, Map<FieldObject, Point> inpObjectsOnField, Map<FieldObject, Point> inpObjectsOnBorder) throws NoSuchPositionException;

    protected Field configure(Field inpField, Map<FieldObject, Point> inpObjectsOnField, Map<FieldObject, Point> inpObjectsOnBorder) throws NoSuchPositionException {
        for (FieldObject obj : inpObjectsOnField.keySet()) {
            inpField.putObjectOnField(inpObjectsOnField.get(obj), obj);
        }
        for (FieldObject obj : inpObjectsOnBorder.keySet()) {
            inpField.putObjectOnBorder(inpObjectsOnBorder.get(obj), obj);
        }
        return inpField;
    }

    protected Field configureRegular(Field inpField) {
        final Point barPoint = new Point(0, 9);
        final Point policeStationPoint = new Point(3, 14);
        final Point pillarPoint = new Point(7, 7);
        final Point lampPoint = new Point(3, 10);
        final Point pointForGlassPoint = new Point(14, 4);
        final Integer illuminationRadius = 3;

        Map<FieldObject, Point> objectsOnField = new HashMap<FieldObject, Point>();
        Map<FieldObject, Point> objectsOnBorder = new HashMap<FieldObject, Point>();

        objectsOnField.put(new Pillar(), pillarPoint);
        objectsOnField.put(new Lamp(illuminationRadius), lampPoint);
        objectsOnBorder.put(new Bar(), barPoint);
        objectsOnBorder.put(new PoliceStation(), policeStationPoint);
        objectsOnBorder.put(new PointForGlass(), pointForGlassPoint);

        try {
            return this.configure(inpField, objectsOnField, objectsOnBorder);
        } catch (NoSuchPositionException e) {
            throw new RuntimeException("That's can't be!");
        }
    }
}
