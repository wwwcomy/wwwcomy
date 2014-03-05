package com.iteye.wwwcomy.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("before " + method.getName());
		Object o = proxy.invokeSuper(obj, args);
		System.out.println("after " + method.getName());
		return o;
	}

}
