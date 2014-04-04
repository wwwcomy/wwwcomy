package com.iteye.wwwcomy.designpattern.decorator;

public abstract class Beverage {
	protected String desc = "Unknown Beverage";

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public abstract double getCost();
}
