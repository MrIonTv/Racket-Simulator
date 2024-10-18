package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.numerical.Quotient;
import org.racketsimulator.expression.*;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuotientTest {

    @Test
    public void testSingleNumericArgument() {
        Callable plus = new Quotient();
        List<Expression> args = List.of(new Numeric(5));

        Expression result = plus.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("5", result.content(), "The result should be 5 when only one number is provided.");
    }

    @Test
    public void testMultipleNumericArguments() {
        Callable plus = new Quotient();
        List<Expression> args = Arrays.asList(
                new Numeric(6),
                new Numeric(3),
                new Numeric(2)
        );

        Expression result = plus.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("1", result.content(), "The result should be 1 for 6 / (3 * 2).");
    }

    @Test
    public void testNonNumericArgument() {
        Callable plus = new Quotient();
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Symbol("#t")
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            plus.execute(args);
        }, "An exception should be thrown when a non-numeric argument is passed.");
    }

    @Test
    public void testEmptyArgumentList() {
        Callable plus = new Quotient();
        List<Expression> args = List.of();

        assertThrows(InvalidCallableArgs.class, () -> {
            plus.execute(args);
        }, "An exception should be thrown when no arguments are passed.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable plus = new Quotient();
        Configuration config = new Configuration();
        Environment sourceEnv = config.source(config.sourceMap());
        Environment runtimeEnv = config.runTime(sourceEnv);

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Numeric(8)), sourceEnv, runtimeEnv),
                new Numeric(5)
        );

        Expression result = plus.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("1", result.content(), "The result should be 1 when the first evaluated SExpression is 8 and the second number is 5.");
    }

    @Test
    public void testNestedSExpressionEvaluation() {
        Callable plus = new Quotient();
        Configuration config = new Configuration();
        Environment sourceEnv = config.source(config.sourceMap());
        Environment runtimeEnv = config.runTime(sourceEnv);

        SExpression innerExpression = new SExpression(
                List.of(new Numeric(8)), sourceEnv, runtimeEnv);
        SExpression outerExpression = new SExpression(
                List.of(innerExpression), sourceEnv, runtimeEnv);

        List<Expression> args = Arrays.asList(
                outerExpression,
                new Numeric(2)
        );

        Expression result = plus.execute(args);
        assertInstanceOf(Numeric.class, result);
        assertEquals("4", result.content(), "The result should be 4 when nested SExpressions evaluate to 8 and another argument is 2.");
    }
}
