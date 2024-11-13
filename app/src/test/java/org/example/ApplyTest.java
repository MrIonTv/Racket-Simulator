package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.primitive.Apply;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplyTest {

    private Apply apply;
    private Environment runtime;
    private ExpressionBuilder builder;

    @BeforeEach
    public void setUp() {
        Configuration config = new Configuration();
        runtime = config.runTime();
        builder = config.entranceParser();
        apply = new Apply(runtime, builder);
    }

    @Test
    public void testApplyWithValidArguments() {
        Symbol symbol = new Symbol("+");

        Expression list = new Pair(List.of(new Numeric(1), new Numeric(2)));

        Expression result = apply.execute(List.of(symbol, list));

        assertEquals("3", result.stringContent());
    }

    @Test
    public void testApplyWithInvalidNumberOfArguments() {
        Symbol symbol = new Symbol("+");
        assertThrows(InvalidCallableArgs.class, () ->apply.execute(List.of(symbol)));
    }

    @Test
    public void testApplyWithNonSymbolFirstArgument() {
        Symbol symbol = new Symbol("kek");

        Expression list = new Pair(List.of(new Numeric(1), new Numeric(2)));

        assertThrows(InvalidExpression.class, () ->apply.execute(List.of(symbol, list)));
    }

    @Test
    public void testApplyWithInvalidSecondArgument() {
        Symbol symbol = new Symbol("+");

        QExpression list = new QExpression(new Numeric(1));

        assertThrows(InvalidCallableArgs.class, () ->apply.execute(List.of(symbol, list)));
    }
}
