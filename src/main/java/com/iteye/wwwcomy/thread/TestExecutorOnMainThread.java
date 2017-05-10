package com.iteye.wwwcomy.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Check whether the main thread will run after the sub threads being done.
 * 
 * The difference between submit and execute method can be found at:
 * http://stackoverflow.com/questions/3929342/choose-between-executorservices-submit-and-executorservices-execute
 * 
 * @author wwwcomy
 *
 */
public class TestExecutorOnMainThread {

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		Thread t1 = new Thread(new ConsoleOutputThreadForExecutors());
		Thread t2 = new Thread(new ConsoleOutputThreadForExecutors());
		// executor.submit(t1);
		// executor.submit(t2);
		executor.execute(t1);
		executor.execute(t2);
		executor.shutdown();
		System.out.println("Main Thread Done1!");
		// submit and execute method will not block the main thread!

		executor = Executors.newFixedThreadPool(10);
		List<Callable<String>> list = new ArrayList<Callable<String>>();
		list.add(new ConsoleOutputCallableForExecutors());
		list.add(new ConsoleOutputCallableForExecutors());
		executor.invokeAll(list);
		// invokeAll method will block the current thread, until all the tasks
		// are done.
		executor.shutdown();
		System.out.println("Main Thread Done2!");

	}
}

class ConsoleOutputThreadForExecutors implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " running...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " done...");
	}

}

class ConsoleOutputCallableForExecutors implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println(Thread.currentThread().getName() + " running...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " done...");
		return "done";
	}

}
