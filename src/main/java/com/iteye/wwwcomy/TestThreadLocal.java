package com.iteye.wwwcomy;

public class TestThreadLocal {

	public static void main(String[] args) {
		String myTestStr = "这是一个测试数据";
		String myTestStr2 = "这是两个测试数据";
		ThreadLocal<String> threadLocal = new ThreadLocal<String>();
		threadLocal.set(myTestStr);

		ThreadLocal<String> threadLocal2 = new ThreadLocal<String>();
		threadLocal2.set(myTestStr2);

		// 取出来并打印
		String str2 = threadLocal.get();
		System.out.println(str2 == myTestStr);
		System.out.println(str2.equals(myTestStr));
		System.out.println(str2);
		// 取出来并打印
		String str3 = threadLocal2.get();
		System.out.println(str3);
	}

}
