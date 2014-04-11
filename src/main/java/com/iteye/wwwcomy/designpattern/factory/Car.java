package com.iteye.wwwcomy.designpattern.factory;


public class Car implements Moveable {

	private static Car instance;

	public Car() {
	}

	public static Car getInstance() {
		if (instance == null)
			instance = new Car();
		return instance;
	}

	public void run() {
		System.out.println("The car is runnning...");
	}

}
