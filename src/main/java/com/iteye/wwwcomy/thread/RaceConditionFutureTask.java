package com.iteye.wwwcomy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

/**
 * 
 * @author wwwcomy
 * 
 */
public class RaceConditionFutureTask implements Callable<Integer> {
	Counter2 counter;
	static CyclicBarrier cb = null;

	int i = 0;

	RaceConditionFutureTask() {
	}

	RaceConditionFutureTask(Counter2 counter) {
		this.counter = counter;
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Counter2 counter = new Counter2();

		cb = new CyclicBarrier(2, new Runnable() { // 此方法在主线程结束后依然会调用
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "a");
				System.out.println(counter.count);
			}
		});

		FutureTask<Integer> task1 = new FutureTask<>(new RaceConditionFutureTask(counter));
		FutureTask<Integer> task2 = new FutureTask<>(new RaceConditionFutureTask(counter));
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		t1.start();
		t2.start();

		// 这句可以在其他线程结束之前就已经执行了
		System.out.println(counter.count);
		// 在futureTask线程未执行完之前，get方法会阻塞当前线程
		System.out.println(task1.get());
		System.out.println(task2.get());
	}

	@Override
	public Integer call() throws Exception {
		for (int i = 0; i < 500000; i++) {
			counter.add(1);
			this.i++;
		}
		System.out.println("Calling cb.await()");
		cb.await();
		Thread.sleep(1000);
		return this.i;
	}
}

class Counter3 {

	/**
	 * 此处加上volatile并没有起到预期作用，依旧要把方法同步 NOTICE 这里count的值的范围是多少？ 循环十次的极限情况：
	 * 1.线程一拿到了0,等待线程二 2.线程二拿到了0,并且计算了9次,得到结果9并且写了回去 3.线程一第一次计算完,并且把1写入
	 * 4.线程二第十次取到了1 5.线程一计算完成,把结果写入 6.线程二把第十次的计算结果2写入
	 */
	protected volatile long count = 0;

	public void add(long value) {
		this.count++;
	}
}