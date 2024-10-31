package org.racketsimulator;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.builtin.SelfCallable;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.And;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Not;
import org.racketsimulator.callable.builtin.arithmetic.booleans.logical.Or;
import org.racketsimulator.callable.builtin.arithmetic.booleans.relational.*;
import org.racketsimulator.callable.builtin.arithmetic.numerical.*;
import org.racketsimulator.callable.builtin.primitive.*;
import org.racketsimulator.environment.DefaultEnvironment;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.expressionbuilder.StringParser;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;
import org.racketsimulator.repl.MainDriver;
import org.racketsimulator.repl.REPL;

import java.util.HashMap;

public class Configuration {
    public REPL mainDriver() {
        return new MainDriver(entranceParser());
    }

    public ExpressionBuilder entranceParser() {
        Environment runtime = runTime();
        return new StringParser(runtime);
    }

    public Environment runTime() {
        Environment runtime = new DefaultEnvironment();
        loadSourceMap(runtime);
        return runtime;
    }

    public void loadSourceMap(Environment runtime) {
        runtime.defineSymbol(new Symbol("+"), new Addition(runtime));
        runtime.defineSymbol(new Symbol("-"), new Subtraction(runtime));
        runtime.defineSymbol(new Symbol("*"), new Multiplication(runtime));
        runtime.defineSymbol(new Symbol("/"), new Quotient(runtime));
        runtime.defineSymbol(new Symbol("%"), new Remainder(runtime));
        runtime.defineSymbol(new Symbol("="), new Equals(runtime));
        runtime.defineSymbol(new Symbol(">="), new GreaterEqualsThan(runtime));
        runtime.defineSymbol(new Symbol(">"), new GreaterThan(runtime));
        runtime.defineSymbol(new Symbol("<"), new LessThan(runtime));
        runtime.defineSymbol(new Symbol("<="), new LessEqualsThan(runtime));

        runtime.defineSymbol(new Symbol("AND"), new And());
        runtime.defineSymbol(new Symbol("OR"), new Or());
        runtime.defineSymbol(new Symbol("NOT"), new Not());

        runtime.defineSymbol(new Symbol("#t"), new SelfCallable(new Symbol("#t")));
        runtime.defineSymbol(new Symbol("#f"), new SelfCallable(new Symbol("#f")));

        runtime.defineSymbol(new Symbol("cond"), new Cond(runtime, new StringParser(runtime)));
        runtime.defineSymbol(new Symbol("quote"), new Quote(runtime));
        runtime.defineSymbol(new Symbol("apply"), new Apply(runtime, new StringParser(runtime)));
        runtime.defineSymbol(new Symbol("eval"), new Eval(runtime, new StringParser(runtime)));

        runtime.defineSymbol(new Symbol("define"), new Define(runtime));
    }
    //TODO
    public HashMap<String, Callable> runTimeMap() {
        return new HashMap<String, Callable>(){
        };
    }
}
