package org.racketsimulator.expression;

import org.racketsimulator.environment.Environment;

import java.util.Collection;
import java.util.List;

public class QExpression implements Expression{
    private final List<Expression> values;

    public QExpression(List<Expression> values) {
        this.values = values;
    }

    /**
     * @return this instance.
     */
    @Override
    public Expression evaluate() {
        return this;
    }

    /**
     * @return the value of every Expression in the QExpression or "" if there isn't any.
     */
    @Override
    public String content() {
        if (values.isEmpty())
            return "'()";

        StringBuilder result = new StringBuilder();
        for (Expression value : values) {
            if (!(value instanceof Empty)){
                result.append(value.content()).append(" ");
            } else {
                result.append("'()").append(" ");
            }
        }

        return result.toString().trim();
    }
}
