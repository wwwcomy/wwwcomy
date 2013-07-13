package com.iteye.wwwcomy.compiler;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ProxyCompiler {
	static String className = "TimeProxy";

	public static void main(String[] args) throws Exception {
		create();
	}

	public static Object create() throws Exception {
		String fileString = "package test.compiler;"
				+ "import java.util.Random;" + "public class "
				+ className
				+ " implements Movable {"
				+ "\tMovable m;"
				+ "\tpublic Movable getM() {"
				+ "\t\treturn m;"
				+ "\t}"
				+ "\tpublic void setM(Movable m) {"
				+ "\t\tthis.m = m;"
				+ "\t}"
				+ "\t@Override"
				+ "\tpublic void move() {"
				+ "\t\tlong start = System.currentTimeMillis();"
				+ "\t\tm.move();"
				+ "\t\ttry {"
				+ "\t\t\tThread.sleep(new Random().nextInt(2000));"
				+ "\t\t} catch (InterruptedException e) {"
				+ "\t\t\te.printStackTrace();"
				+ "\t\t}"
				+ "\t\tlong end = System.currentTimeMillis();"
				+ "\t\tSystem.out.println(\"Spend Time: \" + (end - start) + \"ms\");"
				+ "\t}" + "}";
		String fileName = System.getProperty("user.dir");
		fileName += "/src/test/compiler/" + className + ".java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(fileString);
		fw.flush();
		fw.close();

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null,
				null, null);
		Iterable i = fileMgr.getJavaFileObjects(fileName);
		CompilationTask task = compiler.getTask(null, fileMgr, null, null,
				null, i);
		task.call();

		String u = ("file:/" + System.getProperty("user.dir") + "/src/")
				.replaceAll("\\\\", "/");
		URL[] url = new URL[] { new URL(u) };
		URLClassLoader uc = new URLClassLoader(url);
		Class c = uc.loadClass("test.compiler." + className);
		System.out.println(c);
		return c.newInstance();
		// Method m = c.getDeclaredMethod("test", null);
		// m.invoke(c.newInstance(), null);
	}
}
