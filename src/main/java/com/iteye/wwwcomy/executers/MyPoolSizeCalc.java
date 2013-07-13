package com.iteye.wwwcomy.executers;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyPoolSizeCalc extends PoolSizeCalculator {

	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		MyPoolSizeCalc calculator = new MyPoolSizeCalc();
		calculator.calculateBoundaries(new BigDecimal(1.0), new BigDecimal(100000));
	}

	protected long getCurrentThreadCPUTime() {
		return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
	}

	protected Runnable creatTask() {
		return new Runnable() {
			public void run() {
				int s = 89128374 + 98413241;
				for (int i = 0; i < 100000; i++) {
					s += i;
				}
				System.out.println(s + "Hello World");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// int a=1;
			}
		};
	}

	protected BlockingQueue<Runnable> createWorkQueue() {
		return new LinkedBlockingQueue<Runnable>();
	}
}
