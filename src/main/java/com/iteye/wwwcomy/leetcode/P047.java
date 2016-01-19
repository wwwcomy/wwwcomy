package com.iteye.wwwcomy.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Permutations II
 * 
 * unique permutations
 * 
 * @author liuxingn
 *
 */
public class P047 {
    public static void main(String[] args) {
        List<List<Integer>> result = new P047().permuteUnique(new int[] { 1, 2, 3 });
        System.out.println(result);
        result = new P047().permuteUnique(new int[] { 1, 1, 3 });
        System.out.println(result);

        result = new P047().permute(new int[] { 1, 1, 2, 2 });
        System.out.println(result);
    }

    public List<List<Integer>> permuteUnique(int[] is) {
        return permuteInner(is, 0);
    }

    public List<List<Integer>> permuteInner(int[] is, int index) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (index == is.length) {
            List<Integer> curr = new ArrayList<Integer>();
            result.add(curr);
            return result;
        }

        List<List<Integer>> prev = permuteInner(is, index + 1);
        Set<String> cache = new HashSet<String>();

        for (List<Integer> list : prev) {
            for (int i = 0; i <= list.size(); i++) {
                List<Integer> newList = new ArrayList<Integer>(list);
                newList.add(i, is[index]);
                if (cache.add(newList.toString())) {
                    result.add(newList);
                }
            }
        }
        return result;
    }

    /**
     * Method2 Swap each one with other chars in the array, like point 2 in the
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
            // use a cache to filter the used integer out, so that no swap is
            // needed
            Set<Integer> used = new HashSet<Integer>();
            for (int j = i + 1; j < tmp.size(); j++) {
                if (used.add(tmp.get(j)) && tmp.get(j) != tmp.get(i)) {
                    List<Integer> l = swap(tmp, i, j);
                    next.add(l);
                    initList.add(l);
                }
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
