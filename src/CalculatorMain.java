import CompClasses.ExpressionTree;

import java.util.*;

/**
 * CalculatorMain:
 * Implemented by Kaleb Coggins on
 * 2/19/2021
 * <p>
 * Puts into use the operations implementing Interfaces.IOperation.
 * Loops until no input is given.
 */

public class CalculatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int n = 0;
            float[] inputs = new float[2];
            String op = null;

            System.out.print(">> ");
            Scanner line = new Scanner(scanner.nextLine());

            // break loop if user gives no input
            if (!line.hasNext()) {
                break;
            }

            List<String> postfix = postFixExpression(line);

            for (String s : postfix) {
                System.out.print(s + " ");
            }

            System.out.println();

            ExpressionTree.Node expr = ExpressionTree.constructTree(postfix);

            System.out.println("ans = " + ExpressionTree.evaluateTree(expr));
        }
    }

    private static List<String> postFixExpression(Scanner line) {
        Stack<String> stack = new Stack<>();
        List<String> opList = new LinkedList<>();

        while (line.hasNext()) {
            String word = line.next();

            boolean num = false;
            try {
                float o = Float.parseFloat(word);
                num = true;
            } catch (NumberFormatException e) {
                // Do nothing
            }

            if (num) {
                opList.add(word);
            } else if (word.equals("(")) {
                stack.push("(");
            } else if (word.equals("^")) {
                stack.push("^");
            } else if (ExpressionTree.isUnaryOperation(word)) {
                stack.push(word);
            } else if (word.equals(")")) {
                while (!stack.peek().equals("(") && !stack.peek().equals(")")) {
                    opList.add(stack.pop());                // store and pop until ( has found
                }
                stack.pop();                                // remove the '(' from stack
            }else {
                if (stack.empty() || preced(word) > preced(stack.peek())) {
                    stack.add(word);                        // push if precedence is high
                } else {
                    while (!stack.isEmpty() && preced(word) <= preced(stack.peek())){
                        opList.add(stack.pop());            // store and pop until higher precedence is found
                    }
                    stack.push(word);
                }
            }
        }

        while (!stack.empty()) {
            opList.add(stack.pop());
        }

        return opList;
    }

    private static int preced(String op) {
        int p = -1;
        switch (op) {
            case "^":
                p = 3;
                break;
            case "*":
            case "/":
            case "%":
                p = 2;
                break;
            case "+":
            case "-":
                p = 1;
        }
        return p;
    }
}
