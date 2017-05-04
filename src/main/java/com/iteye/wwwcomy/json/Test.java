package com.iteye.wwwcomy.json;

public class Test {
	public static void main(String[] args) {
	}
}

class Parent {
	public static String getName() {
		return "Parent";
	}
}

class Child extends Parent {
	public static String getName() {
		return "Child";
	}
}