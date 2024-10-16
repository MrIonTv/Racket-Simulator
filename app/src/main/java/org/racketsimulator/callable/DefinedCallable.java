package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class DefinedCallable implements Callable{
    private final String body;

    public DefinedCallable(String body) {
        this.body = body;
    }

    /**
     * @param qArgs QExpressions to be evaluated
     * @return
     */
    @Override
    public Expression execute(List<QExpression> qArgs) {
        List<Expression> body = List.of(new Symbol(this.body));
        return new QExpression(body);
    }
}
