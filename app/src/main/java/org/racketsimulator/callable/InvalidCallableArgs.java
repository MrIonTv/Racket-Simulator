package org.racketsimulator.callable;

import org.racketsimulator.thowables.RacketSimError;

public class InvalidCallableArgs extends RacketSimError {
    public InvalidCallableArgs(String s) {
        super(s);
    }
}
