package com.iteye.wwwcomy.compiler;

import java.lang.reflect.Method;

public class LogProxy implements InvocationHandler {
	Movable m;

	public Movable getM() {
		return m;
	}

	public void setM(Movable m) {
		this.m = m;
	}

	public LogProxy() {
	}

	public LogProxy(Movable m) {
		this.m = m;
	}

	@Override
	public void invoke(Object o, Method m) {
		System.out.println("记录日志...");
		try {
			m.invoke(this.m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("记录日志完毕");

	}
}