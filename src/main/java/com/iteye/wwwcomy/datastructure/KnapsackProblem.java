package com.iteye.wwwcomy.datastructure;

/**
 * 背包问题, use the dynamic programming (2 dimension arrays)
 * 
 * @author wwwcomy
 *
 */
public class KnapsackProblem {

	public static void main(String[] args) {
		// int weight = 20;
		// int[] objects = new int[] { 11, 8, 7, 6, 5, 22, 33 };
		// solution1(weight, objects);

		solution2();
	}

	/**
	 * My first solution, ugly and only can find one best solution.
	 * 
	 * @param weight
	 * @param objects
	 * @return
	 */
	public static boolean solution1(int weight, int[] objects) {
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

	/**
	 * Use the dynamic programming to solve the knapsack problem
	 * https://www.youtube.com/watch?v=8LusJS5-AGo
	 * 
	 * @return
	 */
	private static void solution2() {
		// int[] products = new int[] { 2, 3, 4, 5 };
		// int[] values = new int[] { 3, 7, 2, 9 };
		// int bagCapacity = 5;
		// int[] products = new int[] { 5, 4, 7, 2, 6 };
		// int[] values = new int[] { 12, 3, 10, 3, 6 };
		// int bagCapacity = 15;
		int[] products = new int[] { 1, 3, 4, 5 };
		int[] values = new int[] { 1, 4, 5, 7 };
		int bagCapacity = 7;
		int[][] twoDimensionArray = new int[products.length + 1][bagCapacity + 1];
		for (int i = 0; i < products.length + 1; i++) {
			for (int j = 0; j < bagCapacity + 1; j++) {
				if (i == 0 || j == 0) {
					twoDimensionArray[i][j] = 0;
					continue;
				}
				// i and j will never be 0 in the below logic
				int productWeight = products[i - 1];
				if (productWeight > j) {
					twoDimensionArray[i][j] = twoDimensionArray[i][j - 1] > twoDimensionArray[i - 1][j]
							? twoDimensionArray[i][j - 1] : twoDimensionArray[i - 1][j];
				} else {
					int weightIfUse = values[i - 1] + twoDimensionArray[i - 1][j - productWeight];
					int weightIfNotUse = twoDimensionArray[i][j - 1] > twoDimensionArray[i - 1][j]
							? twoDimensionArray[i][j - 1] : twoDimensionArray[i - 1][j];
					if (weightIfUse > weightIfNotUse) {
						twoDimensionArray[i][j] = weightIfUse;
					} else {
						twoDimensionArray[i][j] = weightIfNotUse;
					}
				}
			}
		}
		printTwoDimensionArray(twoDimensionArray);
		getAnswer(twoDimensionArray, products, values, bagCapacity);
	}

	private static void printTwoDimensionArray(int[][] twoDimensionArray) {
		for (int i = 0; i < twoDimensionArray.length; i++) {
			for (int j = 0; j < twoDimensionArray[0].length; j++) {
				System.out.print(twoDimensionArray[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void getAnswer(int[][] twoDimensionArray, int[] products, int[] values, int bagCapacity) {
		int j = twoDimensionArray[0].length - 1;
		int i = twoDimensionArray.length - 1;
		while (j > 0) {
			if (twoDimensionArray[i][j] == twoDimensionArray[i - 1][j]) {
				System.out.println("the " + i + "th product is not selected, weight" + products[i - 1] + ", values"
						+ values[i - 1]);
				i = i - 1;
				continue;
			}
			// else if (twoDimensionArray[i][j] == twoDimensionArray[i][j-1]){
			// System.out.println("what happens here?");
			// }
			else {
				System.out.println(
						"the " + i + "th product is selected, weight" + products[i - 1] + ", values" + values[i - 1]);
				i = i - 1;
				j = j - products[i];
			}
		}
	}

}
