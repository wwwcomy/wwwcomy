package com.iteye.wwwcomy.designpattern.decorator;

public class Mocha extends Condiment {

	private Beverage beverage;

	public Mocha(Beverage beverage) {
		this.setBeverage(beverage);
	}

	@Override
	public String getDesc() {
		return beverage.getDesc() + ", Mocha";
	}

	@Override
	public double getCost() {
		return beverage.getCost() + 0.20;
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public void setBeverage(Beverage beverage) {
		this.beverage = beverage;
	}

}
