package org.racketsimulator.expression;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.parser.EnvironmentParser;
import org.racketsimulator.parser.Parser;

import java.util.List;
import java.util.Optional;

public class SExpression implements Expression{
    private final List<Expression> values;
    private final Environment source;
    private final Environment runtime;

    public SExpression(List<Expression> values, Environment source, Environment runtime) {
        if (values.isEmpty()){
            throw new InvalidExpression("SExpression values can't be empty to be created");
        }

        this.source = source;
        this.runtime = runtime;
        this.values = values;
    }

    /**
     * @return the evaluation of all it's expressions
     */
    @Override
    public Expression evaluate() {
        final Expression action = values.getFirst().evaluate();

        if (values.size() == 1) {
            return action.evaluate();
        }

        Callable proc;
        switch (action) {
            case Symbol symbol -> proc = callSymbol((Symbol) action);
            case QExpression qExpression -> proc = callSymbol((Symbol) action.evaluate());
            case null, default -> throw new InvalidExpression("SExpression requires a Callable or a Symbol as " +
                    "first argument.");
        }

        List<Expression> args = values.subList(1, values.size()-1);
        List<QExpression> qArgs = List.of();
        for (Expression arg : args) {
            switch (arg) {
                case QExpression ignored -> qArgs.add((QExpression) arg);
                case SExpression ignored -> qArgs.add((QExpression) arg.evaluate());
                case Symbol ignored -> qArgs.add(runtimeSymbol((Symbol) arg));
                case null, default -> throw new InvalidExpression("Unexpected Expression Type");
            }
        }

        return proc.execute(qArgs);
    }

    /**
     * @return
     */
    @Override
    public String content() {
        return "";
    }
    // TODO
    private Callable callSymbol(Symbol type) {
        Optional<Callable> proc;
        proc = runtime.search(type);
        if (proc.isPresent()) {
            QExpression resumed
            return proc.get();
        }

        proc = source.search(type);
        if (proc.isPresent())
            return proc.get();

        throw new InvalidExpression("The Proc Symbol for the SExpression can't be resolved.");
    }
    private QExpression runtimeSymbol(Symbol arg) {
        Optional<Callable> proc = runtime.search(arg);
        if (proc.isPresent()) {
            final List<QExpression> empty = List.of();
            Parser parser = new EnvironmentParser(runtime, source);
            return (QExpression) parser.build(proc.get().execute(empty).content());
        }

        throw new InvalidExpression("The Proc Symbol for the SExpression can't be resolved.");
    }
}
