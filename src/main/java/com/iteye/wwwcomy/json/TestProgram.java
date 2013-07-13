package com.iteye.wwwcomy.json;

public class TestProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Number num = 12345 + 5432L;
		System.out.println(num.intValue());
	}

	@SuppressWarnings("unused")
	private static void invoke(Object obj) {
		System.out.println("Object");
	}

	@SuppressWarnings("unused")
	private static void invoke(int[] nums) {
		System.out.println("Arrays");
	}

	public static void test1() {
		int m = 5, n = 5;
		if ((m != 5) && (n++ == 5)) {
			System.out.println("a." + n);
		}

		m = n = 5;
		if ((m != 5) & (n++ == 6)) {
			System.out.println("b." + n);
		}

		m = n = 5;
		if ((m == 5) || (n++ == 5)) {
			System.out.println("c." + n);
		}

		m = n = 5;
		if ((m == 5) | (n++ == 6)) {
			System.out.println("d." + n);
		}
	}

	public static void test2() {
		int k = 100;
		Integer int1 = k;
		Integer int2 = k;
		System.out.println("a." + (int1 == int2));
		k = 200;
		Integer int3 = k;
		Integer int4 = k;
		System.out.println("b." + (int3 == int4));

		char c = 'A';
		Character char1 = c;
		Character char2 = c;
		System.out.println("c." + (char1 == char2));
		c = '擦';
		Character char3 = c;
		Character char4 = c;
		System.out.println("d." + (char3 == char4));
	}
}
