package com.iteye.wwwcomy.designpattern.factory;

public class Test {

	public static void main(String[] args) {
		// Car c = Car.getInstance();
		// Car ce = Car.getInstance();
		// if (c == ce)
		VehicleFactory factory = new BroomFactory();
		moveable m = factory.create();
		m.run();
	}
}
