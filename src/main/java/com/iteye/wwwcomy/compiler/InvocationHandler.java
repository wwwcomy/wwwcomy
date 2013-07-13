package com.iteye.wwwcomy.compiler;

import java.lang.reflect.Method;

public interface InvocationHandler {
	void invoke(Object o, Method m);
}
