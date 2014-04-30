package com.iteye.wwwcomy.thread;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {

	public static List<Boolean> stock = new ArrayList<Boolean>(10);

	public static void main(String[] args) {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();

		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		t1.start();
		t2.start();
	}
}

class Producer implements Runnable {
	@Override
	public void run() {
		while (true) {
			synchronized (ProducerConsumer.stock) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (ProducerConsumer.stock.size() == 10) {
					try {
						System.out.println("Stock too much. Waiting to consume...");
						ProducerConsumer.stock.notifyAll();
						ProducerConsumer.stock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					produceOne();
					ProducerConsumer.stock.notifyAll();
				}
			}
		}
	}

	private void produceOne() {
		ProducerConsumer.stock.add(true);
		System.out.println("Produce One... The size of stock is" + ProducerConsumer.stock.size());
	}

}

class Consumer implements Runnable {
	@Override
	public void run() {
		while (true) {
			synchronized (ProducerConsumer.stock) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (ProducerConsumer.stock.size() == 0) {
					try {
						System.err.println("No Stock. Waiting to produce...");
						ProducerConsumer.stock.notifyAll();
						ProducerConsumer.stock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					consumeOne();
					ProducerConsumer.stock.notifyAll();
				}
			}
		}
	}

	private void consumeOne() {
		ProducerConsumer.stock.remove(ProducerConsumer.stock.size() - 1);
		System.err.println("Consume One... The size of stock is" + ProducerConsumer.stock.size());
	}
}
