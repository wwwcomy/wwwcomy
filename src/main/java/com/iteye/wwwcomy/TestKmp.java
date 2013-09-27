package com.iteye.wwwcomy;

import java.util.Arrays;

/**
 * 测试Kmp算法:克努特Knuth-莫里斯Morris-普拉特Pratt
 * 
 * @author wwwcomy
 * 
 */
public class TestKmp {
	public static String s1 = "aaaabaa";
	public static String s2 = "aaab";

	public static void main(String[] args) {
		// index(s1, s2);
		// indexByKmp(s1, s2);
		// indexByKmp2(s1, s2);
		int[] a = getNextArray(s2);
		int[] a1 = getNextArrayJuly(s2);
		int[] aEye = getNextArrayEye(s2);

		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(a1));
		System.out.println(Arrays.toString(aEye));

		System.out.println(":::::::::::");
		// 这种情况在计算倒数第二位"t"时，需要特殊处理
		String test = "abctagcabctagctg";
		System.out.println(Arrays.toString(getNextArray(test)));
		System.out.println(Arrays.toString(getNextArrayEye(test)));
		System.out.println(Arrays.toString(getNextArrayReal(test)));

		System.out.println(":::::::::::");
		test = "agctagcagctagctg";
		System.out.println(Arrays.toString(getNextArray(test)));
		System.out.println(Arrays.toString(getNextArrayEye(test)));
		System.out.println(Arrays.toString(getNextArrayReal(test)));

		indexByKmp(s1, s2);
		indexByKmpJuly(s1, s2);
	}

	/**
	 * 最原始的在s1中查找s2的方法，与JDK原始实现类似。 一旦匹配失败，就回退到初始位置的下一个位置。
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

	/**
	 * 在这种next数组的情况下，使用如下公式：移动位数 = 已匹配的字符数 - 对应的部分匹配值
	 * 
	 * 这种方法当失配时，仅仅对比了第一个字符，没有循环向后，
	 * 
	 * @param pattern
	 * @return
	 */
	public static int[] getNextArray(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = 0;
		int i = 1;
		while (i < len) {
			if (pattern.charAt(i) == pattern.charAt(next[i - 1])) {
				next[i] = next[i - 1] + 1;
			} else {
				if (pattern.charAt(i) == pattern.charAt(0))
					next[i] = 1;
				else
					next[i] = 0;
			}
			i++;
		}
		return next;
	}

	/**
	 * 考虑了循环，但是有问题
	 * 
	 * @deprecated 这里在循环的处理上有问题，注意上面的"abctagcabctagctg"和"agctagcagctagctg"例子
	 * @param pattern
	 * @return
	 */
	public static int[] getNextArrayReal(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = 0;
		int i = 1;
		while (i < len) {
			if (pattern.charAt(i) == pattern.charAt(next[i - 1])) {
				next[i] = next[i - 1] + 1;
			} else {
				int j = next[i - 1];
				// TODO wrong Code!!!!!!!!!!!!!!!!
				while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
					j--;
				}
				next[i] = ++j;
			}
			i++;
		}
		return next;
	}

	/**
	 * JavaEye上的另外一种写法，只是下标略有不同
	 * 
	 * @param pattern
	 * @return
	 */
	public static int[] getNextArrayEye(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		next[0] = 0;
		int i = 1;
		for (i = 2; i < len; i++) {
			int j = i;
			while (j > 1) {
				if (pattern.charAt(i - 1) == pattern.charAt(next[j - 1])) {
					next[i] = next[j - 1] + 1;
					break;
				} else {
					j = next[j - 1];
				}
			}
			if (j == 1) {
				next[i] = 1;
			}
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
			if (s1.charAt(i) == s2.charAt(j)) {
				i++;
				j++;
			} else {
				if (j == 0) {
					i++;
					j++;
				}
				j = next[j - 1];
			}
			if (j == s2Len) {
				System.out.println("结果是" + (i - s2Len));
				j = 0;
			}
		}
		return -1;
	}

	/**
	 * July大神写的Next数组求法
	 * 
	 * @param pattern
	 * @return
	 */
	public static int[] getNextArrayJuly(String pattern) {
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

	/**
	 * July大神写的Kmp入口方法
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int indexByKmpJuly(String s1, String s2) {
		int s1Len = s1.length();
		int s2Len = s2.length();
		int i = 0;
		int j = 0;
		int[] next = getNextArrayJuly(s2);
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
