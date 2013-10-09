package com.iteye.wwwcomy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestKmp2 {

	public static void main(String[] args) {
		// KMP in ?Introduction to algorithms?
		String P = "ab";
		String T = "1ababababca23ababababca456ababababca";
		kmpMatches(T, P);

		System.out.println();

		// JDK RegExp
		Pattern pattern = Pattern.compile(P);
		Matcher matcher = pattern.matcher(T);
		while (matcher.find()) {
			System.out.println("Matches at index:" + matcher.start());
		}
	}

	// Introduction to algorithms
	private static int[] getPrefixFunction(String P) {
		int m = P.length();
		int[] PI = new int[m];
		if (m > 0) {
			PI[0] = 0;
		}
		int k = 0; // max length of Pk )) Pi+1
		for (int i = 1; i < m; i++) {
			while (k > 0 && P.charAt(k) != P.charAt(i)) {
				k = PI[k - 1];
			}
			if (P.charAt(k) == P.charAt(i)) {
				k++;
			}

			PI[i] = k;
		}

		return PI;
	}

	/**
	 * KMP in Introduction to algorithms
	 * 
	 * @param T
	 *            target string
	 * @param P
	 *            pattern string, the string to be searched in target
	 */
	public static void kmpMatches(String T, String P) {
		int m = P.length();
		int n = T.length();
		int[] PI = getPrefixFunction(P);
		int k = 0; // max length
		for (int i = 0; i < n; i++) {
			while (k > 0 && P.charAt(k) != T.charAt(i)) {
				k = PI[k - 1];
			}
			if (m > 0 && P.charAt(k) == T.charAt(i)) {
				k++;
			}
			if (k == m) {
				System.out.println("Matches at index:" + (i - m + 1));
				if (m > 0) {
					k = PI[k - 1];
				}
			}
		}
	}
}