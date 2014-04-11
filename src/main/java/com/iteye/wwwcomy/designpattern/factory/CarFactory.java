package com.iteye.wwwcomy.designpattern.factory;

public class CarFactory extends VehicleFactory {
	public Moveable create() {
		return new Car();
	}
}
