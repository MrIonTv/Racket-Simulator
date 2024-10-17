package org.racketsimulator.expression;

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
        result.append("'( ");
        for (Expression value : values) {
            result.append(value.content()).append(" ");
        }
        result.append(")");
        return result.toString();
    }

    /**
     * @return the quantity of expressions that are contained.
     */
    @Override
    public int valueSize() {
        return values.size();
    }
}
