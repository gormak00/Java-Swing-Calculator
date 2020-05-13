package com.company.View;

import javax.swing.*;

public class CalculatorFrame {
    CalculatorPanel calculatorPanel = new CalculatorPanel();
    TreePanel treePanel = new TreePanel("", null, 0);
    private JFrame mainFrame = new JFrame("Calculator");

    public void initialize() {
        mainFrame.setLayout(null);
        mainFrame.add(calculatorPanel.createCalculatorPanel(mainFrame, this));
        mainFrame.add(treePanel);
        mainFrame.setSize(600, 429);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }

    public void setTreePanel(TreePanel treePanel) {
        this.treePanel = treePanel;
    }
}