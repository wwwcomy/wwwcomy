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
		int[] objects = new int[] { 11, 8, 7, 6, 5 };
		solve(weight, objects);
	}

	private static int[] solve(int weight, int[] objects) {
		if (objects.length == 1) {
			if (weight - objects[0] == 0) {
				System.out.println("got the answer");
				return objects;
			} else if (weight - objects[0] > 0) {
				return null;
				// not the best answer
			} else {
				return null;
				// ignore as the object's weight is too high
			}
		}
		for (int i = 0; i < objects.length; i++) {

			int nextTargetWeight = weight - objects[i];
			if (nextTargetWeight < 0) {
				int[] tmp = new int[objects.length - 1];
				System.arraycopy(objects, 1, tmp, 0, objects.length - 1);
				solve(weight, tmp);
			} else if (nextTargetWeight == 0) {
				System.out.println("one best solution:" + objects[i]);
				return null;
			} else if (nextTargetWeight > 0) {
				int[] tmp = new int[objects.length - 1];
				System.arraycopy(objects, 1, tmp, 0, objects.length - 1);
				solve(nextTargetWeight, tmp);
			}
		}
		return null;
	}

}
