package com.iteye.wwwcomy.designpattern.singleton;

/**
 * 使用静态内部类来完成单例的初始化
 * 
 * @author wwwcomy
 * 
 */
public class TestSyncSingleton2 {
	private static class HOLDER {
		private static final TestSyncSingleton2 instance = new TestSyncSingleton2();
	}

	public static TestSyncSingleton2 getInstance() {
		return HOLDER.instance;
	}
}
