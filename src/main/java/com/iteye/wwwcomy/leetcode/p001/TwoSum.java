package com.iteye.wwwcomy.leetcode.p001;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
	/**
	 * Brute-force 暴力破解
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			// if (nums[i] >= target)
			// continue;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target)
					return new int[] { i + 1, j + 1 };
			}
		}
		// should not run to this line
		return new int[] { nums.length, nums.length };
	}

	/**
	 * 用Map,牺牲空间,减少一层循环
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int[] twoSum2(int[] nums, int target) {
		Map map = new HashMap();
		for (int i = 0; i < nums.length; i++) {
			if (map.get(target - nums[i]) != null)
				return new int[] { (Integer) map.get(target - nums[i]), i + 1 };
			map.put(nums[i], i + 1);
		}
		return new int[] { nums.length, nums.length };
	}
}
