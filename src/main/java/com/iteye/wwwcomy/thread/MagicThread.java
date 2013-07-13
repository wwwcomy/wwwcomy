package com.iteye.wwwcomy.thread;

public class MagicThread {

	public static void main(String[] args) {
		Object o = new Object();
		enter(o);
	}

	public static void enter(Object obj) {
		System.out.println("Step 1");
		try {
			magic1(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Step 2");
		synchronized (obj) {
			System.out.println("Step 3 (never reached here)");
		}
		System.out.println("Step 4");
	}

	static void magic(final Object obj) throws Exception {
		final Thread t = new Thread() {
			public void run() {
				synchronized (this) {
					synchronized (obj) {
						try {
							notify();
							join();
						} catch (InterruptedException e) {
						}
					}

				}

			}
		};
		synchronized (t) {
			t.start();
			t.wait();
		}
	}

	static void magic1(final Object obj) throws Exception {
		Thread t = new Thread() {
			public void run() {
				synchronized (obj) {
					while (true) {
					}
				}
			};
		};
		t.start();
		t.join(1);
	}
}
