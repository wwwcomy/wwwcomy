package com.iteye.wwwcomy.designpattern.factory;

public class PlaneFactory extends VehicleFactory {
	@Override
	Moveable create() {
		return new Plane();
	}
}
