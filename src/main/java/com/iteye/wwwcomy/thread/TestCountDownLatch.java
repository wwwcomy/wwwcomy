package com.iteye.wwwcomy.thread;

import java.util.concurrent.CountDownLatch;

/**
 * If the count down latch's counter came to 0, then the await thread will run
 * 
 * @author wwwcomy
 *
 */
public class TestCountDownLatch {

	static CountDownLatch latch = new CountDownLatch(2);

	public static void main(String[] args) {
		Thread t1 = new Thread(new ConsoleOutputThread());
		Thread t2 = new Thread(new ConsoleOutputThread());
		t1.start();
		t2.start();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread Done!");

	}
}

class ConsoleOutputThread implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " running...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " done...");
		TestCountDownLatch.latch.countDown();
	}

}
