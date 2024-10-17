package org.racketsimulator.expression;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.DefinedCallable;
import org.racketsimulator.callable.SelfCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expressionbuilder.EnvironmentBuilder;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.ArrayList;
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
        Expression action = values.getFirst().evaluate();

        if (valueSize() == 1) {
            return action.evaluate();
        }
        if (action instanceof SExpression) {
            action = action.evaluate();
        }

        Callable proc;
        if ((action instanceof QExpression))
            throw new InvalidExpression("SExpression requires any Expression but a QExpression as first argument.");

        proc = validateSymbol((Symbol) action);

        List<Expression> args = values.subList(1, values.size() - 1);

        if (!(proc instanceof DefinedCallable))
            return proc.execute(args);

        ExpressionBuilder constructor = new EnvironmentBuilder(source, runtime);
        Expression procExpression = constructor.build(proc.execute(new ArrayList<>()).content());
        return procExpression.evaluate();
    }

    /**
     * @return the value of every Expression in the SExpression.
     */
    @Override
    public String content() {
        StringBuilder result = new StringBuilder();
        for (Expression value : values) {
            result.append(value.content()).append(" ");
        }
        return result.toString();
    }

    /**
     * @return the quantity of expressions
     */
    @Override
    public int valueSize() {
        return values.size();
    }

    private Callable validateSymbol(Symbol action) {
        if (action.valueSize() != 1)
            throw new InvalidExpression("The callable must be single.");

        Expression symbol = accessSymbol(action);
        if (!(symbol instanceof Symbol))
            return new SelfCallable(symbol);

        Optional<Callable> callable = runtime.search((Symbol) symbol);
        if (callable.isPresent())
            return callable.get();

        callable = source.search((Symbol) symbol);
        if (callable.isPresent())
            return callable.get();

        throw new InvalidExpression("Impossible to solve the symbol: " + symbol.content());
    }

    private Expression accessSymbol(Symbol action) {
        String value = action.content();
        value = value.substring(3, value.length() - 1);

        if (value.isEmpty())
            return new Empty();
        if (value.matches("-?\\d+"))
            return new Numeric(Integer.parseInt(value));
        return new Symbol(value);
    }
}
