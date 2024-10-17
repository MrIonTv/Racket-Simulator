package org.racketsimulator.expression;

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
    public String content() {
        return value + "";
    }

    /**
     * @return
     */
    @Override
    public int valueSize() {
        return 1;
    }

}
