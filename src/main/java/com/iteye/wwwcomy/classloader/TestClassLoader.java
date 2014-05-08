package com.iteye.wwwcomy.classloader;

import java.io.*;

/**
 * 测试自定义ClassLoader,知识点：
 * 1.一般覆盖findClass用于自定义加载(由于D盘并不是classPath,所以AppClassLoader及父亲ExtClassLoader
 * ,BootStrapClassLoader都加载不到，最终由本类的findClass方法加载)
 * 2.如果项目中(ClassPath中)存在test的类，则类将由AppClassLoader加载，返回的Class对象就是同一个了
 * 3.自己加载类可以先将类作为流读入，再调用 defineClass方法返回具体类
 * 4.Tomcat对不同的项目肯定是使用不同的ClassLoader实例的，
 * 这样能保证不同的Context在不同的类命名空间运行，具体见WebappClassLoader
 * 
 * @author wwwcomy
 * 
 */
public class TestClassLoader extends ClassLoader {

	public static void main(String[] args) throws ClassNotFoundException {

		TestClassLoader loader = new TestClassLoader();
		TestClassLoader loader1 = new TestClassLoader();
		Class<?> c = loader.loadClass("test");
		Class<?> c1 = loader1.loadClass("test");
		System.out.println("Class c's loader: " + c.getClassLoader());
		System.out.println("Class c's loader: " + c1.getClassLoader());
		System.out.println(c == c1);
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println("i am called.");
		return super.loadClass(name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class findClass(String name) throws ClassNotFoundException {
		System.out.println("findClass() is called.");
		byte[] data = loadClassData(name);
		if (data == null) {
			throw new ClassNotFoundException();
		}
		return defineClass(name, data, 0, data.length);
	}

	public byte[] loadClassData(String name) {
		FileInputStream fis = null;
		byte[] data = null;
		try {
			fis = new FileInputStream(new File("d:/" + name + ".class"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch = 0;
			while ((ch = fis.read()) != -1) {
				baos.write(ch);
			}
			data = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return data;
	}

	@SuppressWarnings("rawtypes")
	public static void test() {
		Class c;
		ClassLoader cl;
		cl = ClassLoader.getSystemClassLoader();
		System.out.println(cl);
		while (cl != null) {
			cl = cl.getParent();
			System.out.println(cl);
		}
		try {
			c = Class.forName("java.lang.Object");
			cl = c.getClassLoader();
			System.out.println(" java.lang.Object's loader is  " + cl);
			c = Class.forName("test.classloader.TestClassLoader");
			cl = c.getClassLoader();
			System.out.println(" TestClassLoader's loader is  " + cl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
