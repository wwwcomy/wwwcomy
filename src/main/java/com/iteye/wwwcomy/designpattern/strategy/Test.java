package com.iteye.wwwcomy.designpattern.strategy;

/**
 * 测试类
 * 
 * @author wwwcomy
 * 
 */
public class Test {

	public static void main(String[] args) {
		Duck duck = new RedDuck();
		duck.swim();
		duck.quack();
		duck = new RubberDuck();
		duck.swim();
		duck.quack();
	}

}
