package com.iteye.wwwcomy.designpattern.singleton;

/**
 * 测试单例，另外用内部类的单例见com.iteye.wwwcomy.executers.ThreadPool
 * 
 * @author Liuxn
 * 
 */
public class TestSingleton {

	private static volatile TestSingleton instance = null;

	/**
	 * 原始的单例方法
	 * 
	 * @return
	 */
	public static TestSingleton getInstance() {
		if (instance == null) {
			return new TestSingleton();
		}
		return instance;
	}

	private TestSingleton() {

	}

	public static TestSingleton getInstance2() {
		if (instance == null) {
			return syncGetInstance();
		}
		return instance;
	}

	private static TestSingleton syncGetInstance() {
		// be aware of the volatile key word
		// http://www.importnew.com/18126.html
		synchronized (TestSingleton.class) {
			if (instance == null) {
				instance = new TestSingleton();
			}
		}
		return instance;
	}

}
