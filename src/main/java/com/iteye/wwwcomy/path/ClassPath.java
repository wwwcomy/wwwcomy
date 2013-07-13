package com.iteye.wwwcomy.path;

import java.net.URI;
import java.net.URL;

/**
 * 获取当前classpath
 * 
 * @author Liuxn
 * 
 */
public class ClassPath {

	public static void main(String[] args) throws Throwable {
		testClassPath();
	}

	public static void testClassPath() throws Throwable {

		URL base = ClassPath.class.getResource("");
		// 先获得本类的所在位置
		System.out.println("0:" + base.getPath());
		// 下面是一些得到classpath和当前类的绝对路径的一些方法。
		// 你可能需要使用其中的一些方法来得到你需要的资源的绝对路径。
		// 1.
		base = ClassPath.class.getResource("");
		System.out.println("1:" + base.getPath());
		// 得到的是当前类ClassPath.class文件的URI目录。不包括自己！
		// 2.
		base = ClassPath.class.getResource("/");
		System.out.println("2:" + base.getPath());
		// 得到的是当前的classpath的绝对URI路径。
		// 3.
		base = Thread.currentThread().getContextClassLoader().getResource("");
		System.out.println("3:" + base.getPath());
		// 得到的也是当前ClassPath的绝对URI路径。
		// 4.
		base = ClassPath.class.getClassLoader().getResource("");
		System.out.println("4:" + base.getPath());
		// 得到的也是当前ClassPath的绝对URI路径。
		// 5.
		base = ClassLoader.getSystemResource("");
		System.out.println("5:" + base.getPath());
	}

	public static void testUri() throws Throwable {
		// if (args.length != 1) {
		// System.err.println("usage: java URIDemo1 uri");
		// return;
		// }
		// URI uri = new URI(args[0]);
		URI uri = new URI("http://localhost:8089/yigo/web/index.html?a=1");

		System.out.println("Authority = " + uri.getAuthority());
		System.out.println("Fragment = " + uri.getFragment());
		System.out.println("Host = " + uri.getHost());
		System.out.println("Path = " + uri.getPath());
		System.out.println("Port = " + uri.getPort());
		System.out.println("Query = " + uri.getQuery());
		System.out.println("Scheme = " + uri.getScheme());
		System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
		System.out.println("User Info = " + uri.getUserInfo());
		System.out.println("URI is absolute: " + uri.isAbsolute());
		System.out.println("URI is opaque: " + uri.isOpaque());

	}

	public static void testUrl() throws Throwable {

		URL url = new URL("http://localhost:8089/yigo/web/index.html?a=1");

		System.out.println("Authority = " + url.getAuthority());
		System.out.println("Default port = " + url.getDefaultPort());
		System.out.println("File = " + url.getFile());
		System.out.println("Host = " + url.getHost());
		System.out.println("Path = " + url.getPath());
		System.out.println("Port = " + url.getPort());
		System.out.println("Protocol = " + url.getProtocol());
		System.out.println("Query = " + url.getQuery());
		System.out.println("Ref = " + url.getRef());
		System.out.println("User Info = " + url.getUserInfo());

		System.out.print('\n');
		// InputStream is = url.openStream();
		// int ch;
		// while ((ch = is.read()) != -1) {
		// System.out.print((char) ch);
		// }
		// is.close();
	}
}
