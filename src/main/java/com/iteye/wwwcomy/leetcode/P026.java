package com.iteye.wwwcomy.leetcode;

import java.util.Arrays;

public class P026 {
	public static void main(String[] args) {
		int[] nums = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
	}

	public static int removeDuplicates(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		int result = 1;
		int lastInt = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[lastInt]) {
				lastInt = i;
				nums[result] = nums[lastInt];
				result++;
			}
		}
		return result;
	}
}
