package com.iteye.wwwcomy.test;

import java.util.Stack;
import java.util.regex.Pattern;

public class InfixToPostfixExpression {
	public static void main(String[] args) {
		getPostFixExpression("A+B*(C-D)");
	}

	/**
	 * Converts the infix expression into a postfix expression, supported
	 * operators are '+-x/()'
	 * 
	 * @param infixExpression
	 *            the input infix expression
	 * @return the post fix expression
	 */
	public static String getPostFixExpression(String infixExpression) {
		Stack<Character> s = new Stack<>();
		StringBuilder sb = new StringBuilder();
		char[] chars = infixExpression.toCharArray();
		Pattern numCharPattern = Pattern.compile("^[A-Za-z0-9]+$");
		Pattern operatorPattern = Pattern.compile("[\\*\\+\\-\\/]");
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (numCharPattern.matcher(String.valueOf(c)).matches()) {
				// System.out.println("a number or a character:" + c);
				sb.append(c);
			} else if ('(' == c) {
				// System.out.println("a left bracket:" + c);
				s.push(c);
			} else if (')' == c) {
				// System.out.println("a right bracket:" + c);
				if (!s.isEmpty()) {
					char tmp = s.pop();
					while ('(' != tmp) {
						sb.append(tmp);
						tmp = s.pop();
					}
				}
			} else if (operatorPattern.matcher(String.valueOf(c)).matches()) {
				if (s.isEmpty()) {
					s.push(c);
				} else {
					
				}
				// System.out.println("an operator:" + c);
			} else {
				System.err.println("This is not handled!");
			}
		}
		return sb.toString();
	}

}
