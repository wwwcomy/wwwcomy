package com.iteye.wwwcomy.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程也调用cb.await()之后就能保证主线程在子线程执行完成之后再执行了！
 * @author wwwcomy
 *
 */
public class RaceConditionRunnable implements Runnable {
	Counter counter;
	final static CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "执行");
		}
	});

	RaceConditionRunnable() {
	}

	RaceConditionRunnable(Counter counter) {
		this.counter = counter;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Counter counter = new Counter();
		ExecutorService service = Executors.newCachedThreadPool();
		Thread t1 = new Thread(new RaceConditionRunnable(counter));
		Thread t2 = new Thread(new RaceConditionRunnable(counter));
		service.execute(t1);
		service.execute(t2);
		cb.await();
		service.shutdown();
		System.out.println(counter.count);
	}

	@Override
	public void run() {
		for (int i = 0; i < 5000000; i++)
			counter.add(1);
		try {
			System.out.println("before");
			cb.await();
			System.out.println("complete");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		} finally {
		}
	}
}

class Counter {

	/**
	 * 此处加上volatile并没有起到预期作用，依旧要把方法同步
	 * NOTICE 这里count的值的范围是多少？
	 * 循环十次的极限情况：
	 * 1.线程一拿到了0,等待线程二
	 * 2.线程二拿到了0,并且计算了9次,得到结果9并且写了回去
	 * 3.线程一第一次计算完,并且把1写入
	 * 4.线程二第十次取到了1
	 * 5.线程一计算完成,把结果写入
	 * 6.线程二把第十次的计算结果2写入
	 */
	protected volatile long count = 0;

	public void add(long value) {
		this.count++;
	}
}