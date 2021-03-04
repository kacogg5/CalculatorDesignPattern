import CompClasses.ExpressionTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ExpressionFormatting {
    public static float solveExpression(String expression) {
        Scanner scanner = new Scanner(expression);
        List<String> postfix = postFixExpression(scanner);

        ExpressionTree.Node expr = ExpressionTree.constructTree(postfix);
        return ExpressionTree.evaluateTree(expr);
    }

    // input : sqrt ( 2| ) + 3
    // stack : sqrt (
    // result: 2
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

            if (num || ExpressionTree.isConstant(word)) {
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

                while (!stack.empty() && ExpressionTree.isUnaryOperation(stack.peek())) {
                    opList.add(stack.pop());
                }
            } else {
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
