package com.iteye.wwwcomy.designpattern.decorator;

public class Soy extends Condiment {

	private Beverage beverage;

	public Soy(Beverage beverage) {
		this.setBeverage(beverage);
	}

	@Override
	public String getDesc() {
		return beverage.getDesc() + ", Soy";
	}

	@Override
	public double getCost() {
		return beverage.getCost() + 0.15;
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public void setBeverage(Beverage beverage) {
		this.beverage = beverage;
	}

}
