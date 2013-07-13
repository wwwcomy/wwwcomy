package com.iteye.wwwcomy.designpattern.factory;

public class PlaneFactory extends VehicleFactory {
	@Override
	moveable create() {
		return new Plane();
	}
}
