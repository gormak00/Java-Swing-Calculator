package com.company.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
    private List<List<String>> treeLists;
    private List<String> stateList;
    private String currentExpression;
    private StringBuilder cstring = new StringBuilder();//for user string storage
    private StringBuilder mainString;
    private boolean plusSign = true;//saving result sign
    private int closeBracket, openBracket;//saving start and finish substring positions
    private int finishPos = 0, startPos = 0;//saving start and finish expression part positions
    private boolean basicBracket = false;

    public List<List<String>> getTreeLists() {
        return treeLists;
    }

    public List<String> getStateList() {
        return stateList;
    }

    public String readLine(String line) {
        treeLists = new ArrayList();
        stateList = new ArrayList<>();
        stateList.add(line);
        calculate(line);
        if (plusSign) {
            System.out.println("Result: " + cstring);
        } else {
            cstring.insert(0, '-');
            System.out.println("Result: " + cstring);
        }
        plusSign = true;
        if (treeLists.get(treeLists.size() - 1).size() == 3) {
            return treeLists.get(treeLists.size() - 1).get(2);
        } else return treeLists.get(treeLists.size() - 1).get(3);
    }

    private int valueCalc(String input) {
        int slots = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '+') || (input.charAt(i) == '-') ||
                    (input.charAt(i) == '*') || (input.charAt(i) == '/') || (input.charAt(i) == '%') ||
                    (input.charAt(i) == ')') || (input.charAt(i) == '(')) {
                slots++;
            }
        }
        return slots;
    }

    private void calculate(String input) {
        int[] charsPos = new int[valueCalc(input) + 2];
        cstring.delete(0, cstring.length());
        cstring.append(input);

        checkBracket();
        for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == ',') {
                commaForLog(i);
                break;
            }
        }
        for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == '*') {
                mulDivPer(i, charsPos, '*');
                break;
            }
            if (cstring.charAt(i) == '/') {
                mulDivPer(i, charsPos, '/');
                break;
            }
            if (cstring.charAt(i) == '%') {
                mulDivPer(i, charsPos, '%');
                break;
            }

        }
        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '*') || (cstring.charAt(i) == '/') || (cstring.charAt(i) == '%')) {
                calculate(cstring.toString());
            }
        }

        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '+') && (plusSign)) {
                plusPlusSignTrue(i, charsPos);
                break;
            }
            if ((cstring.charAt(i) == '+') && (!plusSign)) {
                plusPlusSignFalse(i, charsPos);
                break;
            }
            if ((cstring.charAt(i) == '-') && (plusSign)) {
                minusPlusSignTrue(i, charsPos);
                break;
            }
            if ((cstring.charAt(i) == '-') && (!plusSign)) {
                minusPlusSignFalse(i, charsPos);
                break;
            }
        }
        for (int i = 0; i < cstring.length(); i++) {
            if ((cstring.charAt(i) == '+') || (cstring.charAt(i) == '-')) {
                calculate(cstring.toString());
            }
        }
    }

    private StringBuilder inverseFunction(StringBuilder number) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.pow(a, -1));
        treeLists.add(newList(a, "1/x", Float.parseFloat(number.toString())));
        return number;
    }

    private StringBuilder sqrtFunction(StringBuilder number) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.sqrt(a));
        treeLists.add(newList(a, "sqrt", Float.parseFloat(number.toString())));
        return number;
    }

    private StringBuilder logFunction(StringBuilder number) {
        float a = 0;
        float b = 0;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == ',') {
                a = Float.parseFloat(number.substring(0, i));
                b = Float.parseFloat(number.substring(i + 1, number.length()));
            }
        }
        float c = (float) (Math.log(b) / Math.log(a));
        number.delete(0, number.length());
        number.insert(0, c);
        treeLists.add(newList(a, "log", b, c));
        return number;
    }

    private StringBuilder factFunction(StringBuilder number) {
        float result = 1;
        float a = Float.parseFloat(number.toString());
        for (int i = 1; i <= a; i++) {
            result = result * i;
        }
        number.delete(0, number.length());
        number.insert(0, result);
        if (plusSign) {
            treeLists.add(newList(a, "fact!", Float.parseFloat(number.toString())));
        } else treeLists.add(newList(a * -1, "fact!", Float.parseFloat(number.toString())));
        return number;
    }

    private List<String> newList(float a, String sign, float b, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(b), String.valueOf(c));
        System.out.println(currentList);
        return currentList;
    }

    private List<String> newList(float a, String sign, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(c));
        System.out.println(currentList);
        return currentList;
    }

    private void checkExpression(String line, String currentExpression, String newLine) {
        if (basicBracket) {
            try {
                Float.parseFloat(line);
            } catch (Exception e) {
                stateList.add(stateList.get(stateList.size() - 1).replace(currentExpression, newLine));
            }
        } else stateList.add(stateList.get(stateList.size() - 1).replace(currentExpression, newLine));
    }

    private void checkBracket() {
        for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == '(') {
                openBracket = i;
            }
            if (cstring.charAt(i) == 's') {
                basicBracket = false;
                calculateSubString(i, 3, 4);
                sqrtFunction(cstring);
                updateLine(mainString);
            } else if (cstring.charAt(i) == '^') {
                basicBracket = false;
                calculateSubString(i, 2, 3);
                inverseFunction(cstring);
                updateLine(mainString);
            } else if (cstring.charAt(i) == 'l') {
                basicBracket = false;
                calculateSubString(i, 2, 3);
                logFunction(cstring);
                updateLine(mainString);
            } else if (cstring.charAt(i) == '!') {
                basicBracket = false;
                calculateSubString(i, 0, 1);
                factFunction(cstring);
                updateLine(mainString);
            } else if (cstring.charAt(i) == ')' && (i == cstring.length() - 1 || (cstring.charAt(i + 1) != 's' && cstring.charAt(i + 1) != '^' && cstring.charAt(i + 1) != 'l' && cstring.charAt(i + 1) != '!')))//for locating max priority
            {
                basicBracket = true;
                calculateSubString(i, 0, 0);
                updateLine(mainString);
            }
        }
    }

    private void commaForLog(int i) {
        StringBuilder mainString = new StringBuilder(cstring);
        System.out.println(cstring.substring(i + 1, cstring.length()));
        mainString.delete(i + 1, cstring.length());
        calculate(cstring.substring(i + 1, cstring.length()));
        mainString.insert(i + 1, cstring.toString());
        cstring.delete(0, cstring.length());
        cstring.insert(0, mainString.toString());
        mainString.delete(0, i);
        calculate(cstring.substring(0, i));
        mainString.insert(0, cstring.toString());
        cstring.delete(0, cstring.length());
        cstring.insert(0, mainString.toString());
    }

    private void plusPlusSignTrue(int i, int[] charsPos) {
        float a = 0, b = 0;
        Float c = null;
        findExpression(i, charsPos);
        a = Float.parseFloat(cstring.substring(startPos, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        currentExpression = cstring.substring(startPos, finishPos);
        cstring.delete(startPos, finishPos);
        c = a + b;
        treeLists.add(newList(a, "+", b, c));
        cstring.insert(startPos, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
    }

    private void plusPlusSignFalse(int i, int[] charsPos) {
        float a = 0, b = 0;
        Float c = null;
        findExpression(i, charsPos);
        a = Float.parseFloat(cstring.substring(0, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        currentExpression = cstring.substring(startPos, finishPos);
        cstring.delete(0, finishPos);
        if (b > a) {
            c = b - a;
            treeLists.add(newList(a * -1, "+", b, c));
            plusSign = true;
        } else {
            c = a - b;
            treeLists.add(newList(a * -1, "+", b, c * -1));
        }
        cstring.insert(0, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
    }

    private void minusPlusSignTrue(int i, int[] charsPos) {
        float a = 0, b = 0;
        Float c = null;
        findExpression(i, charsPos);
        a = Float.parseFloat(cstring.substring(0, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        currentExpression = cstring.substring(startPos, finishPos);
        cstring.delete(0, finishPos);
        if (b > a) {
            c = b - a;
            treeLists.add(newList(a, "-", b, c * -1));
            plusSign = false;
        } else {
            c = a - b;
            treeLists.add(newList(a, "-", b, c));
        }
        cstring.insert(0, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
    }

    private void minusPlusSignFalse(int i, int[] charsPos) {
        float a = 0, b = 0;
        Float c = null;
        findExpression(i, charsPos);
        a = Float.parseFloat(cstring.substring(0, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        cstring.delete(0, finishPos);
        c = a + b;
        treeLists.add(newList(a * -1, "-", b, c * -1));
        cstring.insert(0, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
    }

    private void mulDivPer(int i, int[] charsPos, char sign) {
        float a = 0, b = 0;
        Float c = null;
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < cstring.length() - 1; j++) {
            if ((cstring.charAt(j) == '+') || (cstring.charAt(j) == '-') ||
                    (cstring.charAt(j) == '*') || (cstring.charAt(j) == '/') || (cstring.charAt(j) == '%') ||
                    (cstring.charAt(j) == '(') || (cstring.charAt(j) == ')')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = cstring.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }

        a = Float.parseFloat(cstring.substring(startPos, i));
        b = Float.parseFloat(cstring.substring(i + 1, finishPos));
        currentExpression = cstring.substring(startPos, finishPos);
        cstring.delete(startPos, finishPos);
        if (sign == '*') {
            c = a * b;
            treeLists.add(newList(a, "*", b, c));
        } else if (sign == '/') {
            c = a / b;
            treeLists.add(newList(a, "/", b, c));
        } else if (sign == '%') {
            c = a / 100 * b;
            treeLists.add(newList(a, "%", b, c));
        }
        cstring.insert(startPos, c.toString());
        checkExpression(cstring.toString(), currentExpression, c.toString());
        calculate(cstring.toString());
    }

    private void findExpression(int i, int[] charsPos) {
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < cstring.length() - 1; j++) {
            if ((cstring.charAt(j) == '+') || (cstring.charAt(j) == '-') ||
                    (cstring.charAt(j) == '*') || (cstring.charAt(j) == '/') || (cstring.charAt(j) == '%') ||
                    (cstring.charAt(j) == '(') || (cstring.charAt(j) == ')')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = cstring.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }
    }

    private void calculateSubString(int i, int a, int b) {
        closeBracket = i + a;
        String subString = "";
        subString = cstring.substring(openBracket + 1, closeBracket - b);
        cstring.delete(openBracket, closeBracket + 1);
        mainString = new StringBuilder(cstring);
        calculate(subString);
    }

    private void updateLine(StringBuilder mainString) {
        mainString.insert(openBracket, cstring.toString());
        cstring.delete(0, cstring.length());
        cstring.insert(0, mainString.toString());
        stateList.add(cstring.toString());
        calculate(cstring.toString());
    }

    public void clearLists() {
        treeLists.clear();
        stateList.clear();
    }
}