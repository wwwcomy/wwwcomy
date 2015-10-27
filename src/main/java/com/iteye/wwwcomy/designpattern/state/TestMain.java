package com.iteye.wwwcomy.designpattern.state;

public class TestMain {
	public static void main(String[] args) {

		GumballMachine machine = new GumballMachine();
		machine.insertQuarter();
		machine.ejectQuarter();

		machine.insertQuarter();
		machine.turnCrank();
		machine.ejectQuarter();
		machine.turnCrank();
	}
}
