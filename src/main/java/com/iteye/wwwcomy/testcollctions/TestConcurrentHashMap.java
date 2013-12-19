package com.iteye.wwwcomy.testcollctions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {

	private static Map<Long, ServiceDO> widgetCacheMap = new ConcurrentHashMap<Long, ServiceDO>();

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			Thread tt = new Thread(new Rund());
			tt.start();
		}
	}

	static class Rund implements Runnable {
		/**
		 * 1W次，总有那么几次线程不安全
		 */
		public void run() {
			TestConcurrentHashMap tt = new TestConcurrentHashMap();
			tt.set();
			int s1 = widgetCacheMap.get(1L).getStatus();
			tt.change();
			int s2 = widgetCacheMap.get(1L).getStatus();
			if (s1 == s2) {
				System.out.println(s1 + ":" + s2);
			}
		}
	}

	public void set() {
		Map mm = new HashMap();
		ServiceDO ss = new ServiceDO();
		ss.setStatus(1);
		mm.put(1L, ss);
		widgetCacheMap = mm;
	}

	public void change() {
		Map mm = new HashMap();
		ServiceDO ss = new ServiceDO();
		ss.setStatus(2);
		mm.put(1L, ss);
		widgetCacheMap = mm;
	}

}
