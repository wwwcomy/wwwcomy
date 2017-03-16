package com.iteye.wwwcomy.datastructure;

/**
 * 背包问题
 * 
 * @author wwwcomy
 *
 */
public class KnapsackProblem {

	public static void main(String[] args) {
		int weight = 20;
		int[] objects = new int[] { 11, 8, 7, 6, 5, 22, 33 };
		solution1(weight, objects);
	}

	/**
	 * My first solution, ugly and only can find one best solution.
	 * 
	 * @param weight
	 * @param objects
	 * @return
	 */
	private static boolean solution1(int weight, int[] objects) {
		if (objects.length == 1) {
			if (weight - objects[0] == 0) {
				System.out.println("got the answer:" + objects[0]);
				return true;
			} else if (weight - objects[0] > 0) {
				System.out.println("-----");
				return false;
				// not the best answer
			} else {
				System.out.println("-----");
				return false;
				// ignore as the object's weight is too high
			}
		}
		for (int i = 0; i < objects.length; i++) {
			int nextTargetWeight = weight - objects[i];
			if (nextTargetWeight < 0) {
				int[] tmp = new int[objects.length - 1];
				System.arraycopy(objects, 1, tmp, 0, objects.length - 1);
				boolean result = solution1(weight, tmp);
				if (result) {
					return result;
				}
			} else if (nextTargetWeight == 0) {
				System.out.println("one best solution:" + objects[i]);
				return true;
			} else if (nextTargetWeight > 0) {
				int[] tmp = new int[objects.length - 1];
				System.arraycopy(objects, 1, tmp, 0, objects.length - 1);
				boolean result = solution1(nextTargetWeight, tmp);
				if (result) {
					System.out.println(objects[i]);
					return result;
				}
			}
		}
		return false;
	}

}
