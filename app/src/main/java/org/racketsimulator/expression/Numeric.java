package org.racketsimulator.expression;

import java.util.List;

public class Numeric implements Expression{
    private final int value;
    
    public Numeric(int value){
        this.value = value;
    }

    /**
     * @return this instance
     */
    @Override
    public Expression evaluate() {
        return this;
    }

    /**
     * @return this instance int value.
     */
    @Override
    public List<Expression> content() {
        return List.of(this);
    }

    /**
     * @return always 1
     */
    @Override
    public int valueSize() {
        return 1;
    }

    @Override
    public String stringContent() {
        return value + "";
    }
}
