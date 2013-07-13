package com.iteye.wwwcomy.reflect;

import java.lang.reflect.Constructor;

/**
 * 测试反射单例
 * 
 * @author Liuxn
 * 
 */
public class ReflectSingleton {

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Class<?> singletonClass = Singleton.class;
		Constructor<?> method = singletonClass.getDeclaredConstructor();
		method.setAccessible(true);
		Singleton instance = (Singleton) method.newInstance();
		System.out.println(instance.getString());
	}

}
