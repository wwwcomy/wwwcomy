package com.iteye.wwwcomy.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class InfixToPostfixExpression {
	private static Map<Character, Integer> OPERATOR_MAP;
	static {
		OPERATOR_MAP = new HashMap<>();
		OPERATOR_MAP.put('+', 3);
		OPERATOR_MAP.put('-', 3);
		OPERATOR_MAP.put('*', 7);
		OPERATOR_MAP.put('/', 7);
	}

	public static void main(String[] args) {
		System.out.println(getPostFixExpression("A+B-C"));
		// should be AB+C-
		assert "AB+C-".equals(getPostFixExpression("A+B-C"));

		System.out.println(getPostFixExpression("A*B/C"));
		// should be AB*C/
		assert "AB*C/".equals(getPostFixExpression("A*B/C"));

		System.out.println(getPostFixExpression("A+B*C"));
		// should be ABC*+
		assert "ABC*+".equals(getPostFixExpression("A+B*C"));

		System.out.println(getPostFixExpression("A*B+C"));
		// should be AB*C+
		assert "AB*C+".equals(getPostFixExpression("A*B+C"));

		System.out.println(getPostFixExpression("A*(B+C)"));
		// should be ABC+*
		assert "ABC+*".equals(getPostFixExpression("A*(B+C)"));

		System.out.println(getPostFixExpression("A*B+C*D"));
		// should be AB*CD*+
		assert "AB*CD*+".equals(getPostFixExpression("A*B+C*D"));

		System.out.println(getPostFixExpression("(A+B)*(C-D)"));
		// should be AB+CD-*
		assert "AB+CD-*".equals(getPostFixExpression("(A+B)*(C-D)"));

		System.out.println(getPostFixExpression("((A+B)*C)-D"));
		// should be AB+C*D-
		assert "AB+C*D-".equals(getPostFixExpression("((A+B)*C)-D"));

		System.out.println(getPostFixExpression("A+B*(C-D/(E+F))"));
		// should be ABCDEF+/-*+
		assert "ABCDEF+/-*+".equals(getPostFixExpression("A+B*(C-D/(E+F))"));

		System.out.println(calculatePostfixExpression("345+*612+/-"));
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
		// Pattern operatorPattern = Pattern.compile("[\\*\\+\\-\\/]");
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
			} else if (OPERATOR_MAP.containsKey(c)) {
				// System.out.println("an operator:" + c);
				if (s.isEmpty()) {
					s.push(c);
				} else {
					char tmp = s.peek();
					if (tmp == '(') {
						s.push(c);
					} else if (OPERATOR_MAP.containsKey(c)) {
						if (isHigherPriority(c, tmp)) {
							s.push(c);
						} else {
							sb.append(s.pop());
							s.push(c);
						}
					} else {
						System.err.println("inner operator...");
					}
				}
			} else {
				System.err.println("This is not handled!");
			}
		}
		while (!s.isEmpty()) {
			sb.append(s.pop());
		}
		return sb.toString();
	}

	private static boolean isHigherPriority(char tmp, char c) {
		return OPERATOR_MAP.get(tmp) > OPERATOR_MAP.get(c);
	}

	public static int calculatePostfixExpression(String expression) {
		Pattern numPattern = Pattern.compile("^[0-9]+$");
		Stack<String> s = new Stack<>();
		char[] chars = expression.toCharArray();
		for (char c : chars) {
			if (numPattern.matcher(String.valueOf(c)).matches()) {
				s.push(String.valueOf(c));
			} else if (OPERATOR_MAP.containsKey(c)) {
				int num1, num2;
				switch (c) {
				case '+':
					num1 = Integer.valueOf(s.pop());
					num2 = Integer.valueOf(s.pop());
					s.push(String.valueOf(num2 + num1));
					break;
				case '-':
					num1 = Integer.valueOf(s.pop());
					num2 = Integer.valueOf(s.pop());
					s.push(String.valueOf(num2 - num1));
					break;
				case '*':
					num1 = Integer.valueOf(s.pop());
					num2 = Integer.valueOf(s.pop());
					s.push(String.valueOf(num2 * num1));
					break;
				case '/':
					num1 = Integer.valueOf(s.pop());
					num2 = Integer.valueOf(s.pop());
					s.push(String.valueOf(num2 / num1));
					break;
				}
			} else {
				throw new UnsupportedOperationException();
			}
		}
		return Integer.valueOf(s.pop());
	}

}
