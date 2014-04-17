package com.iteye.wwwcomy.designpattern.state;

public class HasQuarterState implements State {

	private GumballMachine gumballMachine;

	public HasQuarterState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("你已经投了钱了");
	}

	@Override
	public void ejectQuarter() {
		System.out.println("退钱，请收好");
		gumballMachine.setCurState(gumballMachine.getNoQuarterState());
	}

	@Override
	public void turnCrank() {
		System.out.println("拉杆");

	}

	@Override
	public void dispense() {
		System.out.println("出糖，请收好");
		gumballMachine.setCurState(gumballMachine.getNoQuarterState());

	}

}
