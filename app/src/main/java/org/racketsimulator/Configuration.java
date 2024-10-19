package org.racketsimulator;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.SelfCallable;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.And;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Not;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Or;
import org.racketsimulator.callable.builtin.arithmetic.booleans.relational.*;
import org.racketsimulator.callable.builtin.arithmetic.numerical.*;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.expressionbuilder.EntranceParser;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.HashMap;

public class Configuration {
    public ExpressionBuilder entranceParser() {
        Environment source = source();
        return new EntranceParser(runTime(source), source);
    }

    public Environment source() {
        Environment source = new DefaultEnvironment();
        sourceMap(source);
        return source;
    }

    public Environment runTime(Environment source) {
        return new DefaultEnvironment(source);
    }

    public void sourceMap(Environment source) {
            source.defineSymbol(new Symbol("+"), new Addition(source));
            source.defineSymbol(new Symbol("-"), new Subtraction());
            source.defineSymbol(new Symbol("*"), new Multiplication());
            source.defineSymbol(new Symbol("/"), new Quotient());
            source.defineSymbol(new Symbol("%"), new Remainder());
            source.defineSymbol(new Symbol("="), new Equals());
            source.defineSymbol(new Symbol(">="), new GreaterEqualsThan());
            source.defineSymbol(new Symbol(">"), new GreaterThan());
            source.defineSymbol(new Symbol("<"), new LessThan());
            source.defineSymbol(new Symbol("<="), new LessEqualsThan());

            source.defineSymbol(new Symbol("AND"), new And());
            source.defineSymbol(new Symbol("OR"), new Or());
            source.defineSymbol(new Symbol("NOT"), new Not());

            source.defineSymbol(new Symbol("#t"), new SelfCallable(new Symbol("#t")));
            source.defineSymbol(new Symbol("#f"), new SelfCallable(new Symbol("#f")));
    }
    //TODO
    public HashMap<String, Callable> runTimeMap() {
        return new HashMap<String, Callable>(){{

        }};
    }
}
