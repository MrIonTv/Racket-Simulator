package org.racketsimulator.callable.builtin;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.EnvironmentParser;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;
import java.util.Optional;

public abstract class BuiltinCallable implements Callable {
    protected final Environment runtime;

    public BuiltinCallable(Environment runtime) {
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

    protected abstract Expression validateQExpression(QExpression expression);

    private Callable validateSymbol(Symbol action) {
        Expression symbol = accessSymbol(action);
        if (!(symbol instanceof Symbol))
            return new SelfCallable(symbol);

        Optional<Callable> callable = runtime.search((Symbol) symbol);
        if (callable.isPresent()) {
            ExpressionBuilder builder = new EnvironmentParser(List.of(), runtime);
            Expression result = builder.build(callable.get().execute(List.of()).stringContent());
            return new SelfCallable(result);
        }

        throw new InvalidExpression("Impossible to solve the symbol: " + symbol.stringContent());
    }

    protected Expression accessSymbol(Symbol action) {
        String value = action.stringContent();

        if (value.isEmpty())
            return new Empty();
        if (value.matches("-?\\d+"))
            return new Numeric(Integer.parseInt(value));
        return action;
    }
}
