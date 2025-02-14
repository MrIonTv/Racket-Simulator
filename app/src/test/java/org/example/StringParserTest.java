package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class StringParserTest {

    private ExpressionBuilder parser;

    @BeforeEach
    public void setUp() {
        Configuration config = new Configuration();
        parser = config.entranceParser();
    }

    @Test
    public void testSingleNumericToken() {
        String input = "42";
        Expression result = parser.build(input);

        assertInstanceOf(Numeric.class, result);
        assertEquals("42", (result).stringContent());

    }

    @Test
    public void testSingleSymbolToken() {
        String input = "x";
        Expression result = parser.build(input);

        assertInstanceOf(Symbol.class, result);
        assertEquals("x", ((Symbol) result).stringContent());
    }

    @Test
    public void testSimpleSExpression() {
        String input = "(+ 42)";
        Expression result = parser.build(input);

        assertInstanceOf(SExpression.class, result);
        SExpression sExpression = (SExpression) result;

        assertEquals(2, sExpression.valueSize());
    }

    @Test
    public void testEmptySExpression() {
        String input = "()";

        Expression sExpression = parser.build(input);
        assertEquals(0, sExpression.valueSize());
    }

    @Test
    public void testNestedSExpression() {
        String input = "(x (y 42))";
        Expression result = parser.build(input);

        assertInstanceOf(SExpression.class, result);
        SExpression sExpression = (SExpression) result;

        assertEquals(2, sExpression.valueSize());
    }

    @Test
    public void testSugar() {
        String input = "'x";
        Expression result = parser.build(input);

        assertInstanceOf(SExpression.class, result);
        SExpression sExpression = (SExpression) result;

        assertEquals(2, sExpression.valueSize());
    }

    @Test
    public void testComplexSugar() {
        String input = "'(x (y 42))";
        Expression result = parser.build(input);

        assertInstanceOf(SExpression.class, result);
        SExpression sExpression = (SExpression) result;

        assertEquals(1, sExpression.valueSize());
    }
}
