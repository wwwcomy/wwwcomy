package com.iteye.wwwcomy.json;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StackTest {
	// private static final Character CCC = 127;

	public static void main(String[] args) throws Exception {

		List<String> l1 = new LinkedList<String>();
		l1.add("1");
		l1.add("2");
		l1.add("3");
		l1.add("4");
		l1.add(0, "5");
		System.out.println(l1);
		while (l1.size() > 0)
			System.out.println(l1.remove(0));
		System.out.println(l1.size());
	}

	@SuppressWarnings("unused")
	private static void testStack() {
		Stack<String> stack1 = new Stack<String>();
		stack1.push("1");
		stack1.push("2");
		stack1.push("3");
		stack1.push("4");
		System.out.println(stack1.toString());
		stack1.add("5");
		System.out.println(stack1.toString());
		System.out.println(stack1.pop());

	}
}
