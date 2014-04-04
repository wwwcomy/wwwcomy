package com.iteye.wwwcomy.designpattern.decorator;

public class Whip extends Condiment {

	private Beverage beverage;

	public Whip(Beverage beverage) {
		this.setBeverage(beverage);
	}

	@Override
	public String getDesc() {
		return beverage.getDesc() + ", Whip";
	}

	@Override
	public double getCost() {
		return beverage.getCost() + 0.10;
	}

	public Beverage getBeverage() {
		return beverage;
	}

	public void setBeverage(Beverage beverage) {
		this.beverage = beverage;
	}

}
