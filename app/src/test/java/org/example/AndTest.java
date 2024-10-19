package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.And;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class AndTest {
    @Test
    public void testAllTrueArguments() {
        Callable andOperation = new And();
        List<Expression> args = Arrays.asList(
                new Symbol("#t"),
                new Symbol("#t"),
                new Symbol("#t")
        );

        Expression result = andOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when all arguments are true.");
    }

    @Test
    public void testOneFalseArgument() {
        Callable andOperation = new And();
        List<Expression> args = Arrays.asList(
                new Symbol("#t"),
                new Symbol("#f"),
                new Symbol("#t")
        );

        Expression result = andOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.content(), "The result should be #f when at least one argument is false.");
    }

    @Test
    public void testAllFalseArguments() {
        Callable andOperation = new And();
        List<Expression> args = Arrays.asList(
                new Symbol("#f"),
                new Symbol("#f")
        );

        Expression result = andOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.content(), "The result should be #f when all arguments are false.");
    }

    @Test
    public void testInvalidArguments() {
        Callable andOperation = new And();
        List<Expression> args = Arrays.asList(
                new Symbol("#t"),
                new Numeric(1)
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            andOperation.execute(args);
        }, "An exception should be thrown when a non-boolean argument is passed.");
    }

    @Test
    public void testEmptyArguments() {
        Callable andOperation = new And();

        assertThrows(InvalidCallableArgs.class, () -> {
            andOperation.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable andOperation = new And();
        Configuration config = new Configuration();
        Environment sourceEnv = config.source();
        Environment runtimeEnv = config.runTime(sourceEnv);

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Symbol("#t")), runtimeEnv),
                new Symbol("#f")
        );

        Expression result = andOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.content(), "The result should be #f when at least one argument evaluates to false.");
    }

}

