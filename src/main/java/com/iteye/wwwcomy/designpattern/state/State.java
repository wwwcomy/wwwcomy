package com.iteye.wwwcomy.designpattern.state;

/**
 * 状态接口
 * 
 * @author wwwcomy
 * 
 */
public interface State {

	/**
	 * 投入25分
	 */
	public void insertQuarter();

	/**
	 * 退回25分
	 */
	public void ejectQuarter();

	/**
	 * 拉动曲柄
	 */
	public void turnCrank();

	/**
	 * 发放糖果
	 */
	public void dispense();

}
