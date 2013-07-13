package com.iteye.wwwcomy.designpattern.singleton;

public class TestSyncSingleton {

	private static TestSyncSingleton instance = null;

	public static TestSyncSingleton getInstance() {
		if (instance == null) {
			return InitSyncSingleton();
		}
		return instance;
	}

	/**
	 * 这个同步方法只在初始化的时候执行一次，并且加了锁，所以对性能的影响只在
	 * @return
	 */
	private static synchronized TestSyncSingleton InitSyncSingleton() {
		if (instance == null)
			return new TestSyncSingleton();
		return instance;
	}

	private TestSyncSingleton() {

	}

}
