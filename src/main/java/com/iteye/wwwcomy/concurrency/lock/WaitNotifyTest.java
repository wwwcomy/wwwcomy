package com.iteye.wwwcomy.concurrency.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitNotifyTest {

	static Object lock = new Object();

	// not necessary, just trying to use.
	static AtomicInteger ai = new AtomicInteger(0);

	public static void main(String[] args) {
		Thread t1 = new Thread(new DemoThread("Thread1"));
		Thread t2 = new Thread(new DemoThread("Thread2"));
		Thread t3 = new Thread(new DemoThread("Thread3"));
		t1.start();
		t2.start();
		t3.start();
	}

}

class DemoThread implements Runnable {

	private String name;

	DemoThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		synchronized (WaitNotifyTest.lock) {
			System.out.println("The " + name + " has got the lock.");
			int index = WaitNotifyTest.ai.addAndGet(1);
			if (index != 3) {
				try {
					System.out.println("The " + name + " is going to wait.");
					// calling wait will release the lock, if the thread is
					// being notified, it will run after this statement
					WaitNotifyTest.lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("The " + name + " has stopped waiting.");
			} else {
				System.out.println("The " + name + " is going to notify all the thread.");
				WaitNotifyTest.lock.notifyAll();
			}
			System.out.println("The " + name + " is going to out of the synchronized block.");

		}

	}
}
