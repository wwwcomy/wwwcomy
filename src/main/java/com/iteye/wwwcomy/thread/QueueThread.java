package com.iteye.wwwcomy.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 四个线程循环打出1,2,3,4
 * 这里要注意，Integer对象经过操作，比如++之后就会变成另外一个对象，所以对Integer对象加锁是不对的。
 * 
 * @author Liuxn
 * 
 */
public class QueueThread extends Thread {

	public static List<Integer> list1 = new ArrayList<Integer>(10);
	public static List<Integer> list2 = new ArrayList<Integer>(10);
	public static List<Integer> list3 = new ArrayList<Integer>(10);
	public static List<Integer> list4 = new ArrayList<Integer>(10);

	public static Object o = new Object();
	private int id;

	public QueueThread(int a) {
		this.id = a;
	}

	public List<Integer> getList(int n) {
		switch (n) {
		case 1:
			return list1;
		case 2:
			return list2;
		case 3:
			return list3;
		case 4:
			return list4;
		default:
			return null;
		}
	}

	@Override
	public void run() {
		synchronized (o) {
			while (true) {
				// List t = this.getList(id);
				List<Integer> t;
				for (int i = 1; i < 5; i++) {
					t = this.getList(i);
					if (t.size() == 10) {
						continue;
					}
					if ((t.size() == 0 && i == this.id)
							|| (t.size() != 0 && ((t.size() + i) % 4 == this.id) || (t.size() + i) % 4 == 0 && this.id == 4)) {
						t.add(id);
					}
					o.notifyAll();
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notifyAll();
				if (list1.size() == 10 && list2.size() == 10 && list3.size() == 10 && list4.size() == 10)
					return;
			}
		}
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Thread t1 = new QueueThread(1);
		Thread t2 = new QueueThread(2);
		Thread t3 = new QueueThread(3);
		Thread t4 = new QueueThread(4);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		Thread.sleep(100);
		System.out.println(list1);
		System.out.println(list2);
		System.out.println(list3);
		System.out.println(list4);
	}

}
