package com.iteye.wwwcomy.designpattern.state;

public class NoQuarterState implements State {

	private GumballMachine gumballMachine;

	public NoQuarterState(GumballMachine machine) {
		this.gumballMachine = machine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("投入了25分");
		gumballMachine.setCurState(gumballMachine.getHasQuarterState());
	}

	@Override
	public void ejectQuarter() {
		System.out.println("你还没投入钱");
	}

	@Override
	public void turnCrank() {
		System.out.println("你还没投入钱，不能玩");
	}

	@Override
	public void dispense() {
		System.out.println("你还没投入钱，不能发糖");
	}

}
