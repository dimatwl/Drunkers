package ru.spbau.shestavin.drunkers.tests;

import com.sun.tools.javac.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.Field;
import ru.spbau.shestavin.drunkers.buildings.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FieldTest {

    private class TestField extends Field {

        @Override
        public List<String> getTextRepresentation() {
            return null;
        }

        @Override
        public Cell cellAt(Pair<Integer, Integer> inpPosition) {
            return super.cellAt(inpPosition);
        }

        @Override
        public int getSizeOfRow() {
            return super.getSizeOfRow();
        }

        @Override
        public int getSizeOfCol() {
            return super.getSizeOfCol();
        }

    }

    TestField testField;

    @Before
    public void setUp() throws Exception {
        this.testField = new TestField();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        List<Pair<Integer, Integer>> buildingPositions = new ArrayList<Pair<Integer, Integer>>();
        buildingPositions.add(new Pair<Integer, Integer>(0, 9 + 1));
        buildingPositions.add(new Pair<Integer, Integer>(3 + 1, 14 + 2));
        buildingPositions.add(new Pair<Integer, Integer>(7 + 1, 7 + 1));
        buildingPositions.add(new Pair<Integer, Integer>(3 + 1, 10 + 1));
        buildingPositions.add(new Pair<Integer, Integer>(14 + 2, 4 + 1));
        assertTrue(this.testField.cellAt(buildingPositions.get(0)).getObject() instanceof Bar);
        assertTrue(this.testField.cellAt(buildingPositions.get(1)).getObject() instanceof PoliceStation);
        assertTrue(this.testField.cellAt(buildingPositions.get(2)).getObject() instanceof Pillar);
        assertTrue(this.testField.cellAt(buildingPositions.get(3)).getObject() instanceof Lamp);
        assertTrue(this.testField.cellAt(buildingPositions.get(4)).getObject() instanceof PointForGlass);
        for (int i = 0; i < this.testField.getSizeOfCol() + 2; ++i) {
            for (int j = 0; j < this.testField.getSizeOfRow() + 2; ++j) {
                if ((i == 0 || i == this.testField.getSizeOfCol() + 1 || j == 0 || j == this.testField.getSizeOfRow() + 1) && !buildingPositions.contains(new Pair<Integer, Integer>(i, j))) {
                    assertFalse(this.testField.cellAt(new Pair<Integer, Integer>(i, j)).isEmpty());
                    assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(i, j)).getObject() instanceof Wall);
                } else if (!buildingPositions.contains(new Pair<Integer, Integer>(i, j))) {
                    assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(i, j)).isEmpty());
                }
            }
        }

    }

    @Test
    public void testPutObjectOnField() throws Exception {
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(1, 1)).isEmpty());
        this.testField.putObjectOnField(new Pair<Integer, Integer>(0, 0), new Bar());
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(1, 1)).getObject() instanceof Bar);
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(4, 6)).isEmpty());
        this.testField.putObjectOnField(new Pair<Integer, Integer>(3, 5), new Pillar());
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(4, 6)).getObject() instanceof Pillar);
    }

    @Test
    public void testPutObjectOutsideField() throws Exception {
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(0, 1)).getObject() instanceof Wall);
        this.testField.putObjectOutsideField(new Pair<Integer, Integer>(0, 0), new Bar());
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(0, 1)).getObject() instanceof Bar);
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(0, 6)).getObject() instanceof Wall);
        this.testField.putObjectOutsideField(new Pair<Integer, Integer>(0, 5), new Pillar());
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(0, 6)).getObject() instanceof Pillar);
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(9, 16)).getObject() instanceof Wall);
        this.testField.putObjectOutsideField(new Pair<Integer, Integer>(8, 14), new Bar());
        assertTrue(this.testField.cellAt(new Pair<Integer, Integer>(9, 16)).getObject() instanceof Bar);
    }
}
