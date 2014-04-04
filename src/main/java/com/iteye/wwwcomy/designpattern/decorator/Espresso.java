package com.iteye.wwwcomy.designpattern.decorator;

public class Espresso extends Beverage {

	public Espresso() {
		desc = "Espresso";
	}

	@Override
	public double getCost() {
		return 1.99;
	}

}
