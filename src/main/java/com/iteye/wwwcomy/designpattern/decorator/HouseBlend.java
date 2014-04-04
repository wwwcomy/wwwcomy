package com.iteye.wwwcomy.designpattern.decorator;

public class HouseBlend extends Beverage {
	public HouseBlend() {
		desc = "HouseBlend";
	}

	@Override
	public double getCost() {
		return 0.98;
	}
}
