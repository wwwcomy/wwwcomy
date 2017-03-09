package com.iteye.wwwcomy.datastructure;

import java.util.ArrayList;
import java.util.List;

import com.iteye.wwwcomy.leetcode.P046;

/**
 * 变位字，输出某个单词的排列。Anagram? 递归解决 permutation
 * 
 * Step1暂时不处理有重复字符的单词
 * 
 * @author wwwcomy
 * @see P046
 *
 */
public class ShiftWord {

	public static void main(String[] args) {
		char[] c = "cats".toCharArray();
		// arrange(c);

		doArr2(c, c.length);
	}

	@SuppressWarnings({ "rawtypes" })
	private static void arrange(char[] chars) {
		List result = doArr(chars, chars.length);
		int i = 1;
		for (Object o : result) {
			System.out.print(i++ + " ");
			char[] cs = (char[]) o;
			for (char c : cs) {
				System.out.print(c + ",");
			}
			System.out.println();
		}
	}

	/**
	 * This is the same solution as P046 does
	 * 
	 * @param chars
	 * @param size
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List doArr(char[] chars, int size) {
		ArrayList list = new ArrayList();
		if (size == 1) {
			list.add(chars);
			return list;
		}
		for (int i = 0; i < chars.length; i++) {
			char[] innerInput = new char[chars.length - 1];
			// the following array copy will extract the chars[i] out, and
			// create a new char array with the residue chars
			System.arraycopy(chars, 0, innerInput, 0, i);
			if (i < chars.length - 1) {
				System.arraycopy(chars, i + 1, innerInput, i, chars.length - i - 1);
			}
			List innerResult = doArr(innerInput, size - 1);
			// with the recursive calculated results, we should add that result
			// as a suffix of the previous chars[i]
			for (Object o : innerResult) {
				char[] cs = (char[]) o;
				char[] tmp1 = new char[cs.length + 1];
				tmp1[0] = chars[i];
				System.arraycopy(cs, 0, tmp1, 1, cs.length);
				list.add(tmp1);
			}
		}
		return list;
	}

	private static void doArr2(char[] chars, int newSize) {
		if (newSize == 1) {
			return;
		}
		for (int i = 0; i < newSize; i++) {
			doArr2(chars, newSize - 1);
			if (newSize == 2) {
				for (char c : chars) {
					System.out.print(c + ",");
				}
				System.out.println();
			}
			rotate(chars, newSize);
		}
	}

	private static void rotate(char[] chars, int newSize) {
		int position = chars.length - newSize;
		char tmp = chars[position];
		int i;
		for (i = position + 1; i < chars.length; i++) {
			chars[i - 1] = chars[i];
		}
		chars[i - 1] = tmp;
	}

}
