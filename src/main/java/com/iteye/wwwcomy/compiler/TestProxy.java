package com.iteye.wwwcomy.compiler;

public class TestProxy {
	public static void main(String[] args) throws Exception {
		test2();
	}

	public static void test1() throws Exception {
		Movable car = new Car();
		InvocationHandler h = new TimeProxy_Y(car);
		Movable m = (Movable) Proxy.newProxyInstance(Movable.class, h);
		m.move();
	}

	public static void test2() throws Exception {
		Movable car = new Car();
		InvocationHandler h = new LogProxy(car);
		Movable m = (Movable) Proxy.newProxyInstance(Movable.class, h);
		m.move();
	}
}
