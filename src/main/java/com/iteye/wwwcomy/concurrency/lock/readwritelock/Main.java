package com.iteye.wwwcomy.concurrency.lock.readwritelock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) throws Exception {
		SimpleReadWriteLock lock = new SimpleReadWriteLock();
		Reader reader1 = new Reader(lock);
		Reader reader2 = new Reader(lock);
		Writer writer = new Writer(lock);
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(reader1);
		executor.execute(reader2);
		Thread.sleep(1000);
		executor.execute(writer);
	}
}

class Reader implements Runnable {
	SimpleReadWriteLock lock;

	Reader(SimpleReadWriteLock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			lock.lockRead();
			System.out.println(Thread.currentThread().getName() + " has got a read lock.");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlockRead();
			System.out.println(Thread.currentThread().getName() + " has released a read lock.");
		}
	}

}

class Writer implements Runnable {
	SimpleReadWriteLock lock;

	Writer(SimpleReadWriteLock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			lock.lockWrite();
			System.out.println(Thread.currentThread().getName() + " has got a write lock.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				lock.unlockWrite();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
