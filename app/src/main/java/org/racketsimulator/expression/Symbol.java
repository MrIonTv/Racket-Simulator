package org.racketsimulator.expression;

import java.util.List;

public class Symbol implements Expression{
    private final String value;

    public Symbol(String value) {
        this.value = value.trim();
    }

    /**
     * @return this instance.
     */
    @Override
    public Expression evaluate() {
        return this;
    }

    /**
     * @return this instance text value.
     */
    @Override
    public List<Expression> content() {
        return List.of(this);
    }

    /**
     * @return
     */
    @Override
    public int valueSize() {
        return 1;
    }

    /**
     * @return 
     */
    @Override
    public String stringContent() {
        return value;
    }
}
