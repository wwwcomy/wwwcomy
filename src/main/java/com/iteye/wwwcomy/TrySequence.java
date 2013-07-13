package com.iteye.wwwcomy;

/**
 * try catch finally顺序
 * 
 * @author wwwcomy
 * 
 */
public class TrySequence {

	public static int a = 0;

	public static void main(String[] args) {
		new TrySequence().test();
	}

	private Object test() {
		try {
			a++;
			System.out.println(a);
			return test1();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			a++;
			System.out.println("Finally a = " + a);
		}
		return null;
	}

	private Object test1() {
		a++;
		System.out.println("Test1 a = " + a);
		return null;
	}

}
