package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Not;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class NotTest {

    @Test
    public void testTrueArgument() {
        Callable notOperation = new Not();
        List<Expression> args = List.of(new Symbol("#t"));

        Expression result = notOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.stringContent(), "The result should be #f when the argument is #t.");
    }

    @Test
    public void testFalseArgument() {
        Callable notOperation = new Not();
        List<Expression> args = List.of(new Symbol("#f"));

        Expression result = notOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.stringContent(), "The result should be #t when the argument is #f.");
    }

    @Test
    public void testInvalidArgument() {
        Callable notOperation = new Not();
        List<Expression> args = List.of(new Numeric(1));

        assertThrows(InvalidExpression.class, () -> {
            notOperation.execute(args);
        }, "An exception should be thrown when a non-boolean argument is passed.");
    }

    @Test
    public void testEmptyArguments() {
        Callable notOperation = new Not();

        assertThrows(InvalidCallableArgs.class, () -> {
            notOperation.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testMultipleArguments() {
        Callable notOperation = new Not();
        List<Expression> args = List.of(new Symbol("#t"), new Symbol("#f"));

        assertThrows(InvalidCallableArgs.class, () -> {
            notOperation.execute(args);
        }, "An exception should be thrown when more than one argument is passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable notOperation = new Not();
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        List<Expression> args = List.of(
                new SExpression(List.of(new Symbol("#t")), runtimeEnv)
        );

        Expression result = notOperation.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.stringContent(), "The result should be #f when the SExpression evaluates to #t.");
    }

    @Test
    public void testNestedSExpressionEvaluation() {
        Callable notOperation = new Not();
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        // Nested SExpression (not (not #f)) -> should evaluate to #t
        SExpression innerNotExpression = new SExpression(
                List.of(new Symbol("#f")),runtimeEnv);

        SExpression outerNotExpression = new SExpression(
                List.of(innerNotExpression), runtimeEnv);

        Expression result = notOperation.execute(List.of(outerNotExpression));
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.stringContent(), "The result should be #t when the SExpression evaluates to (not (not #f)).");
    }
}
