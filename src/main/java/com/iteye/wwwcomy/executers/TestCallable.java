package com.iteye.wwwcomy.executers;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wwwcomy
 * 
 *         测试Callable接口和使用ExecutorService去运行
 */
public class TestCallable {
	public static void main(String[] args) throws Throwable {
		ExecutorService exec = testSingle();

		testCircle(exec);

		exec.shutdown();

	}

	public static void testCircle(ExecutorService exec) throws InterruptedException, ExecutionException {
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>(); // Future
																				// 相当于是用来存放Executor执行的结果的一种容器
		for (int i = 0; i < 10; i++) {
			results.add(exec.submit(new MyCall(i)));
		}
		Thread.sleep(100);
		for (Future<Integer> fs : results) {
			if (fs.isDone()) {
				System.out.println(fs.get());
			} else {
				System.out.println("Future result is not yet complete");
			}
		}
	}

	public static ExecutorService testSingle() throws InterruptedException, ExecutionException {
		Callable<Integer> c1 = new MyCall(1);
		Callable<Integer> c2 = new MyCall(11);
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Integer> fs1 = exec.submit(c1);
		Future<Integer> fs2 = exec.submit(c2);
		Thread.sleep(100);
		System.out.println(fs1.get());
		System.out.println(fs2.get());
		return exec;
	}

}

class MyCall implements Callable<Integer> {

	private int start;

	public MyCall(int start) {
		this.start = start;
	}

	@Override
	public Integer call() throws Exception {
		for (int i = 0; i < 10; i++) {
			start++;
		}
		return start;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
}