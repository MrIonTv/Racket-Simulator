package org.racketsimulator.expression;

import org.racketsimulator.environment.Environment;

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
    public String content() {
        return "'( )";
    }

    /**
     * @return 0
     */
    @Override
    public int valueSize() {
        return 0;
    }
}
