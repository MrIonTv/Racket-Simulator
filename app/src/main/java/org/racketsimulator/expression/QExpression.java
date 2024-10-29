package org.racketsimulator.expression;

import java.util.List;

public class QExpression implements Expression{
    private final String QUOTE_SYMBOL = "'";
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
    public List<Expression> content() {
        return values;
    }

    /**
     * @return the quantity of expressions that are contained.
     */
    @Override
    public int valueSize() {
        return values.size();
    }

    @Override
    public String stringContent() {
        StringBuilder result = new StringBuilder();
        result.append(QUOTE_SYMBOL);
        for (Expression value : values) {
            result.append(value.stringContent()).append(" ");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
