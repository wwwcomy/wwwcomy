package com.iteye.wwwcomy.thread;

/**
 * Java解惑，线程启动start()
 * 
 * @author Liuxn
 * 
 */
public class PingPong {
	public static synchronized void main(String[] a) {
		Thread t = new Thread() {
			public void run() {
				pong();
			}
		};
		t.run();
		System.out.print("Ping");
	}

	static synchronized void pong() {
		System.out.print("Pong");
	}

	static void test() {
		int j = 0;
		for (int i = 0; i < 100; i++) {
			j = j++;
		}
		System.out.println(j);
	}
}
