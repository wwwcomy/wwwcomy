package com.iteye.wwwcomy.executers;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

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
			ThreadPoolUtil.setCore(1);
			ThreadPoolUtil.setExceptionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
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
