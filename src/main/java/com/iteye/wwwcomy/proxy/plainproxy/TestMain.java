package com.iteye.wwwcomy.proxy.plainproxy;

import java.lang.reflect.Proxy;

/**
 * 测试类
 * 
 * @author wwwcomy
 * 
 */
public class TestMain {
	public static void main(String[] args) {
		HelloImpl impl = new HelloImpl();
		LogHandler handler = new LogHandler(impl);
		// 这里把handler与impl新生成的代理类相关联
		Hello hello = (Hello) Proxy.newProxyInstance(impl.getClass().getClassLoader(), impl.getClass().getInterfaces(), handler);

		// 这里无论访问哪个方法，都是会把请求转发到handler.invoke
		hello.print("All the test");
		hello.sayHello("Denny");

		// 只能搞接口，不能直接转
		// ((HelloImpl) hello).foo();
	}
}
