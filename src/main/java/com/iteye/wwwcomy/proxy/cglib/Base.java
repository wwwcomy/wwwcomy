package com.iteye.wwwcomy.proxy.cglib;

public class Base {
	private int val;

	public Base() {
		
	}

	public Base(Integer val) {
		this.val = val;
	}

	public void add() {
		System.out.println("add......" + val);
	}
	
	@Override
	public String toString() {
		return "" + val;
	}
}
