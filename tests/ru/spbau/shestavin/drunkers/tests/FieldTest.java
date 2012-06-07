package ru.spbau.shestavin.drunkers.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.Field;
import ru.spbau.shestavin.drunkers.buildings.*;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FieldTest {

    private class TestField extends Field {

        public TestField(Integer inpRowSize, Integer inpColSize){
            super(inpRowSize, inpColSize);
        }

        @Override
        public List<String> getTextRepresentation() {
            return null;
        }

        @Override
        public Cell cellAt(Point inpPoint) {
            return super.cellAt(inpPoint);
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
        this.testField = new TestField(15, 15);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        for (int i = 0; i < this.testField.getSizeOfCol() + 2; ++i) {
            for (int j = 0; j < this.testField.getSizeOfRow() + 2; ++j) {
                if ((i == 0 || i == this.testField.getSizeOfCol() + 1 || j == 0 || j == this.testField.getSizeOfRow() + 1)) {
                    assertFalse(this.testField.cellAt(new Point(i, j)).isEmpty());
                    assertTrue(this.testField.cellAt(new Point(i, j)).getObject() instanceof Wall);
                } else {
                    assertTrue(this.testField.cellAt(new Point(i, j)).isEmpty());
                }
            }
        }

    }

    @Test
    public void testPutObjectOnField() throws Exception {
        assertTrue(this.testField.cellAt(new Point(1, 1)).isEmpty());
        this.testField.putObjectOnField(new Point(0, 0), new Bar());
        assertTrue(this.testField.cellAt(new Point(1, 1)).getObject() instanceof Bar);
        assertTrue(this.testField.cellAt(new Point(4, 6)).isEmpty());
        this.testField.putObjectOnField(new Point(3, 5), new Pillar());
        assertTrue(this.testField.cellAt(new Point(4, 6)).getObject() instanceof Pillar);
    }

    @Test
    public void testPutObjectOutsideField() throws Exception {
        assertTrue(this.testField.cellAt(new Point(0, 1)).getObject() instanceof Wall);
        this.testField.putObjectOnBorder(new Point(0, 0), new Bar());
        assertTrue(this.testField.cellAt(new Point(0, 1)).getObject() instanceof Bar);
        assertTrue(this.testField.cellAt(new Point(0, 6)).getObject() instanceof Wall);
        this.testField.putObjectOnBorder(new Point(0, 5), new Pillar());
        assertTrue(this.testField.cellAt(new Point(0, 6)).getObject() instanceof Pillar);
        assertTrue(this.testField.cellAt(new Point(9, 16)).getObject() instanceof Wall);
        this.testField.putObjectOnBorder(new Point(8, 14), new Bar());
        assertTrue(this.testField.cellAt(new Point(9, 16)).getObject() instanceof Bar);
    }
}
