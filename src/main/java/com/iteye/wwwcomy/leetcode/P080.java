package com.iteye.wwwcomy.leetcode;

public class P080 {
	public static void main(String[] args) {
		System.out.println(Math.pow(16, 0.25));
		double d = (double) Math.pow(2, 0.1);
		System.out.println(d);
		System.out.println(Math.pow(1.07, 10));
		System.out.println(Math.pow(1.9672, 0.1));
		System.out.println(Math.pow(1.0958, 10));
		System.out.println(Math.pow(2.5, 0.1));

	}

	public static int removeDuplicates(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		int result = 1;
		int currentNum = nums[0];
		int counter = 1;
		for (int i = 1; i < nums.length; i++) {
			if (currentNum == nums[i]) {
				counter++;
			} else {
				currentNum = nums[i];
				counter = 1;
			}
			if (counter <= 2) {
				nums[result] = nums[i];
				result++;
			}
		}
		return result;
	}
}
