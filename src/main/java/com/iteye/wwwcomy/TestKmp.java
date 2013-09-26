package com.iteye.wwwcomy;

import java.util.Arrays;

/**
 * 测试Kmp算法:克努特Knuth-莫里斯Morris-普拉特Pratt
 * 
 * @author wwwcomy
 * 
 */
public class TestKmp {
	public static String s1 = "abcabcabdabbabcabd";
	public static String s2 = "agctagcagctagctg";

	public static void main(String[] args) {
		// index(s1, s2);
		// indexByKmp(s1, s2);
		// indexByKmp2(s1, s2);
		int[] a = getNextArray(s2);
		System.out.println(Arrays.toString(a));
	}

	/**
	 * 最原始的在s1中查找s2的方法，循环很多，不推荐。 一旦匹配失败，就回退到初始位置的下一个位置。
	 * 
	 * @param s1
	 * @param s2
	 */
	public static void index(String s1, String s2) {
		int s1Len = s1.length();
		int s2Len = s2.length();
		if (s1Len < s2Len)
			return;
		for (int i = 0; i <= s1Len - s2Len; i++) {
			boolean bContain = true;
			for (int j = 0; j < s2Len; j++) {
				if (s1.charAt(i + j) != s2.charAt(j)) {
					bContain = false;
					break;
				}
			}
			if (bContain) {
				System.out.println("S1包含S2串，包含起点Index为：" + i);
			}
		}
	}

	public static int[] getNextArray(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = 0;
		int i = 1;
		while (i < len) {
			if (pattern.charAt(i) == pattern.charAt(next[i - 1])) {
				next[i] = next[i - 1] + 1;
			} else {
				if(pattern.charAt(i) == pattern.charAt(0))
					next[i] = 1;
				else
					next[i] = 0;
			}
			i++;
		}
		return next;
	}

	public static int indexByKmp(String s1, String s2) {
		int s1Len = s1.length();
		int s2Len = s2.length();
		int i = 0;
		int j = 0;
		int[] next = getNextArray(s2);
		while (i < s1Len) {
			if (j == -1 || s1.charAt(i) == s2.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];
			}
			if (j == s2Len)
				System.out.println("结果是" + (i - s2Len));
		}
		return -1;
	}

	public static int[] getNextArray2(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = -1;
		int i = 0, j = -1;
		while (i < len - 1) {
			if (j == -1 || pattern.charAt(j) == pattern.charAt(i)) {
				++i;
				++j;
				if (pattern.charAt(j) != pattern.charAt(i))
					next[i] = j;
				else
					next[i] = next[j];
			} else
				j = next[j];
		}
		return next;
	}

	public static int indexByKmp2(String s1, String s2) {
		int s1Len = s1.length();
		int s2Len = s2.length();
		int i = 0;
		int j = 0;
		int[] next = getNextArray2(s2);
		System.out.println(Arrays.toString(next));
		while (i < s1Len && j < s2Len) {
			if (j == -1 || s1.charAt(i) == s2.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];
			}
			if (j == s2Len)
				System.out.println("结果是" + (i - s2Len));
		}
		return -1;
	}
}
