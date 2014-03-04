package com.iteye.wwwcomy.proxy.compiler;

import java.lang.reflect.Method;

public interface InvocationHandler {
	void invoke(Object o, Method m);
}
