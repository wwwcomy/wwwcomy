package com.iteye.wwwcomy.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	public static void main(String[] args) {
		BankCard card = new BankCard();
		Lock lock = new ReentrantLock();
		Consumer consumer = new Consumer(lock, card);
		Producer producer = new Producer(lock, card);
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(consumer);
		executor.execute(producer);
	}
}
