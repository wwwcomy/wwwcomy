package com.iteye.wwwcomy.designpattern.strategy;

public class NotSwim implements ISwim {

	@Override
	public void swim() {
		System.err.println("The duck cannot swimming");
	}

}
