package com.company.View;

import com.company.Controller.TreeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TreePanel extends JPanel {
    private JTextField resultLine = new JTextField();

    public TreePanel(String result, List<List<String>> treeLists, int digit) {
        setLayout(null);
        setLocation(360, 5);
        setSize(245, 325);
        resultLine.setFont(new Font("Dialog", Font.PLAIN, 25));
        resultLine.setText(result);
        JScrollPane treeScroll = new JScrollPane(TreeController.generateTree(treeLists, digit));
        treeScroll.setBounds(0, 60, 240, 265);
        JScrollPane resultLineScroll = new JScrollPane(resultLine);
        resultLineScroll.setBounds(0, 5, 240, 55);
        add(treeScroll);
        add(resultLineScroll);
    }
}