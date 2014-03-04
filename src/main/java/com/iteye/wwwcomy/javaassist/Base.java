package com.iteye.wwwcomy.javaassist;

public class Base {
	
	private int val;
	
	public Base(Integer val) {
		this.val = val;
	}
	
	public int add(int i) {
		val +=i;
		
		return val;
	}
	
	@Override
	public String toString() {
		return val+"";
	}
}
