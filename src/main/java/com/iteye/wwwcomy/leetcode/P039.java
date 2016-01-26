package com.iteye.wwwcomy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Combination Sum
 * 
 * @author wwwcomy
 *
 */
public class P039 {
    public static void main(String[] args) {
        int[] src = new int[] { 10, 1, 2, 7, 6, 1, 5 };
        src = new int[] { 8, 7, 4, 3 };
        int target = 11;
        List<List<Integer>> result = new P039().combinationSum(src, target);
        System.out.println(result);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        dfs(candidates, result, target, 0, 0, new ArrayList<Integer>());
        return result;
    }

    private void dfs(int[] candidates, List<List<Integer>> result, int target, int index, int sum, List<Integer> tmp) {
        if (sum == target) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        } else if (sum < target) {
            for (int i = index; i < candidates.length; i++) {
                tmp.add(candidates[i]);
                dfs(candidates, result, target, i, sum + candidates[i], tmp);
                tmp.remove(tmp.size() - 1);
            }
        } else {
            return;
        }
    }
}
