package com.iteye.wwwcomy.test;

import java.math.BigDecimal;

import com.iteye.wwwcomy.lxn.utils.VarUtil;

/**
 * 大整数相乘
 * 
 * @author wwwcomy
 * 
 */
public class TestBigMul {
	public static void main(String[] args) {
		String num1 = "345";
		String num2 = "43";

		num1 = "92345678998765432199999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";
		num2 = "92345678998765432112345678998765923456789987654321999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999994321";
		String s = multiply(num1, num2);
		
		BigDecimal b1 = new BigDecimal(s);
		BigDecimal b2 = new BigDecimal(num1).multiply(new BigDecimal(num2));
		
		System.out.println(b1.equals(b2));
	}

	public static String multiply(String num1, String num2) {
		char[] c1 = num1.toCharArray();
		char[] c2 = num2.toCharArray();
		int size1 = c1.length;
		int size2 = c2.length;
		int totalSize = num1.length() + num2.length();
		int[] tmpArray = new int[totalSize];
		// for (char c : c1) {
		// System.out.println(c);
		// }
		// for (char c : c2) {
		// System.out.println(c);
		// }

		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				tmpArray[i + j] += VarUtil.toInt(String.valueOf(c1[i]))
						* VarUtil.toInt(String.valueOf(c2[j]));
			}
		}
		// for (int i : tmpArray) {
		// System.out.println(i);
		// }
		String[] result = new String[totalSize];
		for (int i = tmpArray.length - 2; i >= 0; i--) {
			int ten = tmpArray[i] / 10;
			int rem = tmpArray[i] % 10;
			result[i + 1] = String.valueOf(rem);
			if (i - 1 < 0) {
				result[i + 1] = String.valueOf(rem);
				result[i] = String.valueOf(ten == 0 ? "" : ten);
			} else {
				tmpArray[i - 1] += ten;
			}
		}

		String ret = "";
		for (String s : result) {
			ret += s;
		}
		return ret;
		
	}
}
