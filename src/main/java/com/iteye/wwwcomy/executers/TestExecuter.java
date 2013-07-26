package com.iteye.wwwcomy.executers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecuter {

	private static ExecutorService pool = Executors.newFixedThreadPool(20);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			try {
				pool.execute(new A(i));
			} catch (Throwable e) {
				System.out.println("外层检查到异常");
			}
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
		System.out.println(Thread.currentThread().getName() + "--> Value is:" + value);
		throw new RuntimeException("test");
	}
}