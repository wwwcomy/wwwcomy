package com.iteye.wwwcomy.executers;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 线程池 创建线程池，销毁线程池，添加新任务
 * 
 * @author Liuxn
 */
public final class ThreadPool {
	
	private static class HOLDER{
		private static final ThreadPool instance = new ThreadPool();
	}
	
	/* 单例 */
	private static ThreadPool instance = HOLDER.instance;

	public static final int SYSTEM_BUSY_TASK_COUNT = 150;
	/* 默认池中线程数 */
	public static int worker_num = 5;
	/* 已经处理的任务数 */
	private static int taskCounter = 0;

	public static boolean systemIsBusy = false;

	private static List<Task> taskQueue = Collections.synchronizedList(new LinkedList<Task>());
	/* 池中的所有线程 */
	public PoolWorker[] workers;

	private ThreadPool() {
		workers = new PoolWorker[5];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new PoolWorker(i);
		}
	}

	private ThreadPool(int pool_worker_num) {
		worker_num = pool_worker_num;
		workers = new PoolWorker[worker_num];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new PoolWorker(i);
		}
	}

	public static ThreadPool getInstance() {
		return instance;
	}

	/**
	 * 增加新的任务 每增加一个新任务，都要唤醒任务队列
	 * 
	 * @param newTask
	 */
	public void addTask(Task newTask) {
		synchronized (taskQueue) {
			newTask.setTaskId(++taskCounter);
			newTask.setSubmitTime(new Date());
			taskQueue.add(newTask);
			/* 唤醒队列, 开始执行 */
			taskQueue.notifyAll();
		}
		System.err.println("Submit Task<" + newTask.getTaskId() + ">: " + newTask.info());
	}

	/**
	 * 批量增加新任务
	 * 
	 * @param taskes
	 */
	public void batchAddTask(Task[] taskes) {
		if (taskes == null || taskes.length == 0) {
			return;
		}
		synchronized (taskQueue) {
			for (int i = 0; i < taskes.length; i++) {
				if (taskes[i] == null) {
					continue;
				}
				taskes[i].setTaskId(++taskCounter);
				taskes[i].setSubmitTime(new Date());
				taskQueue.add(taskes[i]);
			}
			/* 唤醒队列, 开始执行 */
			taskQueue.notifyAll();
		}
		for (int i = 0; i < taskes.length; i++) {
			if (taskes[i] == null) {
				continue;
			}
			System.err.println("Submit Task<" + taskes[i].getTaskId() + ">: " + taskes[i].info());
		}
	}

	/**
	 * 线程池信息
	 * 
	 * @return
	 */
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nTask Queue Size:" + taskQueue.size());
		for (int i = 0; i < workers.length; i++) {
			sb.append("\nWorker " + i + " is " + ((workers[i].isWaiting()) ? "Waiting." : "Running."));
		}
		return sb.toString();
	}

	/**
	 * 销毁线程池
	 */
	public synchronized void destroy() {
		for (int i = 0; i < worker_num; i++) {
			workers[i].stopWorker();
			workers[i] = null;
		}
		taskQueue.clear();
	}

	/**
	 * 池中工作线程
	 * 
	 */
	private class PoolWorker extends Thread {
		private int index = -1;
		/* 该工作线程是否有效 */
		private boolean isRunning = true;
		/* 该工作线程是否可以执行新任务 */
		private boolean isWaiting = true;

		public PoolWorker(int index) {
			this.index = index;
			start();
		}

		public void stopWorker() {
			this.isRunning = false;
		}

		public boolean isWaiting() {
			return this.isWaiting;
		}

		/**
		 * 循环执行任务 这也许是线程池的关键所在
		 */
		public void run() {
			while (isRunning) {
				Task r = null;
				synchronized (taskQueue) {
					while (taskQueue.isEmpty()) {
						try {
							/* 任务队列为空，则等待有新任务加入从而被唤醒 */
							taskQueue.wait(20);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
					}
					/* 取出任务执行 */
					r = (Task) taskQueue.remove(0);
				}
				if (r != null) {
					isWaiting = false;
					try {
						if (true) {
							r.setBeginExceuteTime(new Date());
							System.out.println("Worker<" + index + "> start execute Task<" + r.getTaskId() + ">");
							if (r.getBeginExceuteTime().getTime() - r.getSubmitTime().getTime() > 1000)
								System.out.println("longer waiting time. " + r.info() + ",<" + index + ">,time:"
										+ (r.getFinishTime().getTime() - r.getBeginExceuteTime().getTime()));
						}
						/* 该任务是否需要立即执行 */
						if (r.needExecuteImmediate()) {
							new Thread(r).start();
						} else {
							r.run();
						}
						if (true) {
							r.setFinishTime(new Date());
							System.out.println("Worker<" + index + "> finish task<" + r.getTaskId() + ">");
							if (r.getFinishTime().getTime() - r.getBeginExceuteTime().getTime() > 1000)
								System.out.println("longer execution time. " + r.info() + ",<" + index + ">,time:"
										+ (r.getFinishTime().getTime() - r.getBeginExceuteTime().getTime()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					isWaiting = true;
					r = null;
				}
			}
		}
	}
}