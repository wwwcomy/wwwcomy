package com.iteye.wwwcomy.leetcode;

import java.util.Arrays;

public class P080 {
	public static void main(String[] args) {
		int[] nums = new int[] { 1, 1, 1, 2, 2, 3 };
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
		nums = new int[] { 0, 0, 1, 1, 1, 1, 2, 3, 3 };
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
		nums = new int[] { 1, 1, 1, 2, 2, 2, 3, 3 };
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
		nums = new int[] { 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3 };
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
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
