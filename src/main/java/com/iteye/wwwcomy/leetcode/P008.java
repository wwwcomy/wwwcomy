package com.iteye.wwwcomy.leetcode;

/**
 * String to Integer
 * 
 * @author liuxingn
 *
 */
public class P008 {
	/**
	 * 第一个想出来的解决方案。。。 应试教育产物。。。
	 * 
	 * @param str
	 * @return
	 */
	public int myAtoi(String str) {
		try {
			return Integer.valueOf(str.trim());
		} catch (Exception e) {
			String t = "\\s*([\\+-]?\\d+).*";
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(t);
			java.util.regex.Matcher m = p.matcher(str);
			if (m.matches()) {
				try {
					return Integer.valueOf(m.group(1));
				} catch (Exception e1) {
					if (str.trim().startsWith("-")) {
						return Integer.MIN_VALUE; // -2147483648
					} else {
						return Integer.MAX_VALUE; // 2147483647
					}
				}
			} else {
				return 0;
			}
		}
	}

	/**
	 * 按照字符处理，注意Integer的边界处理
	 * 
	 * @param str
	 * @return
	 */
	public int myAtoi2(String str) {
		char[] chars = str.trim().toCharArray();
		boolean lessThanZ = false;
		int tmp = 0;
		if (chars.length == 0) {
			return 0;
		}
		if (chars[0] == (char) 45) {
			lessThanZ = true;
		} else if (chars[0] == (char) 43) {
			// do nothing
		} else if (chars[0] >= 48 && chars[0] <= 59) {
			tmp = chars[0] - 48;
		} else {
			return 0;
		}
		for (int i = 1; i < chars.length; i++) {
			if (chars[i] >= 48 && chars[i] <= 59) {
				int val = chars[i] - 48;
				if (tmp > 214748364) {
					if (lessThanZ) {
						return Integer.MIN_VALUE;
					} else {
						return Integer.MAX_VALUE;
					}
				} else if (tmp == 214748364) {
					if (lessThanZ) {
						if (val > 8) {
							return Integer.MIN_VALUE;
						} else {
							tmp = tmp * 10 + val;
						}
					} else {
						if (val > 7) {
							return Integer.MAX_VALUE;
						} else {
							tmp = tmp * 10 + val;
						}
					}
				} else {
					tmp = tmp * 10 + val;
				}
			} else {
				break;
			}
		}
		if (lessThanZ) {
			return 0 - tmp;
		} else {
			return tmp;
		}
	}

	public static void main(String[] args) {
		System.out.println(new P008().myAtoi2("-2147483647"));
	}
}
