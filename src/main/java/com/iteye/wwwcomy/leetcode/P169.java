package com.iteye.wwwcomy.leetcode;

import java.util.HashMap;
import java.util.Map;

public class P169 {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int majorityElement(int[] nums) {
		int halfLen = nums.length / 2;
		Map map = new HashMap();
		for (int i = 0; i < nums.length; i++) {
			Object o = map.get(nums[i]);
			if (o == null) {
				map.put(nums[i], 1);
			} else {
				int times = (int) o;
				if ((times + 1) > halfLen) {
					return nums[i];
				} else {
					map.put(nums[i], times + 1);
				}
			}
		}
		// throw
		return nums[0];
	}
}
