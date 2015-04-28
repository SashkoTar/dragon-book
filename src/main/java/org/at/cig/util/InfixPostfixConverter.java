package org.at.cig.util;

import java.util.Stack;

// This class converts infix expression to postfix
public class InfixPostfixConverter {

    Stack<Character> stack = new Stack<Character>();
    StringBuilder result = new StringBuilder();

    public String handle(String inputString) {
        String s = makeStringInfixed(inputString);
        for(int i=0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                default:
                    result.append(s.charAt(i));
                    break;
                case '(':
                    handleLeftParenthesis();
                    break;
                case ')':
                    handleRightParenthesis();
                    break;
                case '*':
                    handleStar();
                    break;
                case '.':
                    handleCat();
                    break;
                case '|':
                    handleOr();
                    break;
            }
        }
        releaseStack();
        return result.toString();
    }



    private String makeStringInfixed(String s) {
        StringBuilder result = new StringBuilder();
        char currentChar;
        for(int i = 0; i < s.length(); i++) {
            currentChar = s.charAt(i);
            result.append(currentChar);
            if(isCurrentCharOperand(currentChar) && isNextCharOperand(s, i)){
                result.append('.');
            }
        }
        return result.toString();
    }

    private boolean isCurrentCharOperand(char s) {
        return "(.|*".indexOf(s) < 0;
    }

    private boolean isNextCharOperand(String s, int i) {
        i++;
        if(i < s.length()) {
            char nextSymbol = s.charAt(i);
            return ").|*".indexOf(nextSymbol) < 0;
        }
        return false;
    }

    private void handleLeftParenthesis() {
        stack.push('(');
    }

    private void handleRightParenthesis() {
        while (!stack.empty()) {
            if(stack.peek() == '(') {
                stack.pop();
                break;
            }
            result.append(stack.pop());
        }
    }

    private void handleStar() {
        while (!stack.empty()) {
            if(stack.peek() == '|' || stack.peek() == '(' || stack.peek() =='.') {
                break;
            }
            result.append(stack.pop());
        }
        stack.push('*');
    }

    private void handleCat() {
        while (!stack.empty()) {
            if(stack.peek() == '|' || stack.peek() == '(') {
                break;
            }
            result.append(stack.pop());
        }
        stack.push('.');
    }


    private void handleOr() {
        releaseStack();
        stack.push('|');
    }

    private void releaseStack() {
        while (!stack.empty()) {
            if(stack.peek() == '(') {
                break;
            }
            result.append(stack.pop());
        }
    }
}
