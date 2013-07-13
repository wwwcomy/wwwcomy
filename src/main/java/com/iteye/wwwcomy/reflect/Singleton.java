package com.iteye.wwwcomy.reflect;

/**
 * 为反射做测试用的单例
 * 
 * @author Liuxn
 * 
 */
public class Singleton {
	private static int flag = 0;
	private static Singleton instance = new Singleton();

	/**
	 * 类加载时会先实例化instance
	 */
	private Singleton() {
		System.out.println("Init...");
		flag += 2;
	}

	public static Singleton getInstance() {
		return instance;
	}

	public String getString() {
		return String.valueOf(flag);
	}
}
