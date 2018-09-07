package com.iteye.wwwcomy.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这里用runnable也可以， 另外，主线程后面的代码都是在线程池跑完之后才执行的
 * 
 * @author wwwcomy
 * 
 */
public class RaceConditionCallable implements Callable<Boolean> {
	Counter2 counter;
	static CyclicBarrier cb = null;

	RaceConditionCallable() {
	}

	RaceConditionCallable(Counter2 counter) {
		this.counter = counter;
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Counter2 counter = new Counter2();

		cb = new CyclicBarrier(2, new Runnable() { // 等到线程到达后执行一个后续task
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "a");
			}
		});

		ArrayList<Callable<Boolean>> listCall = new ArrayList<Callable<Boolean>>();
		listCall.add(new RaceConditionCallable(counter));
		listCall.add(new RaceConditionCallable(counter));

		ExecutorService executor = Executors.newFixedThreadPool(2); // 必须是allThread的个数
		try {
			executor.invokeAll(listCall);
		} catch (Throwable e) {
			executor.shutdown();
			throw e;
		} finally {
			System.out.println("over");
		}
		executor.shutdown();
		// 这句是在所有线程都跑完之后才会执行,其实用ExecutorService的话本来就是在所有线程跑完之后执行的，和CyclicBarrier并没有什么关系
		System.out.println(counter.count);
		System.out.println(counter.count1);
	}

	@Override
	public Boolean call()throws Exception {
		for (int i = 0; i < 3000; i++) {
			Thread.sleep(1);
			counter.add(1);
		}
		System.out.println("Done:" + Thread.currentThread().getName());
		cb.await();
		return true;
	}
}

class Counter2 {

	/**
	 * 此处加上volatile并没有起到预期作用，依旧要把方法同步 NOTICE 这里count的值的范围是多少？ 循环十次的极限情况：
	 * 1.线程一拿到了0,等待线程二 2.线程二拿到了0,并且计算了9次,得到结果9并且写了回去 3.线程一第一次计算完,并且把1写入
	 * 4.线程二第十次取到了1 5.线程一计算完成,把结果写入 6.线程二把第十次的计算结果2写入
	 */
	protected volatile long count = 0;

	AtomicInteger count1 = new AtomicInteger(0);

	public void add(long value) {
		this.count++;
		count1.incrementAndGet();
	}
}