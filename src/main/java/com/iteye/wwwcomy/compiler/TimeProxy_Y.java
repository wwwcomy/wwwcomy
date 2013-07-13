package com.iteye.wwwcomy.compiler;

import java.lang.reflect.Method;
import java.util.Random;

public class TimeProxy_Y implements InvocationHandler {
	Object m;

	public Object getM() {
		return m;
	}

	public void setM(Object m) {
		this.m = m;
	}

	public TimeProxy_Y() {
	}

	public TimeProxy_Y(Movable m) {
		this.m = m;
	}

	@Override
	public void invoke(Object o, Method m) {
		System.out.println("开始进行运行时间测量...");
		long start = System.currentTimeMillis();
		try {
			m.invoke(this.m);
			Thread.sleep(new Random().nextInt(2000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("共耗费时间:" + (System.currentTimeMillis() - start));

	}
}