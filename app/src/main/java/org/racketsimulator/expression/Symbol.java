package org.racketsimulator.expression;

import org.racketsimulator.environment.Environment;

public class Symbol implements Expression{
    private final String value;

    public Symbol(String value) {
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
     * @return this instance text value.
     */
    @Override
    public String content() {
        return value;
    }
}
