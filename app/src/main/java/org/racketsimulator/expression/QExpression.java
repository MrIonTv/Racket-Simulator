package org.racketsimulator.expression;

import java.util.List;

public class QExpression implements Expression{
    private static final String QUOTE_SYMBOL = "'";
    private final Expression value;

    public QExpression(Expression value) {
        this.value = value;
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
        return List.of(value);
    }

    /**
     * @return the quantity of expressions that are contained.
     */
    @Override
    public int valueSize() {
        return 1;
    }

    @Override
    public String stringContent() {
        if (value instanceof Numeric)
            return value.stringContent();

        return QUOTE_SYMBOL + value.stringContent();
    }
}
