package com.iteye.wwwcomy.designpattern.strategy;

public class NotQuack implements IQuack {

	@Override
	public void quack() {
		System.err.println("The duck cannot Quack");
	}

}
