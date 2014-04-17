package com.iteye.wwwcomy.designpattern.state;

public class SoldState implements State {
	private GumballMachine gumballMachine;

	public SoldState(GumballMachine machine) {
		this.gumballMachine = machine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("投入了25分");
		gumballMachine.setCurState(gumballMachine.getHasQuarterState());
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
		gumballMachine.releaseBall();
	}

}
