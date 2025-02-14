package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.numerical.Addition;
import org.racketsimulator.expression.*;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionTest {

    @Test
    public void testSingleNumericArgument() {
        Callable callable = new Addition(new DefaultEnvironment());
        List<Expression> args = List.of(new Numeric(5));

        Expression result = callable.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("5", result.stringContent(), "The result should be 5 when only one number is provided.");
    }

    @Test
    public void testMultipleNumericArguments() {
        Callable callable = new Addition(new DefaultEnvironment());
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Numeric(3),
                new Numeric(2)
        );

        Expression result = callable.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("10", result.stringContent(), "The result should be 10 for 5 + 3 + 2.");
    }

    @Test
    public void testNonNumericArgument() {
        Callable callable = new Addition(new DefaultEnvironment());
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Symbol("#t")
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            callable.execute(args);
        }, "An exception should be thrown when a non-numeric argument is passed.");
    }

    @Test
    public void testEmptyArgumentList() {
        Callable callable = new Addition(new DefaultEnvironment());
        List<Expression> args = List.of();

        assertThrows(InvalidCallableArgs.class, () -> {
            callable.execute(args);
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Callable callable = new Addition(runtimeEnv);

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Numeric(8)), runtimeEnv),
                new Numeric(5)
        );

        Expression result = callable.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("13", result.stringContent(), "The result should be 13 when the first evaluated SExpression is 8 and the second number is 5.");
    }

    @Test
    public void testNestedSExpressionEvaluation() {
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Callable callable = new Addition(runtimeEnv);

        // Nested SExpression: ((8)) should evaluate to 8
        SExpression innerExpression = new SExpression(
                List.of(new Numeric(8)), runtimeEnv);
        SExpression outerExpression = new SExpression(
                List.of(innerExpression), runtimeEnv);

        List<Expression> args = Arrays.asList(
                outerExpression,
                new Numeric(2)
        );

        Expression result = callable.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("10", result.stringContent(), "The result should be 10 when nested SExpressions evaluate to 8 and another argument is 2.");
    }
}
