package com.iteye.wwwcomy.classloader;

class a {
	a() {
		System.out.println("a");
	}
}

class b {
	b() {
		System.out.println("b");
	}
}

class c {
	c() {
		System.out.println("c");
	}
}

class d {
	static {
		System.out.println("staticD");
	}

	d() {
		System.out.println("d");
	}

	static void td() {
		System.out.println("td");
	}
}

class e {
	e() {
		System.out.println("e");
	}
}

class f {
	static {
		System.out.println("staticF");
	}

	f() {
		System.out.println("f");
	}
}

class g {
	static {
		System.out.println("firstG");
	}

	g() {
		System.out.println("g");
	}
}

/**
 * ��̬��Ա�ȳ�ʼ��
 * 
 * @author boke
 * 
 */
public class TestInit extends g {
	a a = new a();

	static {
		System.out.println("First");
	}

	public TestInit() {
		System.out.println("mainClass");
	}

	public static void main(String[] args) {
		System.out.println("inMain");
		new c();
		System.out.println("afterC");
		new g();
		d.td();
		new TestInit();
	}

	static b b = new b();
}
