package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Or;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class OrTest {
    @Test
    public void testAllTrueArguments() {
        Callable orOperation = new Or();
        List<Expression> args = Arrays.asList(
                new Symbol("#t"),
                new Symbol("#t"),
                new Symbol("#t")
        );

        Expression result = orOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.stringContent(), "The result should be #t when at least one argument is true.");
    }

    @Test
    public void testOneTrueArgument() {
        Callable orOperation = new Or();
        List<Expression> args = Arrays.asList(
                new Symbol("#f"),
                new Symbol("#t"),
                new Symbol("#f")
        );

        Expression result = orOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.stringContent(), "The result should be #t when at least one argument is true.");
    }

    @Test
    public void testAllFalseArguments() {
        Callable orOperation = new Or();
        List<Expression> args = Arrays.asList(
                new Symbol("#f"),
                new Symbol("#f")
        );

        Expression result = orOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.stringContent(), "The result should be #f when all arguments are false.");
    }

    @Test
    public void testInvalidArguments() {
        Callable orOperation = new Or();
        List<Expression> args = Arrays.asList(
                new Symbol("#t"),
                new Numeric(1)
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            orOperation.execute(args);
        }, "An exception should be thrown when a non-boolean argument is passed.");
    }

    @Test
    public void testEmptyArguments() {
        Callable orOperation = new Or();

        assertThrows(InvalidCallableArgs.class, () -> {
            orOperation.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable orOperation = new Or();
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Symbol("#f")), runtimeEnv),
                new Symbol("#t")
        );

        Expression result = orOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.stringContent(), "The result should be #t when at least one argument evaluates to true.");
    }

}
