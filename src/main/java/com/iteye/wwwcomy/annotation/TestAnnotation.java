package com.iteye.wwwcomy.annotation;

/**
 * 使用注解
 * 
 * @author wwwcomy
 * 
 */
public class TestAnnotation {
	@Working
	public void foo() {
		System.out.println("TestAnnotation.foo");
	}

	public void bar() {
		System.out.println("TestAnnotation.bar");
	}
}
