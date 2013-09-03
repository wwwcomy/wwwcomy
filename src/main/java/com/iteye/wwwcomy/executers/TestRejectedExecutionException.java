package com.iteye.wwwcomy.executers;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 结论：
 * AbortPolicy 超出线程池大小，会直接报RejectedExecutionException(仅这个handler会报，其余三个不会)
 * CallerRunsPolicy 超出线程池大小会尝试用主线程完成，不会报错，并且会执行所有的任务
 * DiscardOldestPolicy 会将早的线程任务干掉，做新的事情，比如这个例子如果循环到1000就是早的不做了，后面999会做
 * DiscardPolicy 与上面一个正好相反
 * 
 * @author Liuxn
 *
 */
public class TestRejectedExecutionException {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			final int tmpint = i;
			exec(tmpint);
		}
	}

	
	private static void exec(final int tmpint) {
		try {
			ThreadPoolUtil.REJECT_POLICY = new ThreadPoolExecutor.CallerRunsPolicy();
			ThreadPoolUtil.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(tmpint + "Hello World");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			});
		} catch (RejectedExecutionException e) {
//			exec(tmpint);
			System.out.println("123");
		} catch (Throwable e) {
			System.out.println("Throwable");
		}
	}

}
