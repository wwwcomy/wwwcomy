package com.iteye.wwwcomy.leetcode;

/**
 * Reverse Words in a String
 * 
 * @author liuxingn
 *
 */
public class P151 {

	public static void main(String[] args) {
		System.out.println("'" + new P151().reverseWords("abc ef gh") + "'");
		System.out.println("'" + new P151().reverseWords(" ") + "'");
		System.out.println("'" + new P151().reverseWords("  ") + "'");
		System.out.println("'" + new P151().reverseWords("abc   ef   gh") + "'");
		System.out.println("'" + new P151().reverseWords("  abc   ef   gh  ") + "'");

		// System.out.println("abc ef gh".substring(7,9));
		System.out.println("'" + new P151().reverseWords2("abc ef gh") + "'");
		System.out.println("'" + new P151().reverseWords2(" ") + "'");
		System.out.println("'" + new P151().reverseWords2("  ") + "'");
		System.out.println("'" + new P151().reverseWords2("  abc   ef   gh  ") + "'");
		System.out.println("'" + new P151().reverseWords2("a") + "'");
		System.out.println("'" + new P151().reverseWords2("") + "'");
	}

	public String reverseWords(String s) {
		char[] chars = s.toCharArray();
		char blankChar = (char) 32;
		int charLen = chars.length;
		char[] newChars = new char[charLen];
		int lastCharIdx = 0;
		int reverseIdx = charLen;
		for (int i = 0; i < charLen; i++) {
			if (chars[i] == blankChar) {
				if (i == lastCharIdx) {
					lastCharIdx = i + 1;
					reverseIdx++;
					continue;
				}
				for (int j = 0; j < i - lastCharIdx; j++) {
					newChars[reverseIdx - lastCharIdx - 1 - j] = chars[i - 1 - j];
				}
				newChars[reverseIdx - 1 - i] = chars[i];
				lastCharIdx = i + 1;
			} else {

			}
		}
		for (int j = lastCharIdx; j < charLen; j++) {
			newChars[reverseIdx - 1 - j] = chars[lastCharIdx + charLen - 1 - j];
		}
		return new String(newChars).trim();

	}

	/**
	 * 单次遍历 从后往前 遇到单个空格就在后面append
	 * 
	 * @param s
	 * @return
	 */
	public String reverseWords2(String s) {
		char[] chars = s.toCharArray();
		int charLen = chars.length;
		if (charLen == 0)
			return "";
		char blankChar = (char) 32;
		StringBuilder sb = new StringBuilder();
		int lastBIdx = charLen - 1;
		for (int i = charLen - 1; i > -1; i--) {
			if (chars[i] == blankChar) {
				if (i == lastBIdx) {
					lastBIdx = i - 1;
					continue;
				}
				sb.append(s.substring(i + 1, lastBIdx + 1) + " ");
				lastBIdx = i - 1;
			} else {
			}
		}
		if (chars[0] != blankChar)
			sb.append(s.substring(0, lastBIdx + 1) + " ");
		return sb.toString().trim();
	}
}
