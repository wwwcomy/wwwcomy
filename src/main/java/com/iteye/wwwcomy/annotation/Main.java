package com.iteye.wwwcomy.annotation;

import java.lang.reflect.Method;

/**
 * 注解测试类
 * 
 * @author wwwcomy
 * 
 */
public class Main {
	public static void main(String[] args) throws Exception {
		TestAnnotation ta = new TestAnnotation();
		Class<TestAnnotation> annotationClass = TestAnnotation.class;
		Method[] methods = annotationClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getParameterTypes().length == 0) {
				if (method.getAnnotation(Working.class) != null) {
					System.out.println("working");
					method.invoke(ta, null);
				} else {
					System.out.println("Without working");
					method.invoke(ta, null);
				}
			}
		}
	}
}
