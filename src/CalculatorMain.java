import CompClasses.ExpressionTree;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalculatorMain:
 * Implemented by Kaleb Coggins on
 * 2/19/2021
 * <p>
 * Puts into use the operations implementing Interfaces.IOperation.
 * Loops until no input is given.
 */

public class CalculatorMain {
    private static boolean windowExists = false;
    private final JFrame application;
    private final GridBagConstraints appGBC;
    private final JPanel display;

    private String userExpression;
    private String actualExpression;
    private boolean lastInputIsNumber;
    private boolean lastWordHasDot;
    private boolean lastInputNegative;
    private int parenLvl = 0;
    private int binMode = 0;

    public static void main(String[] args) throws InstanceAlreadyExistsException {
        CalculatorMain calculator = new CalculatorMain();
    }

    public CalculatorMain() throws InstanceAlreadyExistsException {
        if (windowExists) { throw new InstanceAlreadyExistsException(); }
        windowExists = true;
        this.userExpression = this.actualExpression = "";
        this.lastInputIsNumber = false;
        this.lastWordHasDot = false;
        this.lastInputNegative = false;

        application = new JFrame("Calculator");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setMinimumSize(new Dimension(300, 450));
        application.setLayout(new GridBagLayout());
        application.setResizable(false);

        appGBC = new GridBagConstraints();
        appGBC.fill = GridBagConstraints.BOTH;

        this.display = new JPanel(new GridBagLayout());
        this.display.setBackground(Color.CYAN);
        this.appGBC.gridx = 0;
        this.appGBC.gridy = 0;
        this.appGBC.gridwidth = 5;
        this.appGBC.gridheight = 2;
        application.add(this.display, this.appGBC);

        this.calculatorDisplay();
        this.clearButtonRow();
        this.trigFuncButtons();
        this.leftPanelButtons();
        this.numPadButtons();
        this.rightPanelButtons();
        this.binModeButtons();

        application.setVisible(true);
    }

    private class InputActionListener implements ActionListener {
        private final String name;
        private final boolean isNum;

        public InputActionListener(String name, boolean isNumber) {
            this.name = name;
            this.isNum = isNumber;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buildExpression(name, isNum);
        }
    }

