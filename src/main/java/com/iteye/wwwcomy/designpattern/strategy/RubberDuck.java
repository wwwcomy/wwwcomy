package com.iteye.wwwcomy.designpattern.strategy;

/**
 * 橡胶鸭子
 * 
 * @author wwwcomy
 * 
 */
public class RubberDuck extends Duck {
	public RubberDuck() {
		this.setSwim(new Swim());
		this.setQuack(new NotQuack());
	}
}
