package com.iteye.wwwcomy;

/**
 * 奇怪的内部类
 * 
 * @author Liuxn
 * 
 */
public class Twisted {

	private final String name;

	Twisted(String name) {
		this.name = name;
	}

	private String name() {
		return name;
	}

	private void reproduce() {
		new Twisted("reproduce") {
			public void printName() {
				System.out.println(name());
			}
		}.printName();
	}

	public static void main(String[] args) {
		new Twisted("main").reproduce();
	}

}
