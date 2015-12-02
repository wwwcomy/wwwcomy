package com.iteye.wwwcomy.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Permutations Given a collection of numbers, return all possible permutations.
 * 
 * For example, [1,2,3] have the following permutations: [1,2,3], [1,3,2],
 * [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 * 
 * Solution, see http://blog.csdn.net/tuantuanls/article/details/8717262
 * 
 * @author liuxingn
 *
 */
public class P046 {
	public static void main(String[] args) {
		System.out.println(new P046().permute(new int[] { 1, 2, 3 }));
		System.out.println(new P046().permute(new int[] { 5, 4, 6, 2 }));
	}

	/**
	 * Method1 Swap each one with other chars in the array, like point 2 in the
	 * link http://blog.csdn.net/tuantuanls/article/details/8717262
	 * 
	 * @param nums
	 * @return
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> initList = new ArrayList<List<Integer>>();
		ArrayList<Integer> inputList = new ArrayList<Integer>();
		for (int n : nums) {
			inputList.add(n);
		}
		initList.add(inputList);
		List<List<Integer>> tmpList = new ArrayList<List<Integer>>();
		tmpList.add(inputList);
		int size = nums.length;
		dfs(tmpList, initList, 0, size);
		return initList;
	}

	private void dfs(List<List<Integer>> tmpList, List<List<Integer>> initList, int i, int size) {
		if (i == size)
			return;
		List<List<Integer>> next = new ArrayList<List<Integer>>(tmpList);
		for (List<Integer> tmp : tmpList) {
			for (int j = i + 1; j < tmp.size(); j++) {
				List<Integer> l = swap(tmp, i, j);
				next.add(l);
				initList.add(l);
			}
		}
		dfs(next, initList, i + 1, size);
	}

	private List<Integer> swap(List<Integer> input, int small, int big) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < input.size(); i++) {
			if (i == small) {
				tmp.add(input.get(big));
			} else if (i == big) {
				tmp.add(input.get(small));
			} else {
				tmp.add(input.get(i));
			}
		}
		return tmp;
	}
}
