package org.racketsimulator.environment;

import org.racketsimulator.thowables.RacketSimError;

public class UsedSymbol extends RacketSimError {
    public UsedSymbol(String message) {
        super(message);
    }
}
