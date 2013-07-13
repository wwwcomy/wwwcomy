package com.iteye.wwwcomy.designpattern.factory;

public class CarFactory extends VehicleFactory {
	public moveable create() {
		return new Car();
	}
}
