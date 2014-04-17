package com.iteye.wwwcomy.designpattern.state;

/**
 * 糖果机
 * 
 * @author wwwcomy
 * 
 */
public class GumballMachine {

	private HasQuarterState hasQuarterState;
	private NoQuarterState noQuarterState;
	private SoldOutState soldOutState;
	private SoldState soldState;

	private State curState;

	/**
	 * 现有的糖果数
	 */
	private int count = 5;

	public GumballMachine() {
		hasQuarterState = new HasQuarterState(this);
		noQuarterState = new NoQuarterState(this);
		soldOutState = new SoldOutState(this);
		soldState = new SoldState(this);
		curState = new NoQuarterState(this);
	}

	public void insertQuarter() {
		curState.insertQuarter();
	}

	public void ejectQuarter() {
		curState.ejectQuarter();
	}

	public void turnCrank() {
		curState.turnCrank();
		curState.dispense();
	}

	public void releaseBall() {
		System.out.println("已经给糖");
		if (count > 0)
			count--;
	}

	public State getHasQuarterState() {
		return hasQuarterState;
	}

	public void setHasQuarterState(HasQuarterState hasQuarterState) {
		this.hasQuarterState = hasQuarterState;
	}

	public NoQuarterState getNoQuarterState() {
		return noQuarterState;
	}

	public void setNoQuarterState(NoQuarterState noQuarterState) {
		this.noQuarterState = noQuarterState;
	}

	public SoldOutState getSoldOutState() {
		return soldOutState;
	}

	public void setSoldOutState(SoldOutState soldOutState) {
		this.soldOutState = soldOutState;
	}

	public SoldState getSoldState() {
		return soldState;
	}

	public void setSoldState(SoldState soldState) {
		this.soldState = soldState;
	}

	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
