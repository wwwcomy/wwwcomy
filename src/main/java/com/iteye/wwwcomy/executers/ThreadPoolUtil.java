package com.iteye.wwwcomy.executers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 不要开过多线程池，还是要共用
 */
public class ThreadPoolUtil {

	public static int POOL_MULTIPLE = 1; // 默认线程

	// 默认一个cpu n个线程
	public static int MAX_POOL_MULTIPLE = POOL_MULTIPLE * Runtime.getRuntime().availableProcessors();

	public static int KEEP_ALIVE = 2; // when the number of threads is greater
										// than the core,
	// this is the maximum time that excess idle threads will wait for new tasks
	// before terminating

	// private static final ExecutorService pool =
	// Executors.newFixedThreadPool(...);

	// 参数说明：最少数量 最大数量 空闲时间
	// ArrayBlockingQueue 公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”
	// LinkedBlockingQueue 大多数并发应用程序中，其可预知的性能要低
	private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(POOL_MULTIPLE, // 处理线程数
			MAX_POOL_MULTIPLE, // 默认max线程
			KEEP_ALIVE, TimeUnit.SECONDS, // 时间单位
			new ArrayBlockingQueue<Runnable>(MAX_POOL_MULTIPLE), // 缓冲队列
			new ThreadPoolExecutor.CallerRunsPolicy()); // .DiscardPolicy().DiscardOldestPolicy()抛弃旧的任务

	public static void setMax(int max) {
		POOL.setMaximumPoolSize(max);
	}

	public static void setCore(int core) {
		POOL.setCorePoolSize(core);
		POOL.setMaximumPoolSize(core * Runtime.getRuntime().availableProcessors());
	}

	public static void setExceptionHandler(RejectedExecutionHandler handler) {
		POOL.setRejectedExecutionHandler(handler);
	}

	public static final void shutdown() {
		POOL.shutdown();
	}

	public static final void execute(Runnable task) {
		POOL.execute(task);
	}

	public static final Future<?> submit(Runnable task) {
		return POOL.submit(task);
	}

	public static final <T> Future<T> submit(Runnable task, T result) {
		return POOL.submit(task, result);
	}

	public static final <T> Future<T> submit(Callable<T> task) {
		return POOL.submit(task);
	}

	public static final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return POOL.invokeAll(tasks);
	}

	public static final ExecutorService getInstance() {
		return POOL;
	}

}
