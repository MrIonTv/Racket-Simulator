package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.callable.builtin.primitive.Cond;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CondTest {

    private Environment runtime;
    private Cond cond;

    @BeforeEach
    void setUp() {
        Configuration config = new Configuration();
        runtime = config.runTime();
        ExpressionBuilder builder = config.entranceParser();
        cond = new Cond(runtime, builder);
    }

    @Test
    void testCondWithTrueCondition() {
        Expression qTrue = new QExpression(new Symbol("true-result"));
        Expression trueCondition = new SExpression(List.of(new Symbol("else"), qTrue), runtime);

        Expression result = cond.execute(List.of(trueCondition));

        assertEquals(qTrue.stringContent(), result.stringContent(), "Debería devolver el resultado de la condición verdadera.");
    }

    @Test
    void testCondWithElseCondition() {
        Expression qElse = new QExpression(new Symbol("else-result"));
        Expression falseCondition = new SExpression(List.of(new Symbol("#f"), new QExpression(new Symbol("false-result"))), runtime);
        Expression elseCondition = new SExpression(List.of(new Symbol("else"), qElse), runtime);

        Expression result = cond.execute(List.of(falseCondition, elseCondition));

        assertEquals(qElse.stringContent(), result.stringContent(), "Debería devolver el resultado de la condición else.");
    }

    @Test
    void testCondThrowsWhenEmpty() {
        assertThrows(InvalidCallableArgs.class, () -> cond.execute(List.of()),
                "Debería lanzar excepción si no hay condiciones.");
    }

    @Test
    void testCondThrowsWhenElseNotAtEnd() {
        Expression elseCondition = new SExpression(List.of(new Symbol("else"), new QExpression(new Symbol("else-result"))), runtime);
        Expression trueCondition = new SExpression(List.of(new Symbol("#t"), new QExpression(new Symbol("true-result"))), runtime);

        assertThrows(InvalidCallableArgs.class, () -> cond.execute(List.of(elseCondition, trueCondition)),
                "Debería lanzar excepción si else no está al final.");
    }
}
