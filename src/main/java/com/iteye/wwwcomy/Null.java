package com.iteye.wwwcomy;

/**
 * 空对象可以调用静态方法，会有个静态调用的警告，但是程序不会报错
 * 
 * @author Liuxn
 * 
 */
public class Null {

	public static void greet() {
		System.out.println("Hello World");
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		((Null) null).greet();
	}

}
