package com.iteye.wwwcomy.proxy.plainproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类,实现InvocationHandler接口,可以在被代理类的方法前后做一些其他的事情
 * 
 * @author wwwcomy
 * 
 */
public class LogHandler implements InvocationHandler {

	private Object dele;

	public LogHandler(Object obj) {
		this.dele = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		doBefore();
		// 在这里完全可以把下面这句注释掉，而做一些其它的事情
		Object result = method.invoke(dele, args);
		after();
		return result;
	}

	private void doBefore() {
		System.out.println("before....");
	}

	private void after() {
		System.out.println("after....");
	}
}
