package com.iteye.wwwcomy.datastructure;

/**
 * 汉诺塔, solve the problem using recursive method
 * 
 * @author wwwcomy
 *
 */
public class TowerApp {

	public static void main(String[] args) {
		doTower(3, 'A', 'B', 'C');
	}

	public static void doTower(int i, char from, char inter, char to) {
		if (i == 1) {
			System.out.println("Moving the 1st plate from " + from + " to " + to);
		} else {
			// first step is to move the i-1 plates from the A plate to the B
			// plate(naming using the first call, as it may change in the inner
			// calls)
			doTower(i - 1, from, to, inter);
			// then move the largest plate from A plate to the C plate
			System.out.println("Moving the " + i + "th plate from " + from + " to " + to);
			// at last, move the i-1 plate from B plate to the C plate
			doTower(i - 1, inter, from, to);
		}
	}

}
