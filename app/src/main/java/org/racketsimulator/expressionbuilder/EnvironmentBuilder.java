package org.racketsimulator.expressionbuilder;

import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

public class EnvironmentBuilder implements ExpressionBuilder {
    private final Environment source;
    private final Environment runtime;
    public EnvironmentBuilder(Environment runtime, Environment source) {
        this.source = source;
        this.runtime = runtime;
    }

    /**
     * @param sentence 
     * @return
     */
    @Override
    public Expression build(String sentence) {
        return null;
    }
}
