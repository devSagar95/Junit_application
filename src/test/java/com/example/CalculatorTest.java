package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void testAddition() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    public void testSubtraction() {
        Calculator calc = new Calculator();
        assertEquals(1, calc.subtract(3, 2));
    }

    @Test
    public void testFailureCase() {
        Calculator calc = new Calculator();
        assertEquals(0, calc.subtract(2, 2)); // This will pass
    }
}
