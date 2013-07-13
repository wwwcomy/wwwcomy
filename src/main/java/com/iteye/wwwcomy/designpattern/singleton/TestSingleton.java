package com.iteye.wwwcomy.designpattern.singleton;

/**
 * 测试单例，另外用内部类的单例见com.iteye.wwwcomy.executers.ThreadPool
 * 
 * @author Liuxn
 * 
 */
public class TestSingleton {

	private static TestSingleton instance = null;

	public static TestSingleton getInstance() {
		if (instance == null) {
			return new TestSingleton();
		}
		return instance;
	}

	private TestSingleton() {

	}

}
