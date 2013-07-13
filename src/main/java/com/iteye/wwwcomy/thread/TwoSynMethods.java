package com.iteye.wwwcomy.thread;

/**
 * @author Liuxn
 * 
 */
public class TwoSynMethods {

	private void ivkMethod1() {
		Thread thread1 = new Thread() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					// System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					// System.out.println("~~~~ start thread method1 ");
					method1();
					// System.out.println("~~~~ end   thread method1 ");
				}
			}
		};

		thread1.start();
	}

	private void ivkMethod2() {
		Thread thread2 = new Thread() {
			public void run() {
				for (int i = 0; i < 20; i++) {
					// System.out.println("\n\n#################################################");
					// System.out.println("#### start thread method2 ");
					method2();
					// System.out.println("#### end   thread method2 ");
				}
			}
		};

		thread2.start();
	}

	private synchronized void method1() {
		System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~ enter method1");

		try {
			System.out.println("~~~~ method1 begin to sleep 10s");
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("~~~~ method1 end sleep");
	}

	private synchronized void method2() {
		System.out.println("\n\n#################################################");
		System.out.println("#### enter method2");

		try {
			System.out.println("#### method2 begin to sleep 3s");
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("#### method2 end sleep");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// synchronized方法 为对同一对象的锁，所以注释代码和非注释代码跑的结果不同
			TwoSynMethods obj = new TwoSynMethods();
			TwoSynMethods obj2 = new TwoSynMethods();
			obj.ivkMethod1();
			// obj.ivkMethod1();
			obj2.ivkMethod2();

		} catch (Exception e) {
			e.printStackTrace();

		} catch (Error er) {
			er.printStackTrace();
		}
	}

}