    private class ResultActionListener implements ActionListener {
        public ResultActionListener() { }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateResult();
        }
    }

    private class BinaryActionListener implements ActionListener {
        private final int mode;

        public BinaryActionListener(int mode) {
            this.mode = mode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateBinaryMode(this.mode);
        }
    }

    private class ClearActionListener implements ActionListener {
        public ClearActionListener() {}

        @Override
        public void actionPerformed(ActionEvent e) {
            clearExpression();
        }
    }

    private void calculatorDisplay() {
        GridBagConstraints c = new GridBagConstraints();

        JLabel expressionDisplay = new JLabel(" ");
        expressionDisplay.setPreferredSize(new Dimension(300, 50));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        this.display.add(expressionDisplay, c);

        JLabel resultDisplay = new JLabel(" ");
        resultDisplay.setPreferredSize(new Dimension(300, 50));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.gridheight = 1;
        this.display.add(resultDisplay, c);

        JLabel binaryDisplay = new JLabel(" ");
        binaryDisplay.setPreferredSize(new Dimension(300, 50));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        c.gridheight = 1;
        this.display.add(binaryDisplay, c);
    }

    private void numPadButtons() {
        JPanel numpad = new JPanel(new GridBagLayout());
        numpad.setPreferredSize(new Dimension(180, 200));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        JButton[] numButtons = new JButton[12];

        numButtons[0] = new JButton(".");
        numButtons[0].addActionListener(new InputActionListener(".", true));
        c.gridx = 0;
        c.gridy = 3;
        numpad.add(numButtons[0], c);

        numButtons[1] = new JButton("0");
        numButtons[1].addActionListener(new InputActionListener("0", true));
        c.gridx = 1;
        c.gridy = 3;
        numpad.add(numButtons[1], c);

        numButtons[2] = new JButton("-/+");
        numButtons[2].addActionListener(new InputActionListener("!", false));
        c.gridx = 2;
        c.gridy = 3;
        numpad.add(numButtons[2], c);

        for (int i = 1; i < 10; i++) {
            numButtons[i+2] = new JButton(""+i);

            numButtons[i+2].addActionListener(new InputActionListener(""+i, true));
            c.gridx = (i - 1) % 3;
            c.gridy = 2 - (i - 1) / 3;
            numpad.add(numButtons[i+2], c);
        }

        this.appGBC.gridx = 1;
        this.appGBC.gridy = 5;
        this.appGBC.gridwidth = 3;
        this.appGBC.gridheight = 4;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(numpad, this.appGBC);
    }

    private void rightPanelButtons() {
        JPanel rightOpPad = new JPanel(new GridBagLayout());
        rightOpPad.setMinimumSize(new Dimension(60, 300));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

//        JButton delBtn = new JButton("del");
//        delBtn.addActionListener(new ClearActionListener());
//        c.gridx = 0;
//        c.gridy = 0;
//        rightOpPad.add(delBtn, c);

        JButton addBtn = new JButton("+");
        addBtn.addActionListener(new InputActionListener("+", false));
        c.gridx = 0;
        c.gridy = 4;
        rightOpPad.add(addBtn, c);

        JButton subBtn = new JButton("-");
        subBtn.addActionListener(new InputActionListener("-", false));
        c.gridx = 0;
        c.gridy = 3;
        rightOpPad.add(subBtn, c);

        JButton multBtn = new JButton("x");
        multBtn.addActionListener(new InputActionListener("*", false));
        c.gridx = 0;
        c.gridy = 2;
        rightOpPad.add(multBtn, c);

        JButton divBtn = new JButton("/");
        divBtn.addActionListener(new InputActionListener("/", false));
        c.gridx = 0;
        c.gridy = 1;
        rightOpPad.add(divBtn, c);

        JButton eqBtn = new JButton("=");
        eqBtn.addActionListener(new ResultActionListener());
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        rightOpPad.add(eqBtn, c);

        this.appGBC.gridx = 4;
        this.appGBC.gridy = 3;
        this.appGBC.gridwidth = 1;
        this.appGBC.gridheight = 6;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(rightOpPad, this.appGBC);
    }

    private void trigFuncButtons() {
        JPanel trigBtns = new JPanel(new GridBagLayout());
        trigBtns.setMinimumSize(new Dimension(180, 100));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        int i = 0;
        for (String s : new String[] {"acos", "asin", "atan", "cos", "sin", "tan"}) {
            JButton btn = new JButton(s);
            btn.addActionListener(new InputActionListener(s, false));
            c.gridx = i % 3;
            c.gridy = i / 3;
            c.gridwidth = 1;
            c.gridheight = 1;
            trigBtns.add(btn, c);
            i++;
        }

        this.appGBC.gridx = 1;
        this.appGBC.gridy = 3;
        this.appGBC.gridwidth = 3;
        this.appGBC.gridheight = 2;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(trigBtns, this.appGBC);
    }

    private void clearButtonRow() {
        JPanel clrBtnRow = new JPanel(new GridBagLayout());
        clrBtnRow.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        JButton delBtn = new JButton("DEL");
        delBtn.addActionListener(new ClearActionListener());
        c.gridx = 0;
        c.gridy = 0;
        clrBtnRow.add(delBtn, c);

        int i = 1;
        for (String s : new String[] {"pi", "e", "(", ")"}) {
            JButton btn = new JButton(s);
            btn.addActionListener(new InputActionListener(s, false));
            c.gridx = i;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            clrBtnRow.add(btn, c);
            i++;
        }

        this.appGBC.gridx = 0;
        this.appGBC.gridy = 2;
        this.appGBC.gridwidth = 5;
        this.appGBC.gridheight = 1;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(clrBtnRow, this.appGBC);
    }

    private void leftPanelButtons() {
        JPanel lftBtns = new JPanel(new GridBagLayout());
        lftBtns.setMinimumSize(new Dimension(60, 300));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        int i = 0;
        for (String s : new String[] {"log", "ln", "%", "xrt", "sqrt", "^"}) {
            JButton btn = new JButton(s);
            btn.addActionListener(new InputActionListener(s, false));
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            lftBtns.add(btn, c);
            i++;
        }

        this.appGBC.gridx = 0;
        this.appGBC.gridy = 3;
        this.appGBC.gridwidth = 1;
        this.appGBC.gridheight = 6;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(lftBtns, this.appGBC);
    }

    private void binModeButtons() {
        JPanel binBtns = new JPanel(new GridBagLayout());
        binBtns.setMinimumSize(new Dimension(300, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        int i = 0;
        for (String s : new String[] {"Bin", "Oct", "Hex"}) {
            JButton btn = new JButton(s);
            btn.addActionListener(new BinaryActionListener(i));
            c.gridx = i;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            binBtns.add(btn, c);
            i++;
        }

        this.appGBC.gridx = 0;
        this.appGBC.gridy = 9;
        this.appGBC.gridwidth = 5;
        this.appGBC.gridheight = 1;
        this.appGBC.fill = GridBagConstraints.BOTH;
        this.application.add(binBtns, this.appGBC);
    }

    private void buildExpression(String input, boolean isNum) {
        if (lastWordHasDot && input.equals(".")) {
            System.out.print(""); // do nothing
        } else if (input.equals(".") && !lastInputIsNumber) {
            userExpression += "0.";
            actualExpression += "0.";
            lastInputIsNumber = true;
            lastWordHasDot = true;
        } else if (isNum) {
            userExpression += input;
            actualExpression += input;
            lastInputIsNumber = true;
            if (input.equals(".")) { lastWordHasDot = true; }
        } else if (ExpressionTree.isConstant(input)) {
            userExpression += input + " ";
            actualExpression += input + " ";
            lastInputIsNumber = true;
            lastWordHasDot = true;
        } else {
            if (actualExpression.length() > 0 &&
                    actualExpression.charAt(actualExpression.length() - 1) == '.') {
                userExpression += "0";
                actualExpression += "0";
                lastWordHasDot = false;
                lastInputIsNumber = true;
            }

            if (lastInputIsNumber) {
                actualExpression += " ";
                lastInputIsNumber = false;
            }

            if (input.equals("!")) {
                userExpression += " -";
                actualExpression += "! ";
                lastInputIsNumber = false;
            } else if (ExpressionTree.isUnaryOperation(input)) {
                userExpression += input + "(";
                actualExpression += input + " ( ";
                lastInputIsNumber = false;
                parenLvl++;
            } else if (input.equals("(")) {
                userExpression += "(";
                actualExpression += "( ";
                lastInputIsNumber = false;
                parenLvl++;
            } else if (input.equals(")")) {
                userExpression += ")";
                actualExpression += ") ";
                lastInputIsNumber = true;
                parenLvl--;
            } else {
                userExpression += input;
                actualExpression += input + " ";
                lastInputIsNumber = false;
            }

            lastWordHasDot = false;
            lastInputNegative = false;
        }

        updateDisplayExpression();
    }

    private String getBinString(float toConvert) {
        int temp = toConvert % 1 == 0 ? (int)toConvert : Float.floatToIntBits(toConvert);
        return switch (this.binMode) {
            case (1) -> ("8x"+Integer.toOctalString(temp));
            case (2) -> ("16x"+Integer.toHexString(temp));
            default ->  ("2x"+Integer.toBinaryString(temp));
        };
    }

    public void clearExpression() {
        userExpression = "";
        actualExpression = "";
        lastWordHasDot = false;
        lastInputIsNumber = false;
        lastInputNegative = false;
        parenLvl = 0;

        updateDisplayExpression();
        updateDisplayResult("");
        updateBinaryResult("");
    }

    private void updateBinaryMode(int mode) {
        this.binMode = mode;
        this.updateResult();
    }

    private void updateDisplayExpression() {
        String text = this.userExpression;
        if (text.equals("")) { text = " "; }
        ((JLabel)this.display.getComponent(0)).setText(text);
    }

    private void updateResult() {
        if (this.parenLvl != 0 ||
                !this.lastInputIsNumber) {
            updateDisplayResult("Err: Bad Syntax");
        } else {
            try {
                float result = ExpressionFormatting.solveExpression(actualExpression);

                if (result == 42) {
                    updateDisplayResult("You win!");
                } else if (!(result == Float.POSITIVE_INFINITY ||
                        result == Float.NEGATIVE_INFINITY)) {

                    String resultStr =
                            (result >= Math.pow(10, 10) ||
                                    result < Math.pow(10, -10)) ?
                            String.format("%6.3e", result): "" + result;

                    String binStr = getBinString( result );

                    updateBinaryResult(binStr);
                    updateDisplayResult(resultStr);
                } else {
                    updateDisplayResult("Err: Div By 0");
                }
            } catch (ArithmeticException ae) {
                updateDisplayResult("Err: Div By 0");
            } catch (Exception e) {
                updateDisplayResult("Err: Unexpected!");
            }
        }
    }

    private void updateDisplayResult(String toDisplay) {
        ((JLabel)this.display.getComponent(1)).setText("= " + toDisplay);
    }

    private void updateBinaryResult(String toDisplay) {
        ((JLabel)this.display.getComponent(2)).setText("= " + toDisplay);
    }
}
