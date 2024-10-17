package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.EntranceParser;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class EntranceParserTest {

    private EntranceParser parser;

    @BeforeEach
    public void setUp() {
        // Puedes simular tus entornos o usar mocks
        Environment sourceEnv = new DefaultEnvironment();
        Environment runtimeEnv = new DefaultEnvironment();

        // Crea el EntranceParser
        parser = new EntranceParser(runtimeEnv, sourceEnv);
    }

    @Test
    public void testSingleNumericToken() {
        String input = "42";
        Expression result = parser.build(input);

        assertInstanceOf(Numeric.class, result);
        assertEquals("42", (result).content());

    }

    @Test
    public void testSingleSymbolToken() {
        String input = "x";
        Expression result = parser.build(input);

        assertInstanceOf(Symbol.class, result);
        assertEquals("x", ((Symbol) result).content());
    }

    @Test
    public void testSimpleSExpression() {
        String input = "(x 42)";
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
