/*
 * This source file was generated by the Gradle 'init' task
 */
package org.racketsimulator;

import org.racketsimulator.repl.REPL;

public class App {
    public static void main(String[] args) {
        System.out.println("RACKET SIMULATOR: Version 1.0 by Ion Dolanescu Bravo.");
        Configuration config = new Configuration();
        REPL driver = config.mainDriver();
        driver.run();
    }
}
