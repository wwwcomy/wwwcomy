package com.iteye.wwwcomy.designpattern.strategy;

/**
 * 策略模式 基类
 * 
 * @author wwwcomy
 * 
 */
public abstract class Duck {

	private ISwim swim;
	private IQuack quack;

	public Duck() {
		this.swim = new Swim();
		this.quack = new Quack();
	}

	public void quack() {
		quack.quack();
	}

	public void swim() {
		swim.swim();
	}

	public ISwim getSwim() {
		return swim;
	}

	public void setSwim(ISwim swim) {
		this.swim = swim;
	}

	public IQuack getQuack() {
		return quack;
	}

	public void setQuack(IQuack quack) {
		this.quack = quack;
	}
}
