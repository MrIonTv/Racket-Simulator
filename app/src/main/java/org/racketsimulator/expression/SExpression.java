package org.racketsimulator.expression;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.builtin.DefinedCallable;
import org.racketsimulator.callable.builtin.SelfCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expressionbuilder.EnvironmentParser;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SExpression implements Expression{
    private final List<Expression> values;
    private final Environment runtime;

    public SExpression(List<Expression> values,Environment runtime) {
        if (values.isEmpty()){
            throw new InvalidExpression("SExpression values can't be empty to be created");
        }

        this.runtime = runtime;
        this.values = values;
    }

    /**
     * @return the evaluation of all it's expressions
     */
    @Override
    public Expression evaluate() {
        Expression action = values.getFirst().evaluate();

        if (action instanceof SExpression) {
            action = action.evaluate();
        }

        if ((action instanceof QExpression))
            if (valueSize() == 1)
                return action;
            else
                throw new InvalidExpression("SExpression can't have QExpression as first proc argument.");

        if (action instanceof Numeric) {
            return action;
        }

        Callable proc;
        proc = validateSymbol((Symbol) action);

        List<Expression> args = values.subList(1, values.size());

        if (!(proc instanceof DefinedCallable))
            return proc.execute(args);

        ExpressionBuilder constructor = new EnvironmentParser(args, runtime);
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
        Expression symbol = accessSymbol(action);
        if (!(symbol instanceof Symbol))
            return new SelfCallable(symbol);

        Optional<Callable> callable = runtime.search((Symbol) symbol);
        if (callable.isPresent())
            return callable.get();

        throw new InvalidExpression("Impossible to solve the symbol: " + symbol.content());
    }

    private Expression accessSymbol(Symbol action) {
        String value = action.content();

        if (value.isEmpty())
            return new Empty();
        if (value.matches("-?\\d+"))
            return new Numeric(Integer.parseInt(value));
        return action;
    }
}
