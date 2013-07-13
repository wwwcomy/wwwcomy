package com.iteye.wwwcomy.thread;

public class TestSyncFunc implements Runnable {

	int i = 100;

	public static void main(String[] args) throws InterruptedException {
		TestSyncFunc test = new TestSyncFunc();
		Thread t1 = new Thread(test);
		t1.start();
		Thread.sleep(1000);
		test.m();
	}

	static Object a = new Object();

	void change() throws Exception {
		synchronized (TestSyncFunc.class) {
			i = 1000;
			Thread.sleep(10000);
			System.out.println("change()调用i=" + i);
		}

	}

	void m() {
		System.out.println("m()调用i=" + i);
	}

	@Override
	public void run() {
		try {
			change();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
