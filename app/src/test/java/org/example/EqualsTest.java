package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.booleans.relational.Equals;
import org.racketsimulator.expression.*;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class EqualsTest {

    @Test
    public void testEqualNumbers() {
        Callable equalsOperation = new Equals();
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Numeric(5),
                new Numeric(5)
        );

        Expression result = equalsOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when all numbers are equal.");
    }

    @Test
    public void testDifferentNumbers() {
        Callable equalsOperation = new Equals();
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Numeric(6),
                new Numeric(5)
        );

        Expression result = equalsOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.content(), "The result should be #f when at least one number is different.");
    }

    @Test
    public void testSingleArgument() {
        Callable equalsOperation = new Equals();
        List<Expression> args = List.of(new Numeric(7));

        Expression result = equalsOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when there is only one argument.");
    }

    @Test
    public void testNonNumericArgument() {
        Callable equalsOperation = new Equals();
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Symbol("#t")
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            equalsOperation.execute(args);
        }, "An exception should be thrown when a non-numeric argument is passed.");
    }

    @Test
    public void testEmptyArguments() {
        Callable equalsOperation = new Equals();

        assertThrows(InvalidCallableArgs.class, () -> {
            equalsOperation.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable equalsOperation = new Equals();
        Configuration config = new Configuration();
        Environment sourceEnv = config.source(config.sourceMap());
        Environment runtimeEnv = config.runTime(sourceEnv);

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Numeric(5)), sourceEnv, runtimeEnv),
                new Numeric(5)
        );

        Expression result = equalsOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when all evaluated arguments are equal.");
    }

    @Test
    public void testNestedSExpressionEvaluation() {
        Callable equalsOperation = new Equals();
        Configuration config = new Configuration();
        Environment sourceEnv = config.source(config.sourceMap());
        Environment runtimeEnv = config.runTime(sourceEnv);

        SExpression innerExpression = new SExpression(
                List.of(new Numeric(5)), sourceEnv, runtimeEnv);
        SExpression outerExpression = new SExpression(
                List.of(innerExpression), sourceEnv, runtimeEnv);

        List<Expression> args = Arrays.asList(
                outerExpression,
                new Numeric(5)
        );

        Expression result = equalsOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when nested SExpressions evaluate to equal values.");
    }
}
