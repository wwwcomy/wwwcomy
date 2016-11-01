package com.iteye.wwwcomy.concurrency.lock;

import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

	private Lock lock;
	private BankCard card;

	public Consumer(Lock lock, BankCard card) {
		this.lock = lock;
		this.card = card;
	}

	@Override
	public void run() {
		try {
			while (true) {
				lock.lock();
				System.out.print("消费者准备消费，余额:" + card.getBalance() + "\t");
				card.setBalance(card.getBalance() - 2000);
				System.out.println("消费者消费了2000元，余额:" + card.getBalance());
				Thread.sleep(100);
				lock.unlock();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
