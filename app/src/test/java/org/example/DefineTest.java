package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.racketsimulator.Configuration;
import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.builtin.DefinedCallable;
import org.racketsimulator.callable.builtin.SelfCallable;
import org.racketsimulator.callable.builtin.primitive.Define;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;
import org.racketsimulator.expressionbuilder.StringParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DefineTest {
    private Environment runtime;
    private Callable defineCall;

    @BeforeEach
    void setUp() {
        Configuration config = new Configuration();
        runtime = config.runTime();
        defineCall = new Define(runtime);
    }

    @Test
    void defineSimpleTest() {
        Symbol symbol = new Symbol("x");
        Expression body = new SExpression(List.of(
                new Numeric(1)),
                runtime);
        defineCall.execute(List.of(symbol, body));

        Optional<Callable> answer = runtime.search(symbol);
        assert answer.isPresent();
        assertEquals(new DefinedCallable(runtime, List.of(body)).execute(List.of()).stringContent().trim(),
                answer.get().execute(List.of()).stringContent().trim());
    }
    @Test
    void defineComplexTest() {
        Symbol symbol = new Symbol("fun");
        Expression body = new SExpression(List.of(
                new Symbol("+"),
                new Symbol("x"),
                new Numeric(1)),
                runtime);
        defineCall.execute(List.of(symbol, body));

        Optional<Callable> answer = runtime.search(symbol);
        assert answer.isPresent();
        assertEquals(new DefinedCallable(runtime, List.of(body)).execute(List.of()).stringContent().trim(),
                answer.get().execute(List.of()).stringContent().trim());
    }
}
