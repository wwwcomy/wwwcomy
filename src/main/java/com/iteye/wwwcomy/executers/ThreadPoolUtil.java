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

	public static int POOL_MULTIPLE = 10; // 默认线程
	private static int AVAILABLE_PROCESSPRS = Runtime.getRuntime().availableProcessors();
	
	//@Deprecated 此参数在初始化时已经计算得出，重新设置Pool_Multiple则无效果,所以每次计算
	@SuppressWarnings("unused")
	private static int MAX_POOL_MULTIPLE = POOL_MULTIPLE*(AVAILABLE_PROCESSPRS<=0?1:AVAILABLE_PROCESSPRS); // 默认一个cpu n个线程
	
	public static int KEEP_ALIVE = 3; // when the number of threads is greater than the core, 
	// this is the maximum time that excess idle threads will wait for new tasks before terminating
	public static BlockingQueue<Runnable> QUEUE = null;
	public static RejectedExecutionHandler REJECT_POLICY = null;
	//private static final ExecutorService pool = Executors.newFixedThreadPool(...);
	private static ThreadPoolExecutor POOL = null; // jetty的线程池队列也是BlockingQueue，参考一下
	public static final ThreadPoolExecutor newPool(int P, int M, int K, BlockingQueue<Runnable> Q, RejectedExecutionHandler R) {
		// ArrayBlockingQueue 公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”
		// LinkedBlockingQueue 大多数并发应用程序中，其可预知的性能要低
		if (Q==null)
			Q = new ArrayBlockingQueue<Runnable>(POOL_MULTIPLE*(AVAILABLE_PROCESSPRS<=0?1:AVAILABLE_PROCESSPRS));
		if (R==null)
			R = new ThreadPoolExecutor.AbortPolicy();
		// 参数说明：最少数量   最大数量   空闲时间
		ThreadPoolExecutor pool = new ThreadPoolExecutor(
			P, // 处理线程数
			M, // 默认max线程
			K, // 存活线程
			TimeUnit.SECONDS, // 时间单位
			Q, // 缓冲队列 
            R // 非活动runnable抛弃策略
	    );
		return pool;
	}
	private static ThreadPoolExecutor getPool() {
		if (POOL==null)
			synchronized(ThreadPoolExecutor.class) {
				if (POOL==null) {
					int MaxPoolMultiple = POOL_MULTIPLE*(AVAILABLE_PROCESSPRS<=0?1:AVAILABLE_PROCESSPRS);
					if (QUEUE==null)  // jetty的线程池队列也是BlockingQueue，参考一下
						QUEUE = new ArrayBlockingQueue<Runnable>(MaxPoolMultiple);
					if (REJECT_POLICY==null)
						REJECT_POLICY = new ThreadPoolExecutor.AbortPolicy();
					POOL = newPool(POOL_MULTIPLE, MaxPoolMultiple, KEEP_ALIVE, QUEUE, REJECT_POLICY); 
					// .DiscardPolicy().DiscardOldestPolicy()抛弃旧的任务 
				}
			}
		return POOL;
	}
	
	public static final ExecutorService getInstance() {
		return getPool();
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
	
}
