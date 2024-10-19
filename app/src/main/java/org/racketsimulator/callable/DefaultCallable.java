package org.racketsimulator.callable;

import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.List;
import java.util.Optional;

public abstract class DefaultCallable implements Callable {
    private final Environment runtime;

    protected DefaultCallable(Environment runtime) {
        this.runtime = runtime;
    }

    @Override
    public Expression execute(List<Expression> args) {
        return null;
    }

    protected Expression fixExpression(Expression expression) {
        if (expression instanceof SExpression)
            return expression.evaluate();
        if (expression instanceof QExpression)
            return validateQExpression((QExpression) expression);
        if (expression instanceof Symbol)
            return validateSymbol((Symbol) expression).execute(List.of());
        return expression;
    }

    protected Expression validateQExpression(QExpression expression) {
        return null;
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
