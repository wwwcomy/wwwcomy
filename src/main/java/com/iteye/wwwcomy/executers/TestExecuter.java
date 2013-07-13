package com.iteye.wwwcomy.executers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecuter {

	private static ExecutorService pool = Executors.newFixedThreadPool(20);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			pool.execute(new A(i));
		}
	}
}

class A implements Runnable {

	public int value;

	A(int i) {
		this.value = i;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "--> Value is:"
				+ value);
	}
}