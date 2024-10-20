package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.DefinedCallable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SExpressionTest {
    private Environment runtime;

    @BeforeEach
    void setUp() {
        Configuration config = new Configuration();
        runtime = config.runTime();
        runtime.defineSymbol(new Symbol("nine"), new DefinedCallable(runtime, "9"));
        runtime.defineSymbol(new Symbol("OPERATION"), new DefinedCallable(runtime, "+ nine 1"));
    }

    @Test
    void testEvaluateWithValidSExpression() {
        Expression symbolExpression = new Symbol("+");
        Expression arg1 = new Numeric(1);
        Expression arg2 = new Numeric(2);

        List<Expression> values = new ArrayList<>(List.of(symbolExpression, arg1, arg2));
        SExpression sExpression = new SExpression(values, runtime);

        Expression result = sExpression.evaluate();
        assertInstanceOf(Numeric.class, result);
        assertEquals("3", result.content());
    }

    @Test
    void testEvaluateWithSubSExpression() {
        Expression symbolExpression = new Symbol("-");
        Expression arg1 = new Numeric(5);
        Expression arg2 = new Numeric(3);
        Expression arg3 = new Numeric(0);
        Expression arg4 = new SExpression(List.of(symbolExpression, arg2, arg3), runtime);

        List<Expression> values = new ArrayList<>(List.of(symbolExpression, arg1, arg4));
        SExpression sExpression = new SExpression(values, runtime);

        Expression result = sExpression.evaluate();
        assertInstanceOf(Numeric.class, result);
        assertEquals("2", result.content());
    }

    @Test
    void testEvaluateThrowsForEmptyValues() {
        List<Expression> emptyValues = new ArrayList<>();
        assertThrows(InvalidExpression.class, () -> new SExpression(emptyValues, runtime));
    }

    @Test
    void testEvaluateThrowsForQExpression() {
        Expression qExpression = new QExpression(new ArrayList<>());
        Expression arg1 = new Numeric(1);
        Expression arg2 = new Numeric(2);

        List<Expression> values = new ArrayList<>(List.of(qExpression, arg1, arg2));
        SExpression sExpression = new SExpression(values, runtime);

        assertThrows(InvalidExpression.class, sExpression::evaluate);
    }

    @Test
    void testContentReturnsConcatenatedExpressionContents() {
        Expression arg1 = new Numeric(1);
        Expression arg2 = new Numeric(2);

        List<Expression> values = new ArrayList<>(List.of(arg1, arg2));
        SExpression sExpression = new SExpression(values, runtime);

        assertEquals("1 2 ", sExpression.content());
    }

    @Test
    void testValidateUnknownSymbol() {
        Symbol invalidSymbol = new Symbol("unknown");

        List<Expression> values = new ArrayList<>(List.of(invalidSymbol));
        SExpression sExpression = new SExpression(values, runtime);

        assertThrows(InvalidExpression.class, sExpression::evaluate);

    }

    @Test
    void testEvaluateWithLogicalOperators() {
        Expression symbolAnd = new Symbol("AND");
        Expression trueExpr = new Numeric(1);
        Expression falseExpr = new Numeric(0);

        List<Expression> valuesAnd = new ArrayList<>(List.of(symbolAnd, trueExpr, falseExpr));
        SExpression sExpressionAnd = new SExpression(valuesAnd, runtime);

        assertThrows(InvalidCallableArgs.class, sExpressionAnd::evaluate); // Debe lanzar una excepción

        Expression symbolOr = new Symbol("OR");

        List<Expression> valuesOr = new ArrayList<>(List.of(symbolOr, falseExpr, trueExpr));
        SExpression sExpressionOr = new SExpression(valuesOr, runtime);

        assertThrows(InvalidCallableArgs.class, sExpressionOr::evaluate); // Debe lanzar una excepción

    }

    @Test
    void testEvaluateWithDefinedSymbol() {
        Expression symbolExpression = new Symbol("nine");

        List<Expression> values = new ArrayList<>(List.of(symbolExpression));
        SExpression sExpression = new SExpression(values, runtime);

        Expression result = sExpression.evaluate();
        assertInstanceOf(Numeric.class, result);
        assertEquals("9", result.content()); // Verificamos que el contenido sea "9"
    }

    @Test
    void testEvaluateWithDefinedSymbolForOperation() {
        Expression symbolExpression = new Symbol("OPERATION");

        List<Expression> values = new ArrayList<>(List.of(symbolExpression));
        SExpression sExpression = new SExpression(values, runtime);

        Expression result = sExpression.evaluate();
        assertInstanceOf(Numeric.class, result);
        assertEquals("10", result.content()); // Verificamos que el contenido sea "9"
    }

    @Test
    void testComplexEvaluation() {
        Expression s1 = new Symbol("/");
        Expression s2 = new Symbol("OPERATION");
        Expression s3 = new Numeric(2);

        List<Expression> values = new ArrayList<>(List.of(s1, s2, s3));
        SExpression sExpression = new SExpression(values, runtime);

        Expression result = sExpression.evaluate();
        assertInstanceOf(Numeric.class, result);
        assertEquals("5", result.content());
    }
}
