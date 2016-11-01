package com.iteye.wwwcomy.concurrency.lock;

import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {

	private Lock lock;
	private BankCard card;

	public Producer(Lock lock, BankCard card) {
		this.lock = lock;
		this.card = card;
	}

	@Override
	public void run() {
		try {
			while (true) {
				lock.lock();
				System.out.print("生产者准备生产，余额:" + card.getBalance() + "\t");
				card.setBalance(card.getBalance() + 500);
				System.out.println("生产者生产了500元，余额:" + card.getBalance());
				lock.unlock();
				Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
