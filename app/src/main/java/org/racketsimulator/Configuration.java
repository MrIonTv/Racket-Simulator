package org.racketsimulator;

import org.racketsimulator.callable.Callable;
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
        HashMap<Symbol, Callable> sourceMap = sourceMap();
        Environment source = source(sourceMap);
        return new EntranceParser(runTime(source), source);
    }

    public Environment source(HashMap<Symbol, Callable> sourceMap) {
        return new DefaultEnvironment(sourceMap);
    }

    public Environment runTime(Environment source) {
        return new DefaultEnvironment(runTimeMap(), source);
    }

    public HashMap<Symbol, Callable> sourceMap() {
        return new HashMap<Symbol, Callable>() {{
            put(new Symbol("+"), new Addition());
            put(new Symbol("-"), new Subtraction());
            put(new Symbol("*"), new Multiplication());
            put(new Symbol("/"), new Quotient());
            put(new Symbol("%"), new Remainder());

            put(new Symbol("="), new Equals());
            put(new Symbol(">="), new GreaterEqualsThan());
            put(new Symbol(">"), new GreaterThan());
            put(new Symbol("<"), new LessThan());
            put(new Symbol("<="), new LessEqualsThan());

            put(new Symbol("AND"), new And());
            put(new Symbol("OR"), new Or());
            put(new Symbol("NOT"), new Not());
        }};
    }
    //TODO
    public HashMap<Symbol, Callable> runTimeMap() {
        return new HashMap<Symbol, Callable>();
    }
}
