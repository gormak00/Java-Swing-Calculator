package com.company.Controller;

import com.company.View.CalculatorFrame;
import com.company.View.TreePanel;

import javax.swing.*;

public class CalculatorPanelController {
    private StringBuffer expression;
    private Calculator calc = new Calculator();
    private int digit = 0;
    String result;

    public void actionBasicButtons(CalculatorFrame calculatorFrame, JFrame mainFrame, JTextField calculationLine, JButton b0, JButton b1, JButton b2, JButton b3, JButton b4, JButton b5, JButton b6, JButton b7, JButton b8, JButton b9, JButton bClear, JButton bDivision, JButton bInverse, JButton bLeftBracket, JButton bMinus, JButton bPercent, JButton bMulti, JButton bPlus, JButton bPoint, JButton bRes, JButton bRightBracket, JButton bSqrt, JButton bLeftArrow, JButton bRightArrow) {
        b0.addActionListener(e -> inputSimbol(calculationLine, b0));
        b1.addActionListener(e -> inputSimbol(calculationLine, b1));
        b2.addActionListener(e -> inputSimbol(calculationLine, b2));
        b3.addActionListener(e -> inputSimbol(calculationLine, b3));
        b4.addActionListener(e -> inputSimbol(calculationLine, b4));
        b5.addActionListener(e -> inputSimbol(calculationLine, b5));
        b6.addActionListener(e -> inputSimbol(calculationLine, b6));
        b7.addActionListener(e -> inputSimbol(calculationLine, b7));
        b8.addActionListener(e -> inputSimbol(calculationLine, b8));
        b9.addActionListener(e -> inputSimbol(calculationLine, b9));
        bClear.addActionListener(e -> clearAll(calculationLine, mainFrame, calculatorFrame));
        bDivision.addActionListener(e -> inputSimbol(calculationLine, bDivision));
        bLeftBracket.addActionListener(e -> inputSimbol(calculationLine, bLeftBracket));
        bRightBracket.addActionListener(e -> inputSimbol(calculationLine, bRightBracket));
        bMinus.addActionListener(e -> inputSimbol(calculationLine, bMinus));
        bPercent.addActionListener(e -> inputSimbol(calculationLine, bPercent));
        bMulti.addActionListener(e -> inputSimbol(calculationLine, bMulti));
        bPlus.addActionListener(e -> inputSimbol(calculationLine, bPlus));
        bPoint.addActionListener(e -> inputSimbol(calculationLine, bPoint));
        bInverse.addActionListener(e -> calculationLine.setText("(" + calculationLine.getText() + ")^-1"));
        bSqrt.addActionListener(e -> calculationLine.setText("(" + calculationLine.getText() + ")sqrt"));
        bLeftArrow.addActionListener(e -> {
            if (!calc.getStateList().isEmpty()) {
                if (digit != 0) digit--;
                calculationLine.setText(calc.getStateList().get(digit));
                updateFrame(mainFrame, calculatorFrame);
            }
        });
        bRightArrow.addActionListener(e -> {
            if (!calc.getStateList().isEmpty()) {
                if (calc.getTreeLists().size() != digit) digit++;
                if (digit == calc.getTreeLists().size()) {
                    calculationLine.setText(result);
                } else calculationLine.setText(calc.getStateList().get(digit));
                updateFrame(mainFrame, calculatorFrame);
            }
        });
        bRes.addActionListener(e -> {
            digit = 0;
            result = calc.readLine(calculationLine.getText());
            updateFrame(mainFrame, calculatorFrame);
        });
    }

    public void updateFrame(JFrame mainFrame, CalculatorFrame calculatorFrame) {
        mainFrame.remove(calculatorFrame.getTreePanel());
        calculatorFrame.setTreePanel(new TreePanel(result, calc.getTreeLists(), digit));
        mainFrame.add(calculatorFrame.getTreePanel());
        calculatorFrame.getTreePanel().updateUI();
    }

    private void clearAll(JTextField calculationLine, JFrame mainFrame, CalculatorFrame calculatorFrame) {
        calc.clearLists();
        calculationLine.setText(null);
        mainFrame.remove(calculatorFrame.getTreePanel());
        calculatorFrame.setTreePanel(new TreePanel("", null, 0));
        mainFrame.add(calculatorFrame.getTreePanel());
        calculatorFrame.getTreePanel().updateUI();
    }

    public void actionAdditionalButtons(JTextField calculationLine, JButton bLog, JButton bFact) {
        bLog.addActionListener(e -> calculationLine.setText("(," + calculationLine.getText() + ")log"));        //      Логарифм -> (основание, аргумент)log
        bFact.addActionListener(e -> calculationLine.setText("(" + calculationLine.getText() + ")!"));
    }

    public void actionRB(JRadioButton rbAdditionalFunctions, JPanel additionalPanel, JFrame mainFrame) {
        rbAdditionalFunctions.addActionListener(e -> {
            if (rbAdditionalFunctions.isSelected()) {
                mainFrame.add(additionalPanel);
                mainFrame.setSize(600, 430);
            } else if (!rbAdditionalFunctions.isSelected()) {
                mainFrame.remove(additionalPanel);
                mainFrame.setSize(600, 429);
            }
        });
    }

    private void inputSimbol(JTextField calculationLine, JButton button) {
        calculationLine.requestFocus();
        expression = new StringBuffer(calculationLine.getText());
        int idexOfCursor = calculationLine.getCaretPosition();
        expression.insert(idexOfCursor, button.getText());

        calculationLine.setText(String.valueOf(expression));
        calculationLine.setCaretPosition(idexOfCursor + 1);
    }
}