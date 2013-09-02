package com.iteye.wwwcomy;

/**
 * 迷惑性题目
 * 
 * @author Liuxn
 *
 */
public class TestStatic {
	static int x, y;
	static {
		int x = 5;
	}

	public static void main(String args[]) {
		System.out.println("x1=" + x);
		System.out.println("y1=" + y);
		x--;
		System.out.println("x2=" + x);
		System.out.println("y2=" + y);
		myMethod();
		System.out.println(x + y + ++x);
		System.out.println("x5=" + x);
		System.out.println("y5=" + y);
	}

	public static void myMethod() {
		System.out.println("x3=" + x);
		System.out.println("y3=" + y);
		y = x++ + ++x;
		System.out.println("x4=" + x);
		System.out.println("y4=" + y);
	}
}
