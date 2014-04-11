package com.iteye.wwwcomy.designpattern.factory;

public class BroomFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Broom();
	}

}
