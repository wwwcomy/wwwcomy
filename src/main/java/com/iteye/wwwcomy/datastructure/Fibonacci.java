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

	static int fibonacciTailCall(int x, int y, int n) {
		// System.out.println("Method fibonacciTailCall called");
		if (n == 0) {
			return x;
		}

		return fibonacciTailCall(x + y, x, n - 1);
	}

	public static void main(String[] args) {
		System.out.println(fibonacci1(6));
		System.out.println(fibonacci2(6));
		System.out.println(fibonacciTailCall(1, 0, 6));
	}
}
