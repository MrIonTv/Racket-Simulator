package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.racketsimulator.Configuration;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.primitive.Eval;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.List;

public class EvalTest {

    @Test
    public void testEvalWithSingleNumericArgument() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        Eval eval = new Eval(runtimeEnv, config.entranceParser());
        Numeric numericArg = new Numeric(42);
        List<Expression> args = List.of(numericArg);

        // Act
        Expression result = eval.execute(args);

        // Assert
        assertEquals(numericArg.stringContent(), result.stringContent(), "Expected the Numeric argument to be evaluated and returned as-is.");
    }

    @Test
    public void testEvalWithQExpressionArgument() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();
        Expression expectedExpression = new Numeric(10);  // Valor esperado de la evaluación

        Eval eval = new Eval(runtimeEnv, config.entranceParser());
        QExpression qExpression = new QExpression(new Symbol("(+ 5 5)"));
        List<Expression> args = List.of(qExpression);

        // Act
        Expression result = eval.execute(args);

        // Assert
        assertEquals(expectedExpression.stringContent(), result.stringContent(), "Expected the QExpression " +
                "to be built and evaluated correctly.");
    }

    @Test
    public void testEvalWithInvalidArgument() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        Eval eval = new Eval(runtimeEnv, config.entranceParser());
        Symbol invalidArg = new Symbol("invalid");  // Argumento no permitido
        List<Expression> args = List.of(invalidArg);

        // Act & Assert
        assertThrows(InvalidCallableArgs.class, () -> {
            eval.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");

    }

    @Test
    public void testEvalWithMultipleArguments() {
        // Arrange
        Configuration config = new Configuration();
        Environment runtimeEnv = config.runTime();

        Eval eval = new Eval(runtimeEnv, config.entranceParser());
        List<Expression> args = List.of(new Numeric(42), new Numeric(10));  // Más de un argumento

        // Act & Assert
        assertThrows(InvalidCallableArgs.class, () -> {
            eval.execute(List.of());
        }, "An exception should be thrown when no arguments are passed.");
    }
}
