package com.iteye.wwwcomy.thread;

/**
 * 测试死锁
 * 
 * @author Liuxn
 * 
 */
public class TestDeadLock implements Runnable {

	int flag = 0;
	static Object o1 = new Object();
	static Object o2 = new Object();

	public TestDeadLock(int i) {
		this.flag = i;
	}

	@Override
	public void run() {
		if (flag == 0) {
			// TestDeadLock.class实际上是对每个类所拥有的一个静态对象，所以和o1,o2的作用是一样的
			synchronized (TestDeadLock.class) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) {
					System.out.println("11");
				}
			}
		} else {
			synchronized (o2) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (TestDeadLock.class) {
					System.out.println("22");
				}
			}
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new TestDeadLock(0));
		Thread t2 = new Thread(new TestDeadLock(2));
		t1.start();
		t2.start();
	}
}
