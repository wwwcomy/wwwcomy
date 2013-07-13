package com.iteye.wwwcomy.thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liuxn
 */
public class ThreadQueue {
	public static void main(String[] args) {
		// thread1.begin();
		// thread2.begin();
		// thread3.begin();
		// t4.begin();
		t5.begin();
	}

}

class A {
}

class FileWrapper {
	int id;

	int counter = 0;

	File tmp;

	FileWrapper(int a) {
		this.id = a;
		this.tmp = new File("D:/1/" + id + ".txt");
	}

	public static Object o = new Object();

	public boolean needCir() {
		return this.counter < 10;
	}

	public void write(t5 t) {
		synchronized (o) {
			if (this.counter < 10) {
				FileWriter fw = null;
				try {
					if ((this.id == t.getId(null) && this.counter == 0)
							|| (this.counter != 0 && (this.counter + this.id) % 4 == t.getId(null))) {
						fw = new FileWriter(tmp, true);
						fw.append(String.valueOf(t.getId(null) + 1));
						fw.flush();
						counter++;
					}
					o.notifyAll();
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fw != null)
						try {
							fw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			} else {
				o.notifyAll();
			}
		}
	}
}

class t5 extends Thread {
	private int id;

	private static FileWrapper fw0;
	private static FileWrapper fw1;
	private static FileWrapper fw2;
	private static FileWrapper fw3;

	static {
		fw0 = new FileWrapper(0);
		fw1 = new FileWrapper(1);
		fw2 = new FileWrapper(2);
		fw3 = new FileWrapper(3);
	}

	public t5(int a) {
		this.id = a;
	}

	public int getId(Object o) {
		return id;
	}

	public void run() {
		while (fw0.needCir() || fw1.needCir() || fw2.needCir() || fw3.needCir()) {
			fw0.write(this);
			fw1.write(this);
			fw2.write(this);
			fw3.write(this);
		}
	}

	public static void begin() {
		Thread t0 = new t5(0);
		Thread t1 = new t5(1);
		Thread t2 = new t5(2);
		Thread t3 = new t5(3);

		t0.start();
		t1.start();
		t2.start();
		t3.start();
	}
}

class t4 extends Thread {
	static int i = 0;

	public void run() {
		for (int j = 0; j < 50000; j++)
			i += 1;

		System.out.println(t4.i);
	}

	public static void begin() {
		Thread t1 = new t4();
		Thread t2 = new t4();

		t1.start();
		t2.start();
	}
}

class thread3 extends Thread {
	private int id;

	private static Object o = new Object();
	public static int counter = 0;

	public thread3(int a) {
		this.id = a;
	}

	public void run() {
		while (counter < 10) {
			synchronized (o) {
				while (counter % 3 == id) {
					for (int i = 0; i < 10; i++)
						System.out.println(Thread.currentThread().getName() + "-" + i + "->" + counter);
					counter++;
				}

			}
		}
	}

	public static void begin() {
		Thread t0 = new thread3(0);
		Thread t1 = new thread3(1);
		Thread t2 = new thread3(2);

		t0.start();
		t1.start();
		t2.start();
	}
}

class thread2 extends Thread {
	public static FuckInt n = new FuckInt(0);

	private int id;

	thread2(int n) {
		this.id = n;
	}

	public void run() {
		while (n.getValue() < 75) {
			synchronized (n) {
				if (n.getValue() / 5 % 3 == id) {
					System.out.println(Thread.currentThread().getName() + "-->" + n);
					n.setValue(n.getValue() + 1);
				}
			}
		}
	}

	public static void begin() {
		Thread t1 = new thread2(0);
		Thread t2 = new thread2(1);
		Thread t3 = new thread2(2);

		t1.start();
		t2.start();
		t3.start();
	}
}

class thread1 implements Runnable {

	public static FuckInt n = new FuckInt(0);

	thread1(int n) {
		this.id = n;
	}

	private int id;

	/**
	 * @param args
	 */
	public static void begin() {
		Thread q0 = new Thread(new thread1(2));
		Thread q1 = new Thread(new thread1(1));
		Thread q2 = new Thread(new thread1(0));

		q0.start();
		q1.start();
		q2.start();
	}

	@Override
	public void run() {
		synchronized (n) {
			while (n.getValue() < 75) {
				while (n.getValue() / 5 % 3 == id) {
					System.out.println(Thread.currentThread().getName() + "-->" + n);
					n.setValue(n.getValue() + 1);
				}
				n.notifyAll();
				try {
					n.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (n.getValue() >= 75) {
					n.notifyAll();
					return;
				}
			}
		}
	}
}

class FuckInt {
	private int value;

	public FuckInt(int a) {
		this.value = a;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String toString() {
		return String.valueOf(value);
	}

	public int getValue() {
		return value;
	}
}
