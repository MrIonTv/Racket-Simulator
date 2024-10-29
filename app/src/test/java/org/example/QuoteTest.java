package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.arithmetic.numerical.Quotient;
import org.racketsimulator.callable.builtin.primitive.Quote;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.List;

public class QuoteTest {

    @Test
    public void testExecuteWithSingleNumericArgument() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Callable quoteOperation = new Quote(runtimeEnv);
        Numeric numericArg = new Numeric(42); // Por ejemplo, un número cualquiera
        List<Expression> args = List.of(numericArg);

        // Act
        Expression result = quoteOperation.execute(args);

        // Assert
        assertEquals(numericArg, result, "Expected the numeric argument to be returned as-is.");
    }

    @Test
    public void testExecuteWithNonNumericArgument() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Callable quoteOperation = new Quote(runtimeEnv);
        Symbol symbolArg = new Symbol("(+ 1 2)");  // Un símbolo como argumento
        List<Expression> args = List.of(symbolArg);

        // Act
        Expression result = quoteOperation.execute(args);

        // Assert
        assertInstanceOf(QExpression.class, result, "Expected a QExpression to be returned.");
        assertEquals("'(+ 1 2)", ((QExpression) result).stringContent(), "The QExpression should contain the original argument.");
    }

    @Test
    public void testExecuteWithInvalidArgumentSize() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Callable quoteOperation = new Quote(runtimeEnv);
        Symbol symbolArg1 = new Symbol("x");
        Symbol symbolArg2 = new Symbol("y");
        List<Expression> args = List.of(symbolArg1, symbolArg2);

        // Act & Assert
        assertThrows(InvalidCallableArgs.class, () -> {
            quoteOperation.execute(args);
        }, "An exception should be thrown when no arguments are passed.");    }
}
