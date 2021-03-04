package CompClasses;

import Interfaces.IBinaryOperation;
import Interfaces.IConstant;
import Interfaces.IUnaryOperation;
import StratClasses.*;

import java.util.List;
import java.util.Stack;

public class ExpressionTree {

    // Java program for expression tree
    public static class Node {

        String value;
        Node left, right;

        Node(String item) {
            value = item;
            left = right = null;
        }
    }

    // A utility function to check if 'c'
    // is an operator

    // -- Do not include in report
    static boolean isOperator(String c) {
        for (IUnaryOperation op: unaryOperations) {
            if (op.name().equals(c)) { return true; }
        }
        for (IBinaryOperation op: binaryOperations) {
            if (op.name().equals(c)) { return true; }
        }
        return false;
    }

    public static boolean isConstant(String op) {
        for (IConstant c: constants) {
            if (c.name().equals(op)) { return true; }
        }
        return false;
    }

    public static boolean isUnaryOperation(String op) {
        for (IUnaryOperation opc: unaryOperations) {
            if (opc.name().equals(op)) { return true; }
        }
        return false;
    }

    public static boolean isBinaryOperation(String op) {
        for (IBinaryOperation opc: binaryOperations) {
            if (opc.name().equals(op)) { return true; }
        }
        return false;
    }

    // Returns root of constructed tree for given
    // postfix expression
    public static Node constructTree(List<String> postfix) {
        Stack<Node> st = new Stack<Node>();
        Node t, t1, t2;

        // Traverse through every character of
        // input expression
        for (String word : postfix) {

            // If operand, simply push into stack
            if ((!isOperator(word)) || isConstant(word)) {
                t = new Node(word);
                st.push(t);
            } else if (isUnaryOperation(word)) {
                t = new Node(word);

                // Pop one top node
                // Store top
                t1 = st.pop();      // Remove top

                //  make them children
                t.right = t1;

                // Add this subexpression to stack
                st.push(t);
            } else {
                t = new Node(word);

                // Pop two top nodes
                // Store top
                t1 = st.pop();      // Remove top
                t2 = st.pop();

                //  make them children
                t.right = t1;
                t.left = t2;

                // Add this subexpression to stack
                st.push(t);
            }
        }

        //  only element will be root of expression
        // tree
        t = st.peek();
        st.pop();

        if (!st.empty()) {
            throw new IllegalArgumentException("AHHHHHHHHHHHHHHHHHHH");
        }

        return t;
    }

    // Code beyond this point are for evaluation of a ExpressionTree

    // Declare an array of IUnaryOperator Classes
    final static IUnaryOperation[] unaryOperations = {
            new SinOperation(),
            new AsinOperation(),
            new CosOperation(),
            new AcosOperation(),
            new TanOperation(),
            new AtanOperation(),
            new SqrtOperation(),
            new NegativeOperation(),
            new LogOperation(),
            new LnOperation()
    };
    // Declare an array of IBinaryOperator Classes
    final static IBinaryOperation[] binaryOperations = {
            new AddOperation(),
            new SubOperation(),
            new MultOperation(),
            new DivOperation(),
            new ModOperation(),
            new PowOperation(),
            new XrtOperation()
    };
    // Declare an array of IConstant Classes
    final static IConstant[] constants = {
            new PiConstant(),
            new EConstant()
    };


    public static float evaluateTree(Node n) {
        if (isOperator(n.value)) {
            if (n.left == null || n.right == null) {
                Node toUse = (n.left == null) ? n.right : n.left;
                for (IUnaryOperation op: unaryOperations) {
                    if (op.name().equals(n.value)) { return op.calculate(evaluateTree(toUse)); }
                }
            } else {
                for (IBinaryOperation op: binaryOperations) {
                    if (op.name().equals(n.value)) { return op.calculate(evaluateTree(n.left), evaluateTree(n.right)); }
                }
            }

            throw new IllegalArgumentException("Operator not found!");
        } else {
            for (IConstant c: constants) {
                if (c.name().equals(n.value)) { return c.calculate(); }
            }

            try { return Float.parseFloat(n.value); }
            catch (NumberFormatException e) { throw new IllegalArgumentException("Unknown symbol " + n.value); }
        }
    }
}
