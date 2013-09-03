package com.iteye.wwwcomy.executers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 不要开过多线程池，还是要共用
 * 
 * 线程池中处理任务的优先级为：
核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
 * 
 */
public class ThreadPoolUtil {

	public static int POOL_MULTIPLE = 15; // 默认线程
	public static int MAX_POOL_MULTIPLE = POOL_MULTIPLE*Runtime.getRuntime().availableProcessors(); // 默认一个cpu n个线程
	public static int KEEP_ALIVE = 2; // when the number of threads is greater than the core, 
	// this is the maximum time that excess idle threads will wait for new tasks before terminating
	public static BlockingQueue<Runnable> QUEUE = null;
	/**
	 * 策略仅在初始化前进行设置有效
	 * 策略种类:
	 * AbortPolicy线程无法执行时直接抛出java.util.concurrent.RejectedExecutionException异常
	 * CallerRunsPolicy主线程直接尝试执行该任务；当线程池中可加入时，将任务添加到线程池中；该操作会重复执行，可以有效降低主线程将任务加入到线程池的速度
	 * DiscardPolicy直接抛弃当前的任务
	 * DiscardOldestPolicy直接抛弃旧的任务，即把线程池内最早加入队列的线程抛弃
	 * 
	 */
	public static RejectedExecutionHandler REJECT_POLICY = null;
	//private static final ExecutorService pool = Executors.newFixedThreadPool(...);
	private static ThreadPoolExecutor POOL = null;
	private static ThreadPoolExecutor getPool() {
		if (POOL==null)
			synchronized(ThreadPoolExecutor.class) {
				if (POOL==null) {
					// ArrayBlockingQueue 公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”
					// LinkedBlockingQueue 大多数并发应用程序中，其可预知的性能要低
					if (QUEUE==null)
						QUEUE = new ArrayBlockingQueue<Runnable>(MAX_POOL_MULTIPLE);
					if (REJECT_POLICY==null)
						REJECT_POLICY = new ThreadPoolExecutor.AbortPolicy();
					// 参数说明：最少数量   最大数量   空闲时间
					POOL = new ThreadPoolExecutor(
							POOL_MULTIPLE, // 处理线程数
							MAX_POOL_MULTIPLE, // 默认max线程
							KEEP_ALIVE, 
							TimeUnit.SECONDS, // 时间单位
							QUEUE, // 缓冲队列 
				            REJECT_POLICY); // .DiscardPolicy().DiscardOldestPolicy()抛弃旧的任务 
				}
			}
		return POOL;
	}
	
	public static final void shutdown() {
		getPool().shutdown();
	}
	
	public static final void execute(Runnable task) {
		getPool().execute(task);
	}
	
	public static final Future<?> submit(Runnable task) {
		return getPool().submit(task);
    }

    public static final <T> Future<T> submit(Runnable task, T result) {
		return getPool().submit(task, result);
    }

    public static final <T> Future<T> submit(Callable<T> task) {
		return getPool().submit(task);
	}
	
	public static final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) 
		throws InterruptedException {
		return getPool().invokeAll(tasks);
	}
	
	public static final ExecutorService getInstance() {
		return getPool();
	}
	
}
