package com.iteye.wwwcomy.designpattern.factory.abstractfactory;

public class DefaultFactory {
	public Car createCar() {
		return new Car();
	}

	public AK47 createAK() {
		return new AK47();
	}

	public Food createFood() {
		return new Food();
	}
}
