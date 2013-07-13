package com.iteye.wwwcomy.classloader;

import java.io.*;

public class TestClassLoader extends ClassLoader {

	public static void main(String[] args) throws ClassNotFoundException {

		TestClassLoader loader = new TestClassLoader();
		// try {
		// loader.findClass("stringTest");
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();123123123123
		// }
		Class<?> c = loader.loadClass("stringTest");
		System.out.println("Class c's loader: " + c.getClassLoader());
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

	// public static void test() {
	// Class c;
	// ClassLoader cl;
	// cl = ClassLoader.getSystemClassLoader();
	// System.out.println(cl);
	// while (cl != null) {
	// cl = cl.getParent();
	// System.out.println(cl);
	// }
	// try {
	// c = Class.forName("java.lang.Object");
	// cl = c.getClassLoader();
	// System.out.println(" java.lang.Object's loader is  " + cl);
	// c = Class.forName("test.classloader.TestClassLoader");
	// cl = c.getClassLoader();
	// System.out.println(" TestClassLoader's loader is  " + cl);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
