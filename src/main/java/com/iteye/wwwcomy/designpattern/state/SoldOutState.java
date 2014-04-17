package com.iteye.wwwcomy.designpattern.state;

public class SoldOutState implements State {
	@SuppressWarnings("unused")
	private GumballMachine gumballMachine;

	public SoldOutState(GumballMachine machine) {
		this.gumballMachine = machine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("没糖了");

	}

	@Override
	public void ejectQuarter() {
		System.out.println("还没投");

	}

	@Override
	public void turnCrank() {
		System.out.println("还没投");

	}

	@Override
	public void dispense() {
		System.out.println("还没投");

	}

}
