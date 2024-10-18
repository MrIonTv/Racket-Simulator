package org.example;

import org.junit.jupiter.api.Test;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.booleans.relational.LessEqualsThan;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class LessEqualsThanTest {

    @Test
    public void testGreaterThan() {
        Callable greaterEquals = new LessEqualsThan();
        List<Expression> args = Arrays.asList(
                new Numeric(7),
                new Numeric(5)
        );

        Expression result = greaterEquals.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#f", result.content(), "The result should be #t when the first number is greater than the second.");
    }

    @Test
    public void testEquals() {
        Callable greaterEquals = new LessEqualsThan();
        List<Expression> args = Arrays.asList(
                new Numeric(5),
                new Numeric(5)
        );

        Expression result = greaterEquals.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when the two numbers are equal.");
    }

    @Test
    public void testLessThan() {
        Callable greaterEquals = new LessEqualsThan();
        List<Expression> args = Arrays.asList(
                new Numeric(3),
                new Numeric(5)
        );

        Expression result = greaterEquals.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #f when the first number is less than the second.");
    }

    @Test
    public void testNonNumericArgument() {
        Callable greaterEquals = new LessEqualsThan();
        List<Expression> args = Arrays.asList(
                new Numeric(7),
                new Symbol("#t")
        );

        assertThrows(InvalidCallableArgs.class, () -> {
            greaterEquals.execute(args);
        }, "An exception should be thrown when a non-numeric argument is passed.");
    }

    @Test
    public void testInvalidArgumentCount() {
        Callable greaterEquals = new LessEqualsThan();
        List<Expression> args = List.of(new Numeric(7));

        assertThrows(InvalidCallableArgs.class, () -> {
            greaterEquals.execute(args);
        }, "An exception should be thrown when the number of arguments is not exactly two.");
    }

    @Test
    public void testSExpressionEvaluation() {
        Callable greaterEquals = new LessEqualsThan();
        Environment sourceEnv = new DefaultEnvironment();
        Environment runtimeEnv = new DefaultEnvironment();

        List<Expression> args = Arrays.asList(
                new SExpression(List.of(new Numeric(5)), sourceEnv, runtimeEnv),
                new Numeric(5)
        );

        Expression result = greaterEquals.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when the first evaluated SExpression is greater than the second.");
    }

    @Test
    public void testNestedSExpressionEvaluation() {
        Callable greaterEquals = new LessEqualsThan();
        Environment sourceEnv = new DefaultEnvironment();
        Environment runtimeEnv = new DefaultEnvironment();

        SExpression innerExpression = new SExpression(
                List.of(new Numeric(8)), sourceEnv, runtimeEnv);
        SExpression outerExpression = new SExpression(
                List.of(innerExpression), sourceEnv, runtimeEnv);

        List<Expression> args = Arrays.asList(
                outerExpression,
                new Numeric(8)
        );

        Expression result = greaterEquals.execute(args);
        assertInstanceOf(Symbol.class, result);
        assertEquals("#t", result.content(), "The result should be #t when nested SExpressions evaluate to equal values.");
    }
}
