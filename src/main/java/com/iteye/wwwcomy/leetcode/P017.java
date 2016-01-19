package com.iteye.wwwcomy.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Letter Combinations of a Phone Number
 * 
 * @author wwwcomy
 *
 */
public class P017 {
    public static void main(String[] args) {
        System.out.println(new P017().letterCombinations("23"));
        System.out.println(new P017().letterCombinations1("23"));
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<String>();
        if (digits == null || "".equals(digits)) {
            return result;
        }
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list2 = new ArrayList<String>();
        list2.add("a");
        list2.add("b");
        list2.add("c");
        map.put("2", list2);
        list2 = new ArrayList<String>();
        list2.add("d");
        list2.add("e");
        list2.add("f");
        map.put("3", list2);
        list2 = new ArrayList<String>();
        list2.add("g");
        list2.add("h");
        list2.add("i");
        map.put("4", list2);
        list2 = new ArrayList<String>();
        list2.add("j");
        list2.add("k");
        list2.add("l");
        map.put("5", list2);
        list2 = new ArrayList<String>();
        list2.add("m");
        list2.add("n");
        list2.add("o");
        map.put("6", list2);
        list2 = new ArrayList<String>();
        list2.add("p");
        list2.add("q");
        list2.add("r");
        list2.add("s");
        map.put("7", list2);
        list2 = new ArrayList<String>();
        list2.add("t");
        list2.add("u");
        list2.add("v");
        map.put("8", list2);
        list2 = new ArrayList<String>();
        list2.add("w");
        list2.add("x");
        list2.add("y");
        list2.add("z");
        map.put("9", list2);

        char[] chars = digits.toCharArray();
        result = dfs(chars, 0, map);
        return result;
    }

    List<String> dfs(char[] chars, int idx, Map<String, List<String>> map) {
        List<String> result = new ArrayList<String>();
        if (idx == chars.length - 1) {
            for (String s : map.get(String.valueOf(chars[idx]))) {
                result.add(s);
            }
            return result;
        }
        List<String> tmp = dfs(chars, idx + 1, map);
        for (String s : map.get(String.valueOf(chars[idx]))) {
            for (String s1 : tmp) {
                result.add(s + s1);
            }
        }
        return result;
    }

    /**
     * Second solution, only did some tricky things on String holder.
     * 
     * @param digits
     * @return
     */
    public List<String> letterCombinations1(String digits) {
        List<String> result = new ArrayList<String>();
        if (digits == null || "".equals(digits)) {
            return result;
        }
        String[] staticString = new String[] { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        char[] chars = digits.toCharArray();
        result = dfs1(chars, 0, staticString);
        return result;
    }

    List<String> dfs1(char[] chars, int idx, String[] staticString) {
        List<String> result = new ArrayList<String>();
        if (idx == chars.length - 1) {
            for (char s : staticString[Integer.valueOf(String.valueOf(chars[idx]))].toCharArray()) {
                result.add(String.valueOf(s));
            }
            return result;
        }
        List<String> tmp = dfs1(chars, idx + 1, staticString);
        for (char s : staticString[Integer.valueOf(String.valueOf(chars[idx]))].toCharArray()) {
            for (String s1 : tmp) {
                result.add(String.valueOf(s) + s1);
            }
        }
        return result;
    }
}
