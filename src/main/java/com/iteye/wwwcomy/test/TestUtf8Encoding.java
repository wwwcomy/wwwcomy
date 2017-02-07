package com.iteye.wwwcomy.test;

import java.nio.charset.Charset;

/**
 * https://www.zhihu.com/question/23374078 UTF-8 encoding test, given one UTF8
 * code, return mapping character.
 * 
 * @author wwwcomy
 *
 */
public class TestUtf8Encoding {
	public static void main(String[] args) {
		// "报"的UTF8编码表中对应的编码
		String a = "62a5";
		// integer.toBinaryString will remove the 0 prefix
		String binary = Integer.toBinaryString(Integer.valueOf(a, 16));
		System.out.println("To binary:" + beautify(binary));
		// 转成2进制后,根据UTF-8的编码规则进行编码，生成一下二进制码
		// 编码规则：
		// 0xxxxxxx
		// 110xxxxx 10xxxxxx
		// 1110xxxx 10xxxxxx 10xxxxxx
		// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
		// 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
		// 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
		String bi = getUtf8Binary(binary);
		System.out.println("After UTF-8 encoding:" + beautify(bi));

		String result = generateResult(bi);
		System.out.println("Result is:" + result);
	}

	/**
	 * 写成一个动态的，这样就是随便写一个16进制码，先转成2进制，然后自动生成UTF编码后的二进制码，然后生成String
	 * 
	 * @param initbinary
	 * @return
	 */
	private static String getUtf8Binary(String initBinary) {
		int len = initBinary.length();
		StringBuilder sb = new StringBuilder();
		if (len <= 7) {
			sb.append("0");
			for (int i = (7 - len); i > 0; i--) {
				sb.append(0);
			}
			sb.append(initBinary);
		} else {
			int unicodeHolder = len / 6;
			for (int i = 0; i < unicodeHolder; i++) {
				sb.insert(0, "10" + initBinary.substring(len - 6 * (i + 1), len - 6 * (i + 1) + 6));
			}
			sb.insert(0, initBinary.substring(0, len % 6));
			// 这是最高位要补0的个数，假如除以六得到2，即肯定需要2个字节了，那么前面需要"11"，再加上"10"，再减去除以6的余数
			int remainder = 8 - unicodeHolder - 2 - len % 6;
			for (int i = 0; i < remainder; i++) {
				sb.insert(0, "0");
			}
			sb.insert(0, "10");
			for (int i = 0; i < unicodeHolder; i++) {
				sb.insert(0, "1");
			}
		}
		return sb.toString();
	}

	/**
	 * 按照解码方式生成字符,也是个动态的
	 * 
	 * @param bi
	 * @return
	 */
	private static String generateResult(String bi) {
		// 生成二进制码后，按照每8个位一个字节的方式，生成N个byte,进而生成String，可以看到对应的字符打印出来
		int len = bi.length();
		int size = len / 8;
		byte[] bs = new byte[size];
		for (int i = 0; i < size; i++) {
			String s = bi.substring(8 * i, 8 * (i + 1));
			// 这里从二进制码转换成byte貌似在java里面挺麻烦，如果直接使用Byte.parseByte()方法,会产生溢出，所以要先转成Int然后强转成byte才能生成正常的byte
			// 注意java里面byte是-128~127，
			// 八位中的第一位是符号，而int是4个字节，如果1个字节的话，区间就成了0-255了，所以会溢出
			bs[i] = (byte) Integer.parseInt(s, 2);
		}
		String result = new String(bs, Charset.forName("UTF-8"));
		return result;
	}

	private static String beautify(String bi) {
		int length = bi.length();
		if (length % 8 != 0) {
			for (int j = 0; j < (8 - length % 8); j++) {
				bi = "0" + bi;
			}
		}
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i * 8) < bi.length()) {
			sb.append(bi.substring(i * 8, i * 8 + 8));
			sb.append(" ");
			i++;
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

}
