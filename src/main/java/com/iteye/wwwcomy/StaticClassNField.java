package com.iteye.wwwcomy;

/**
 * 同样的名字，静态属性的优先级大于静态类的优先级
 * 
 * @author Liuxn
 * 
 */
public class StaticClassNField {

	public static void main(String[] args) {
		System.out.println(X.Y.Z);
	}

}

class X {
	static class Y {
		static String Z = "Black";
	}

	static C Y = new C();
}

class C {
	String Z = "White";
}
