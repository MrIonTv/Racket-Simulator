package org.racketsimulator.expression;

import java.util.List;

public class Empty implements Expression{
    /**
     * @return this instance
     */
    @Override
    public Expression evaluate() {
        return this;
    }

    /**
     * @return an empty String
     */
    @Override
    public List<Expression> content() {
        return List.of(this);
    }

    /**
     * @return 0
     */
    @Override
    public int valueSize() {
        return 0;
    }

    @Override
    public String stringContent() {
        return "'()";
    }
}
