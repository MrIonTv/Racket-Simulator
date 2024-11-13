package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.primitive.Car;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Environment runtime;
    private Car car;
    
    @BeforeEach
    void setUp() {
        Configuration config = new Configuration();
        runtime = config.runTime();
        ExpressionBuilder builder = config.entranceParser();
        car = new Car(runtime);
    }

    @Test
    public void testExecute_WithCorrectArgument_ReturnsCarElement() {
        Expression list = new Pair(List.of(new Symbol("1"), new Symbol("2"), new Symbol("3")));

        Expression result = car.execute(List.of(list));

        assertInstanceOf(QExpression.class, result);
        QExpression resultQExpression = (QExpression) result;
        assertEquals(1, resultQExpression.valueSize());
        assertEquals("'1", resultQExpression.stringContent());
    }

    @Test
    public void testExecute_WithoutArguments_ThrowsException() {
        assertThrows(InvalidCallableArgs.class, () -> {
            car.execute(List.of());
        });
    }

    @Test
    public void testExecute_WithNonQExpressionArgument_ThrowsException() {
        // Crea un objeto no-QExpression
        Symbol symbol = new Symbol("test");

        // Prueba que lanzar una excepción si el argumento no es un QExpression
        assertThrows(InvalidCallableArgs.class, () -> {
            car.execute(List.of(symbol));
        });
    }

    @Test
    public void testExecute_WithEmptyQExpression_ThrowsException() {
        Expression emptyList = new Pair(List.of());

        assertThrows(InvalidCallableArgs.class, () -> {
            car.execute(List.of(emptyList));
        });
    }

    @Test
    public void testAccessSymbol_WithValidSymbol_ReturnsExpectedExpression() {
        Symbol symbol = new Symbol("(5 5)");
        Numeric symbol2 = new Numeric(89);
        Expression list = new Pair(List.of(symbol, symbol2));
        Expression result = car.execute(List.of(list));

        assertInstanceOf(QExpression.class, result);
        assertEquals("'(5 5)", result.stringContent());
    }

    @Test
    public void testAccessSymbol_WithEmptySymbol_ReturnsEmpty() {
        Empty emptySymbol = new Empty();

        assertThrows(InvalidCallableArgs.class, () -> {
            car.execute(List.of(emptySymbol));
        });
    }

}
