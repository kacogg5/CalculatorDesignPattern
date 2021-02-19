import java.util.Scanner;

/**
 * CalculatorMain:
 * Implemented by Kaleb Coggins on
 * 2/19/2021
 *
 * Puts into use the operations implementing IOperation.
 * Loops until no input is given.
 */

public class CalculatorMain {
    // Declare an array of IUnaryOperator Classes
    final static IUnaryOperation[] unaryOperations = {
            new SinOperation(),
            new CosOperation()
    };
    // Declare an array of IBinaryOperator Classes
    final static IBinaryOperation[] binaryOperations = {
            new AddOperation(),
            new SubOperation(),
            new MultOperation()
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while ( true ) {
            int n = 0;
            float[] inputs = new float[2];
            String op = null;

            System.out.print(">> ");
            Scanner line = new Scanner(scanner.nextLine());

            // break loop if user gives no input
            if (!line.hasNext()) { break; }

            // parse input for numbers / operations
            while ( line.hasNext() ) {
                String word = line.next();

                try {
                    // check if word is a number
                    float x = Float.parseFloat(word);
                    if (n < 2) {
                        inputs[n] = x;
                        n++;
                    } else {
                        System.out.println("Too Many operands!");
                    }
                } catch (NumberFormatException e) {
                    // if not a number, assume it is an operation
                    if (op == null) { op = word; }
                    else { System.out.println("Too many operations!"); }
                }
            }

            // perform operation
            boolean f = false;
            if (n == 0) { System.out.println("Not enough operands!"); }
            else if (op == null) { System.out.println("No operation specified!"); }
            else if (n == 1) {
                for (IUnaryOperation uOp : unaryOperations) {
                    if ( uOp.name().equals(op) ) {
                        System.out.println(uOp.calculate(inputs[0]));
                        f = true;
                        break;
                    }
                }
            } else {
                for (IBinaryOperation bOp : binaryOperations) {
                    if ( bOp.name().equals(op) ) {
                        System.out.println(bOp.calculate(inputs[0], inputs[1]));
                        f = true;
                        break;
                    }
                }
            }

            if (!f) { System.out.println("Operation not found."); }
        }
    }
}
