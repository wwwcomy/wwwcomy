package com.iteye.wwwcomy.thread;

/**
 * 假线程，覆盖了start方法，无线程特性，注意Thread.getName()是final的，不能覆盖
 * 
 * @author Liuxn
 * 
 */
public class FakeThread extends Thread {
	private String name;

	public static void main(String[] args) {
		FakeThread t = new FakeThread();
		t.go();
	}

	public FakeThread() {
	}

	public FakeThread(String name) {
		this.name = name;
	}

	public String getThreadName() {
		return name;
	}

	private void go() {
		FakeThread first = new FakeThread("first");
		first.start();
		FakeThread second = new FakeThread("second");
		second.start();
	}

	public void start() {
		for (int i = 0; i < 2; i++) {
			System.out.println(this.getThreadName() + i);
			try {
				Thread.sleep(100);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
