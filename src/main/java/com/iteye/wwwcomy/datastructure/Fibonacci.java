package com.iteye.wwwcomy.datastructure;

/**
 * Easy to write Fibonacci or use cache, starting to learn "Tail Call"
 * 
 * @see http://www.ruanyifeng.com/blog/2015/04/tail-call.html
 * 
 * @author wwwcomy
 *
 */
public class Fibonacci {

	static int fibonacci1(int x) {
		// System.out.println("Method 1 called");
		if (x <= 2) {
			return 1;
		}
		return fibonacci1(x - 1) + fibonacci1(x - 2);
	}

	static int[] CACHE = new int[1024];

	static {
		CACHE[0] = 1;
		CACHE[1] = 1;
	}

	static int fibonacci2(int x) {
		// System.out.println("Method 2 called");
		if (x <= 2 || CACHE[x - 1] != 0) {
			return CACHE[x - 1];
		}
		CACHE[x - 1] = fibonacci2(x - 1) + fibonacci2(x - 2);
		return CACHE[x - 1];
	}

	/**
	 * This is a very tricky way of changing the loop to a recursive pattern,
	 * and of cause, this is a tail call.
	 * 
	 * @param x
	 * @param y
	 * @param n
	 * @return
	 */
	static int fibonacciTailCall(int x, int y, int n) {
		// System.out.println("Method fibonacciTailCall called");
		if (n == 1) {
			return x;
		}

		return fibonacciTailCall(x + y, x, n - 1);
	}

	static int fibonacciUseWhile(int x) {
		if (x < 3) {
			return 1;
		}
		int num1 = 1;
		int num2 = 1;
		int num3 = 0;
		while (x > 2) {
			num3 = num2;
			num2 = num1 + num2;
			num1 = num3;
			x--;
		}
		return num2;
	}

	public static void main(String[] args) {
		System.out.println(fibonacci1(6));
		System.out.println(fibonacci2(6));
		System.out.println(fibonacciTailCall(1, 0, 6));
		System.out.println(fibonacciUseWhile(6));
	}
}
