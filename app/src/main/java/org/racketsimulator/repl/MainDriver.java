package org.racketsimulator.repl;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;
import org.racketsimulator.thowables.RacketSimError;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDriver implements REPL {
    public static final String INPUT_SYMBOL = "> ";
    public static final char OPEN_SYMBOL = '(';
    public static final char CLOSE_SYMBOL = ')';
    private static final String TAB = "  ";
    private final ExpressionBuilder parser;

    public MainDriver(ExpressionBuilder parser) {
        this.parser = parser;
    }

    @Override
    public void run() {
        boolean continueReading = true;
        Scanner scanner = new Scanner(System.in);

        while (continueReading) {
            try {
                DriverInput result = read(scanner);
                continueReading = result.continueReading();

                if (!result.lines().isEmpty()) {
                    Expression expression = parser.build(result.lines());
                    System.out.println(expression.evaluate().stringContent());
                }

            } catch (RacketSimError e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Simulator Turned Off.");
    }

    private DriverInput read(Scanner scanner) {
        int balance = 0;
        List<String> lines = new ArrayList<>();
        boolean isBalanced = false;

        System.out.print(INPUT_SYMBOL);
        String line = scanner.nextLine();

        if (line == null || line.isBlank()) {
            return new DriverInput("", false);
        }

        while (line != null && !isBalanced) {
            lines.add(line);

            for (char ch : line.toCharArray()) {
                switch (ch) {
                    case OPEN_SYMBOL -> balance++;
                    case CLOSE_SYMBOL -> balance--;
                }

                if (balance < 0) {
                    throw new SyntaxError("You can't close an expression without opening. Buffer has been cleaned.");
                }
            }

            if (balance == 0) {
                isBalanced = true;
            }

            if (!isBalanced) {
                System.out.print(TAB + TAB.repeat(balance));
                line = scanner.nextLine();
            }

            if ((line == null || line.isBlank()) && isBalanced) {
                return new DriverInput("", false);
            }
        }

        return new DriverInput(String.join("\n", lines), line != null);
    }
}
