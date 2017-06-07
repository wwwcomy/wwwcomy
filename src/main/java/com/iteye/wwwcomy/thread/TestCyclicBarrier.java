package com.iteye.wwwcomy.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * If the barrier's counter came to the initial number, then the runnable task
 * defined in the barrier will run, but the main thread will still run through
 * the end
 * 
 * @author wwwcomy
 *
 */
public class TestCyclicBarrier {

	static CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
		@Override
		public void run() {
			System.out.println("This will run at last, thread name: " + Thread.currentThread().getName());
		}
	});

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new ConsoleOutputThreadInBarrier());
		Thread t2 = new Thread(new ConsoleOutputThreadInBarrier());
		t1.start();
		t2.start();
		System.out.println("Main Thread Done!");

	}
}

class ConsoleOutputThreadInBarrier implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " running...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " done...");
		try {
			TestCyclicBarrier.barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}
