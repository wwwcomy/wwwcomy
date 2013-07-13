package com.iteye.wwwcomy.lxn.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

	/**
	 * Comment for <code>EMPTY_STRING</code>
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * Comment for <code>DOT</code>
	 */
	public static final char DOT = '.';

	/**
	 * Comment for <code>UNDERSCORE</code>
	 */
	public static final char UNDERSCORE = '_';

	/**
	 * Comment for <code>COMMA</code>
	 */
	public static final String COMMA = ",";

	/**
	 * Comment for <code>OPEN_PAREN</code>
	 */
	public static final String OPEN_PAREN = "(";

	/**
	 * Comment for <code>CLOSE_PAREN</code>
	 */
	public static final String CLOSE_PAREN = ")";

	/**
	 * 一个为"True"的私有变量,只是为了减少内存
	 */
	public static final String STR_TRUE = "True";

	/**
	 * 一个为"False"的私有变量,只是为了减少内存
	 */
	public static final String STR_FALSE = "False";

	/**
	 * 一个全局的空字符串
	 */
	public static final String STR_EMPTY = "";

	/**
	 * 数学符号,无穷数
	 */
	public static final String STR_INFINITUDE = "∞";

	/**
	 * @param s
	 * @return Map
	 */
	public static Map<Character, Integer> getCharMaps(String s) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			Integer count = map.get(c);
			map.put(c, count == null ? 1 : count + 1);
		}
		return map;
	}

	/**
	 * @param seperator
	 * @param strings
	 * @return String
	 */
	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return EMPTY_STRING;
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	/**
	 * @param seperator
	 * @param objects
	 * @return String
	 */
	public static String join(String seperator, Iterator<?> objects) {
		StringBuffer buf = new StringBuffer();
		buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	/**
	 * @param x
	 * @param sep
	 * @param y
	 * @return String[]
	 */
	public static String[] add(String[] x, String sep, String[] y) {
		String[] result = new String[x.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = x[i] + sep + y[i];
		}
		return result;
	}

	/**
	 * @param string
	 * @param times
	 * @return String
	 */
	public static String repeat(String string, int times) {
		StringBuffer buf = new StringBuffer(string.length() * times);
		for (int i = 0; i < times; i++)
			buf.append(string);
		return buf.toString();
	}

	/**
	 * @param seperators
	 * @param list
	 * @return
	 */
	public static String[] split(String list, String seperators) {
		return split(list, seperators, false);
	}

	/**
	 * @param seperators
	 * @param list
	 * @param include
	 * @return String[]
	 */
	public static String[] split(String list, String seperators, boolean include) {
		list = list == null ? "" : list;
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	/**
	 * @param seperators
	 * @param list
	 * @return String[]
	 */
	public static String[] splitVB(String list, String seperators) {
		return list.split(seperators, list.length());
	}

	public static String[] splitExpSeq(String seq, char separator) {
		if (seq.isEmpty()) {
			return null;
		}
		int length = seq.length();
		LinkedList<String> listResult = new LinkedList<String>();
		char c;
		int pos = -1;
		int deep = 0;
		String s = null;
		boolean leftQuFound = false;
		for (int i = 0; i < length; ++i) {
			c = seq.charAt(i);
			if (c == separator && deep == 0) {
				s = seq.substring(pos + 1, i);
				if (!s.isEmpty()) {
					listResult.add(s);
				}
				pos = i;
			} else if (c == '(') {
				++deep;
			} else if (c == ')') {
				--deep;
			} else if (c == '\'') {
				if (leftQuFound) {
					--deep;
					leftQuFound = false;
				} else {
					leftQuFound = true;
					++deep;
				}
			}
		}
		if (pos == -1) {
			s = seq;
		} else {
			s = seq.substring(pos + 1, length);
		}
		if (!s.isEmpty()) {
			listResult.add(s);
		}
		return (String[]) listResult.toArray(new String[] {});
	}

	/**
	 * @param srcStr
	 * @param seperators
	 * @param include
	 * @return String[]
	 */
	private static ArrayList<String> pSplitEx(String srcStr, String seperators,
			boolean include, int k) {
		// String[] seps = new String[seperators.length()];
		ArrayList<String> list = new ArrayList<String>();
		// for (int k = 0; k < seperators.length(); k++) {
		String sep = seperators.substring(k, k + 1);
		StringTokenizer tokens = new StringTokenizer(srcStr, sep, include);
		// String[] result = new String[tokens.countTokens()];
		// int i = 0;
		boolean canAdd = true;
		while (tokens.hasMoreTokens()) {
			// result[i++] = tokens.nextToken();
			String sonStr = tokens.nextToken();
			if (k < seperators.length() - 1) {
				String sep2 = seperators.substring(k + 1, k + 2);
				ArrayList<String> sonList = pSplitEx(sonStr, sep2, include, k);
				if (sonList.size() > 0) {
					list.addAll(sonList);
					canAdd = false;
				}
			}
			if (canAdd)
				list.add(sonStr);
		}
		// }
		return list;
	}

	/**
	 * @param srcStr
	 * @param seperators
	 * @param include
	 * @return String[]
	 */
	public static String[] splitEx(String srcStr, String seperators,
			boolean include) {
		return pSplitEx(srcStr, seperators, include, 0).toArray(new String[0]);
	}

	/**
	 * ("42343\;23fsafa,\,werw\,,sw",";,") 用SplitEx结果是：[42343\] [23fsafa] [\]
	 * [werw\] [] [sw] 用SplitEx2结果是：[42343;23fsafa] [,werw,] [sw]
	 * 
	 * @param str
	 * @param delimiters
	 * @return String[]
	 */
	public static String[] splitEx2(String str, String delimiters) {
		// ("42343\;23fsafa,\,werw\,,sw",";,")
		// 用SplitEx结果是：“42343\”“23fsafa”“\”“werw\”“”“sw”
		// 用SplitEx2 结果是：“42343;23fsafa”“,werw,”“sw”
		// delimiters as !@#$%^&*()-+=\/[]{};:'"<>,.?
		// String[] a = SplitEx(delimiters, "");
		String[] a = null;
		if (isBlankOrNull(delimiters))
			a = splitEx(str, "!@#$%^&*()-+=\\/[]{};:'\"<>,.? ", true);
		else
			a = splitEx(str, delimiters, true);
		for (int i = 0; i < a.length; i++) {
			str = replaceAll(str, "\\" + a[i], DataConstant.STR_SEPARATOR_2 + i);
		}
		// DebugUtil.debug(str);
		String[] aResult = splitEx(str, delimiters, false);
		for (int l = 0; l < aResult.length; l++) {
			for (int i = 0; i < a.length; i++) {
				aResult[l] = replaceAll(aResult[l],
						DataConstant.STR_SEPARATOR_2 + i, a[i]);
			}
		}
		// SplitEx2 = aResult
		return aResult;
	}

	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}

	/**
	 * @param qualifiedName
	 * @param seperator
	 * @return String
	 */
	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}

	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0) {
			return EMPTY_STRING;
		} else {
			return qualifiedName.substring(0, loc);
		}
	}

	/**
	 * @param strs
	 *            字符串集合
	 * @param suffix
	 *            后缀
	 * @return String[] 拼接字符串
	 */
	public static String[] suffix(String[] strs, String suffix) {
		if (suffix == null)
			return strs;
		String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = suffix(strs[i], suffix);
		}
		return result;
	}

	/**
	 * @param str
	 *            字符串
	 * @param suffix
	 *            后缀
	 * @return String 拼接字符串
	 */
	public static String suffix(String str, String suffix) {
		return (suffix == null) ? str : str + suffix;
	}

	/**
	 * @param strs
	 *            字符串集合
	 * @param prefix
	 *            后缀
	 * @return String[] 拼接字符串
	 */
	public static String[] prefix(String[] strs, String prefix) {
		if (prefix == null)
			return strs;
		String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = prefix + strs[i];
		}
		return result;
	}

	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String root(String qualifiedName) {
		int loc = qualifiedName.indexOf(".");
		return (loc < 0) ? qualifiedName : qualifiedName.substring(0, loc);
	}

	/**
	 * @param tfString
	 * @return boolean
	 */
	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	/**
	 * @param array
	 * @return String
	 */
	public static String toString(Object[] array) {
		// int len = array.length;
		// if (len == 0)
		// return StringUtil.EMPTY_STRING;
		// StringBuffer buf = new StringBuffer(len * 12);
		// for (int i = 0; i < len - 1; i++) {
		// buf.append(array[i]).append(COMMA_SPACE);
		// }
		// return buf.append(array[len - 1]).toString();
		return toString(array, null);
	}

	/**
	 * @param array
	 * @return String
	 */
	public static String toString(Object[] array, String delimiter) {
		int len = array.length;
		if (len == 0)
			return StringUtil.EMPTY_STRING;
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			if (delimiter == null)
				buf.append(array[i]).append(COMMA);
			else
				buf.append(array[i]).append(delimiter);
		}
		return buf.append(array[len - 1]).toString();
	}

	/**
	 * @param string
	 * @param placeholders
	 * @param replacements
	 * @return String[]
	 */
	public static String[] multiply(String string, Iterator<?> placeholders,
			Iterator<?> replacements) {
		String[] result = new String[] { string };
		while (placeholders.hasNext()) {
			result = multiply(result, (String) placeholders.next(),
					(String[]) replacements.next());
		}
		return result;
	}

	/**
	 * @param strings
	 * @param placeholder
	 * @param replacements
	 * @return String[]
	 */
	private static String[] multiply(String[] strings, String placeholder,
			String[] replacements) {
		String[] results = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++) {
				results[n++] = replace(strings[j], placeholder, replacements[i]);
			}
		}
		return results;
	}

	/**
	 * 将字符串数组转换成用delimiter分隔的字符串。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param delimiter
	 *            分隔符
	 * @return String
	 */
	public static String getStrByArray(String[] strs, String delimiter) {
		StringBuffer buf = new StringBuffer();
		int count = 0;
		if (strs != null) {
			for (int i = 0; i < strs.length; i++) {
				if (isBlankOrNull(strs[i]))
					continue;
				if (count > 0)
					buf.append(delimiter);
				buf.append(strs[i]);
				count++;
			}
		}
		return buf.toString();
	}

	/**
	 * 将字符串数组转换成用delimiter分隔的字符串。
	 * 
	 * @param objs
	 *            字符串数组
	 * @param delimiter
	 *            分隔符
	 * @return String
	 */
	public static String getStrByArray(Object[] objs, String delimiter) {
		StringBuffer buf = new StringBuffer();
		int count = 0;
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				if (isBlankOrNull(objs[i]))
					continue;
				if (count > 0)
					buf.append(delimiter);
				buf.append(objs[i]);
				count++;
			}
		}
		return buf.toString();
	}

	/**
	 * 从文件名中获得文件扩展名.
	 * 
	 * @param fileName
	 * @return String
	 */
	public static String getExtName(String fileName) {
		String[] names = split(fileName, "\\.");
		String extName = "";
		if (names != null && names.length > 0)
			extName = names[names.length - 1];
		return extName;
	}

	/**
	 * 分割字符串，空值不省略，加入返回的数组
	 * 
	 * @param str
	 *            必需的。包含子字符串和分隔符的字符串表达式。
	 * @param div
	 *            分隔符。用于标识子字符串边界的字符串字符。
	 * @return String[] 返回子字符串的数组，空值不省略。
	 */
	public static String[] splitHasNVL(String str, String div) {
		return splitHasNVL(str, div, false);
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 *            必需的。包含子字符串和分隔符的字符串表达式。
	 * @param div
	 *            分隔符。用于标识子字符串边界的字符串字符。
	 * @param ignored
	 *            标识。是否忽略空值。
	 * @return String[] 返回子字符串的数组，空值省略。
	 */
	public static String[] splitHasNVL(String str, String div, boolean ignored) {
		ArrayList<String> result = new ArrayList<String>();
		String temp = str;
		while (temp != null && temp.indexOf(div) != -1) {
			String value = temp.substring(0, temp.indexOf(div));
			// DebugUtil.debug(value);
			result.add(value);
			temp = temp.substring(temp.indexOf(div) + 1, temp.length());
		}

		if (!ignored) {
			result.add(temp);
		} else if (!isBlankOrNull(temp)) {
			result.add(temp);
		}

		String[] datas = new String[result.size()];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = (String) result.get(i);
		}
		return datas;
	}

	/**
	 * 取得整形数组
	 * 
	 * @param str
	 * @return Integer[]
	 */
	public static Integer[] getIntArray(String str) {
		String[] strs = split(str, ",");
		Integer[] result = new Integer[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = Double.valueOf(strs[i]).intValue();
		}
		return result;
	}

	/**
	 * 整形数组转化为字符串
	 * 
	 * @param ints
	 * @return
	 */
	public static String getString(Integer[] ints) {
		if (ints == null) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < ints.length; i++) {
			if (i != 0) {
				str.append(",");
			}
			str.append(ints[i]);
		}
		return str.toString();
	}

	/**
	 * @param obj
	 * @param style
	 * @return String
	 */
	public static String formatNum(Object obj, String style) {
		java.text.DecimalFormat format = new java.text.DecimalFormat(style);
		return format.format(obj);
	}

	/**
	 * @param obj
	 * @param dateStyle
	 * @return String
	 */
	public static String formatDate(Object obj, String dateStyle) {
		// String style = dateStyle == null ? "yyyy-MM-dd" : dateStyle;
		// return new Simple DateFormat(style).format(obj);
		return DateUtil.format(obj, dateStyle);
	}

	/**
	 * @param str
	 * @param div
	 * @return String[]
	 */
	public static String[] splitUniq(String str, String div) {
		StringTokenizer stk = new StringTokenizer(str, div);
		HashMap<Object, Object> valueMap = new HashMap<Object, Object>();
		while (stk.hasMoreElements()) {
			Object value = stk.nextToken();
			valueMap.put(value, value);
		}
		Set<Object> set = valueMap.keySet();
		String[] datas = new String[set.size()];
		int i = 0;
		Iterator<Object> iter = set.iterator();
		while (iter.hasNext()) {
			datas[i++] = (String) iter.next();
		}
		return datas;
	}

	/**
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return String
	 */
	public static String replace(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc))
					.append(replacement)
					.append(template.substring(loc + placeholder.length()))
					.toString();
		}
	}

	/**
	 * 将strSource中所有的strFrom字符串替换为strTo字符串。
	 * 
	 * @param template
	 *            源字符串
	 * @param placeholder
	 *            要替换的字符串
	 * @param replacement
	 *            替换为的字符串
	 * @return String
	 */
	public static String replaceAll(String template, String placeholder,
			String replacement) {
		if (template == null)
			return "";
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc))
					.append(replacement)
					.append(replaceAll(
							template.substring(loc + placeholder.length()),
							placeholder, replacement)).toString();
		}
	}

	/**
	 * str是空串或者null则返回true，否则返回false。
	 * 
	 * isEmpty
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlankOrNull(String str) {
		return (str == null || str.isEmpty());
	}

	/**
	 * str是空串或者null则返回true，否则返回false。
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlankOrNull(Object str) {
		if (str instanceof RefParameter) {
			RefParameter<?> refStr = (RefParameter<?>) str;
			return refStr == null || refStr.getValue() == null
					|| refStr.getValue().toString().length() == 0;
		}
		if (str == null || str.toString().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * @param o
	 * @return boolean
	 */
	public static boolean isBlankOrStrNull(Object o) {
		if (o instanceof RefParameter) {
			RefParameter<?> refStr = (RefParameter<?>) o;
			return refStr == null || refStr.getValue() == null
					|| refStr.getValue().toString().length() == 0;
		}
		String str = String.valueOf(o);
		if (str == null || str.length() == 0 || str.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}

	/**
	 * str是空串或者null则返回true，否则返回false。
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlankOrStrNull(String str) {
		if (str == null || str.length() == 0 || str.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}

	/**
	 * @param str
	 * @return boolean
	 */
	public static boolean isTrimBlankOrNull(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	// for (int b = inputStream.read(); b >= 0; b = inputStream.read())
	// {
	// outputStream.write((byte) b);
	// }
	// int bytesRead = 0;// 实际读取的字节数
	// byte[] buff = new byte[ ]; //缓冲1M
	// while ((bytesRead = inputStream.read(buff)) != -1) {
	// outputStream.write(buff, 0, bytesRead);
	// }
	/**
	 * @param inputStream
	 * @return String
	 * @throws Throwable
	 */
	public synchronized static String readString(InputStream inputStream)
			throws Throwable {
		if (inputStream == null)
			return null;
		// String result = null;
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			//
			int bytesRead = -1;
			while ((bytesRead = inputStream.read()) != -1) {
				outputStream.write(bytesRead);
			}
			return new String(outputStream.toByteArray());
		} catch (Throwable e) {
			// throw BKException.dealEx(e);
			throw e;
		} finally {
			if (outputStream != null)
				outputStream.close();
		}
	}

	/**
	 * @param str
	 * @return InputStream
	 * @throws Throwable
	 */
	public synchronized static InputStream writeIntoStream(String str)
			throws Throwable {
		if (StringUtil.isBlankOrNull(str))
			return null;
		try {
			return new ByteArrayInputStream(str.getBytes());
		} catch (Throwable e) {
			throw e;
		}
	}

	/**
	 * @param arrObject
	 * @param str
	 * @return String
	 */
	public static String join(List<?> arrObject, String str) {
		if (arrObject == null) {
			return "";
		}
		String sReturn = "";
		for (Object o : arrObject) {
			sReturn += String.valueOf(o) + str;
		}
		// Delete last ;
		if (sReturn.length() != 0) {
			sReturn = sReturn.substring(0, sReturn.length() - str.length());
		}
		return sReturn;
	}

	/**
	 * @param arrStr
	 * @param str
	 * @return String
	 */
	public static String join(String[] arrStr, String str) {
		String sReturn = "";
		if (arrStr == null) {
			return null;
		}
		for (String s : arrStr) {
			sReturn += s + str;
		}
		// Delete last ;
		if (sReturn.length() != 0) {
			sReturn = sReturn.substring(0, sReturn.length() - str.length());
		}
		return sReturn;
	}

	/**
	 * @param arrO
	 * @param str
	 * @return String
	 */
	public static String join(Object[] arrO, String str) {
		String sReturn = "";
		if (arrO == null) {
			return null;
		}
		for (Object s : arrO) {
			sReturn += s.toString() + str;
		}
		// Delete last ;
		if (sReturn.length() != 0) {
			sReturn = sReturn.substring(0, sReturn.length() - str.length());
		}
		return sReturn;
	}

	/**
	 * @param ints
	 * @param str
	 * @return String
	 */
	public static String join(int[] ints, String str) {
		String sReturn = "";
		for (int i : ints) {
			sReturn += i + str;
		}
		// Delete last ;
		if (sReturn.length() != 0) {
			sReturn = sReturn.substring(0, sReturn.length() - str.length());
		}
		return sReturn;
	}

	/**
	 * @param s
	 * @param seperator
	 * @return int
	 */
	public static int instr(String s, String seperator) {
		return s.toUpperCase().indexOf(seperator.toUpperCase());
	}

	/**
	 * 特定的判断字符串中是否存在另一个字符串的方法,不区分大小写
	 * 
	 * @param s
	 * @param find
	 * @param seperator
	 * @return boolean
	 */
	public static boolean instr(String s, String find, String seperator) {
		return (seperator + s + seperator).toUpperCase().indexOf(
				(seperator + find + seperator).toUpperCase()) >= 0;
	}

	/**
	 * 特定的判断字符串中是否存在另一个字符串的方法,区分大小写
	 * 
	 * @param s
	 * @param find
	 * @param seperator
	 * @return boolean
	 */
	public static boolean instrCase(String s, String find, String seperator) {
		return (seperator + s + seperator)
				.indexOf((seperator + find + seperator)) >= 0;
	}

	/**
	 * 这个split方法会将开始和最后的空字符串组去掉
	 * 
	 * @param s
	 * @param seperator
	 * @return String[]
	 */
	public static String[] splitTrim(String s, String seperator) {
		while (s.startsWith(seperator)) {
			s = s.substring(seperator.length());
		}
		while (s.endsWith(seperator)) {
			s = s.substring(0, s.length() - seperator.length());
		}
		return StringUtil.split(s, seperator);
	}

	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String left(String str, int i) {
		str = str == null ? "" : str;
		if (i > str.length())
			i = str.length();
		return str.substring(0, i);
	}

	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String right(String str, int i) {
		str = str == null ? "" : str;
		if (i > str.length())
			i = str.length();
		return str.substring(str.length() - i, str.length());
	}

	/**
	 * @param str
	 * @param i
	 * @param len
	 */
	public static String mid(String str, int i, int len) {
		if (str == null)
			return null;
		if (i + len > str.length())
			return str.substring(i, str.length());
		return str.substring(i, i + len);
	}

	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String mid(String str, int i) {
		return mid(str, i, str.length());
	}

	/**
	 * @param str
	 * @return int
	 */
	public static int len(Object str) {
		if (str == null)
			return 0;
		else
			return str.toString().length();
	}

	/**
	 * @param args
	 * @return int
	 */
	public static int len(Object[] args) {
		if (args == null)
			return 0;
		else
			return args.length;
	}

	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isDate(Object obj) {
		if (obj == null)
			return false;
		return isDate(obj.toString());
	}

	/**
	 * @param obj
	 *            []
	 * @return boolean
	 */
	public static boolean isDate(Object[] obj) {
		if (obj == null)
			return false;
		for (int i = 0; i < obj.length; i++) {
			if (!isDate(obj[i]))
				return false;
		}

		return true;
	}

	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null)
			return false;
		return isNumeric(obj.toString());
	}

	/**
	 * @param obj
	 *            []
	 * @return boolean
	 */
	public static boolean isNumeric(Object[] obj) {
		if (obj == null)
			return false;
		for (int i = 0; i < obj.length; i++) {
			if (!isNumeric(obj[i]))
				return false;
		}

		return true;
	}

	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isDate(String str) {
		Date result = GenericTypeValidator.formatDate(str, null);
		if (result != null)
			return true;
		return false;
	}

	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isNumeric(String str) {
		// String regex = "/^(-|\\+)?\\d+(\\.\\d+)?$/";
		// return str.matches(regex);
		// Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = patt.matcher(str);
		// if (matcher.find())
		Double result = GenericTypeValidator.formatDouble(str);
		if (result != null)
			return true;
		// else{
		// //Billform传入的数据可能为###,##0.00形式 by zhufw 2008-11-06
		// result = GenericTypeValidator.formatDouble(str.replace(",", ""));
		// if (result != null)
		// return true;
		// }
		return false;
	}

	/**
	 * @param obj
	 *            []
	 * @return boolean
	 */
	public static boolean isBoolean(Object[] obj) {
		if (obj == null)
			return false;
		for (int i = 0; i < obj.length; i++) {
			if (!isBoolean(obj[i]))
				return false;
		}

		return true;
	}

	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isBoolean(Object obj) {
		if (obj == null)
			return false;
		return isBoolean(obj.toString());
	}

	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isBoolean(String str) {
		return (str != null && (str.equalsIgnoreCase("TRUE")
				|| str.equalsIgnoreCase("FALSE") || isNumeric(str)));
		// || str.equalsIgnoreCase("1") || str
		// .equalsIgnoreCase("0")
	}

	/**
	 * 根据SQL语句获取要纠正的数据库表名
	 * 
	 * @param SQL
	 *            sql语句
	 * @return String[] 包含的表名
	 */
	public static String[] getTableFromSQL(String SQL) {
		// , String[] asTable //(?:exp) 匹配exp,不捕获匹配的文本，也不给此分组分配组号
		// (?:exp)不会改变正则表达式的处理方式，只是这样的组匹配的内容不会被捕获到某个组里面，也不会拥有组号。
		SQL = " " + SQL; // sql最前面加个空格主要是”update,delete“的正确匹配
		String regex = "\\s+(?:(?:from|join|update|into)\\s+)";
		regex += "(?:(?:(?:\\w+\\.)?\\w+(?:\\s+as)?(?:\\s+\\w+)?\\s*,\\s*)*)";
		regex += "(?:\\w+\\.)?(\\w+)";
		// {%s}
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(SQL);
		ArrayList<String> list = new ArrayList<String>();
		while (matcher.find()) {
			String temp = matcher.group().toUpperCase()
					.replaceAll(" FROM ", "").replaceAll(" JOIN ", "")
					.replaceAll(" UPDATE ", "").replaceAll(" INTO ", "")
					.replaceAll(" DELETE ", "").replaceAll("INSERT INTO", "");
			// SCM_DBRIGHTS不用检查 by zhufw 2008-09-09
			// 业务权限在执行initdatabase前已经被使用 要在dbm里创建
			// .replaceAll("SCM_META RIGHTS", "").replaceAll("GP_ROLE ROLE",
			// "");
			String[] tbls = null;
			if (!temp.trim().equals("")) {
				tbls = split(temp, ",");
				// Node node = new Node(matcher.group(), matcher.start(),
				// (matcher.end() - 1));
				// valueMap.put(matcher.group(), matcher.group());
				for (int i = 0; i < tbls.length; i++) {
					temp = split(tbls[i].trim(), " ")[0];
					if (!list.contains(temp))
						list.add(temp);
				}
			}
		}
		return (String[]) list.toArray(new String[0]);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 将字符串值转换为合法的SQL值
	 * 
	 * @param sStr
	 * @return String
	 */
	public static String Str2SQL(String sStr) {
		// return "'" + sStr.replaceAll("'", "''") + "'";
		if (StringUtil.isBlankOrNull(sStr))
			return "''";
		return "'" + sStr.replaceAll("'", "''") + "'";
	}

	/**
	 * @param sql1
	 * @param sql2
	 * @return String
	 */
	public static String GetJoinSQL(String sql1, String sql2) {
		return join(sql1, sql2, null);
	}

	/**
	 * 通过spt合并两个字符串
	 * 
	 * @param s1
	 * @param s2
	 * @param spt
	 * @return String
	 */
	public static String join(String s1, String s2, String spt) {
		if (spt == null)
			spt = " and ";
		return isTrimBlankOrNull(s1) ? s2 : s1
				+ (isTrimBlankOrNull(s2) ? "" : spt + s2);
	}

	/**
	 * 通过字符串将两个字符串连起来,空字符串不参于相连
	 * 
	 * @param s1
	 * @param s2
	 * @param spt
	 * @return String
	 */
	public static String getJoin(String s1, String s2, String spt) {
		return isBlankOrNull(s1) ? s2 : s1
				+ (isBlankOrNull(s2) ? "" : spt + s2);
	}

	/**
	 * @param value
	 * @return String
	 */
	public static String getNnofStrValue(Object value) {
		return value == null ? "" : value.toString();
		// if (value instanceof String)
		// return value == null ? "" : value.toString();
		// else if (value instanceof BigDecimal)
		// return value == null ? "0" : value.toString();
		// else if (value instanceof Double)
		// return value == null ? "" : value.toString();
		// else if (value instanceof Long)
		// return value == null ? "" : value.toString();
		// else if (value instanceof Integer)
		// return value == null ? "" : value.toString();
		// else if (value instanceof Byte)
		// return value == null ? "" : value.toString();
		// return "";
	}

	/**
	 * @param str1
	 * @param str2
	 * @return Boolean
	 */
	public static Boolean like(String str1, String str2) {
		// 目前这个比较是区分大小写的，因为VB中的LIKE 也是区分大小写的
		return str1.matches(str2 + "((\\-*\\d*|\\s*|\\w*)*)");

	}

	/**
	 * @param s
	 * @return Matcher
	 */
	private static Matcher pSplitExpression(String s) {
		// '##BD 将表达式按(,)这些符号将表达式分开,两个"之间表示字符串,""是"在字符串中的转义.
		// '##BD 如果代码读不明白的话,请阅读正则表达式相关内容.
		String regex = "([^,\\(\\)\"]|\"([^\"]|\"\")*\")+|,|\\(|\\)";
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(s);
		return matcher;
	}

	/**
	 * @param s
	 * @return ArrayList
	 */
	public static ArrayList<String> sPlitSparse(String s) {
		// '##BD 分离出参数
		// '再读取新值
		Matcher matcher = pSplitExpression(s);
		String value;
		Integer lLeftParenthesis = 0;// '当前左括号的个数
		String sPara = "";
		ArrayList<String> aParaValue = new ArrayList<String>();
		Integer lPara = 0;
		while (matcher.find()) {
			value = matcher.group();
			if (value.equalsIgnoreCase(",")) {
				// '如果没有"("的积累,那表示参数有效
				if (lLeftParenthesis >= 1) {
					sPara = sPara + ",";
				} else {
					aParaValue.add(lPara, sPara);
					lPara = lPara + 1;
					sPara = "";
				}
			} else if (value.equalsIgnoreCase(")")) {
				if (lLeftParenthesis == 1) {
					// '如果没有"("的积累,那表示参数有效
					if (sPara.length() != 0) {
						sPara = sPara + ")";
						lLeftParenthesis = lLeftParenthesis - 1;
					}// '到这里,解析已经完成
				} else {
					lLeftParenthesis = lLeftParenthesis - 1;
					sPara = sPara + ")";
				}
			} else if (value.equalsIgnoreCase("(")) {
				sPara = sPara + "(";
				lLeftParenthesis = lLeftParenthesis + 1;
			} else {
				sPara = sPara + value;
			}
		}
		if (sPara.length() > 0) {
			aParaValue.add(lPara, sPara);
			sPara = "";
		}
		return aParaValue;
	}

	/**
	 * @param s
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> SplitSemicolonParse(String s) {
		// ([^;""]|""([^""]|"""")*"")+|;
		String regex = "([^;\"]|\"([^\"]|\"\")*\")+|;";
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(s);

		ArrayList<String> aRValue = new ArrayList<String>();
		String value = "";
		int lPara = 0;
		while (matcher.find()) {
			value = matcher.group();
			if (!value.equalsIgnoreCase(";")) {
				aRValue.add(lPara, value);
				lPara++;
			}
		}
		return aRValue;
	}

	/**
	 * @param sPart
	 * @param sFormat
	 * @param args
	 * @return String
	 */
	public static String pSprintfEx(String sPart, String sFormat,
			String... args) {
		// ' 对字符串做特殊拼接转化处理
		String pSprintfEx = "";
		String sSplit = ",";
		if (len(args) > 0)
			sSplit = args[0];
		String[] sPiece = split(sPart, ",");// ' 传入字符串总是以逗号分割的
		// int lLoop;
		boolean bNot1St = false;
		for (int i = 0; i < sPiece.length; i++) {
			if (len(sPiece[i]) != 0) {// ' 对内容为空的部分不做处理
				if (!bNot1St) {
					pSprintfEx = replaceAll(sFormat, "%a", sPiece[i]);
					bNot1St = true;
				} else {
					pSprintfEx += sSplit + replaceAll(sFormat, "%a", sPiece[i]);
				}
			}
		}
		return pSprintfEx;
	}

	/**
	 * 将Object[]型数组转换成String[]型数组
	 * 
	 * @param objs
	 *            数组对象
	 * @return String[] 数组
	 */
	public static String[] getStringArrayBYArray(Object[] objs) {
		Integer objsSize = objs.length;
		String[] strs = new String[objsSize];
		for (Integer i = 0; i < objsSize; i++) {
			strs[i] = objs[i].toString();
		}
		return strs;
	}

	/**
	 * 特别处理下空对象
	 * 
	 * @param ret
	 * @return String
	 */
	public static String CStr(Object ret) {
		if (ret == null)
			return "";
		return ret.toString();
	}

	/**
	 * @param num
	 * @param sValue
	 * @return String
	 */
	public static String string(int num, String sValue) {
		StringBuffer sbuf = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sbuf.append(sValue);
		}
		return sbuf.toString();
	}

	/**
	 * @param num
	 * @return String
	 */
	public static String Space(int num) {
		return string(num, " ");
	}

	/**
	 * 读入字符串数组
	 * 
	 * @param in
	 * @return String[]
	 * @throws IOException
	 */
	public static String[] readInternUTFs(ObjectInput in) throws IOException {
		boolean b = in.readBoolean();
		if (b) {
			int length = in.readInt();
			String[] result = new String[length];
			for (int i = 0; i < length; i++) {
				result[i] = readInternUTF(in);
			}
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 序列化时读取字符串
	 * 
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String readInternUTF(ObjectInput in) throws IOException {
		try {
			boolean b = in.readBoolean();
			if (b) {
				// short c = in.readShort();
				String s = in.readUTF().intern();
				if (s != null && s.length() == 0) {
					return ""; // 减少heap中的""对象
				}
				// if (c == 1) {
				// // byte[] by = new ZipUtil().unzip(s.getBytes());
				// // String bs = new String(by);
				// // return bs;
				// StringBuffer bs = new StringBuffer();
				// int block = in.readInt();
				// int then = in.readInt();
				// int i = 0;
				// for (; i < block; i++) {
				// bs.append(in.readUTF());
				// }
				// if (then > 0)
				// bs.append(in.readUTF());
				// return bs.toString();
				// } else {
				return s;
				// }
			} else {
				return null;
			}
		} catch (java.io.UTFDataFormatException ex) {
			throw new IOException("数据过长", ex);
		} catch (IOException ex) {
			throw ex;
		} catch (Throwable e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * 序列化时读取字符串
	 * 
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String readUTF(ObjectInput in) throws IOException {
		try {
			boolean b = in.readBoolean();
			if (b) {
				// short c = in.readShort();
				String s = in.readUTF();
				if (s != null && s.length() == 0) {
					return ""; // 减少heap中的""对象
				}
				// if (c == 1) {
				// // byte[] by = new ZipUtil().unzip(s.getBytes());
				// // String bs = new String(by);
				// // return bs;
				// StringBuffer bs = new StringBuffer();
				// int block = in.readInt();
				// int then = in.readInt();
				// int i = 0;
				// for (; i < block; i++) {
				// bs.append(in.readUTF());
				// }
				// if (then > 0)
				// bs.append(in.readUTF());
				// return bs.toString();
				// } else {
				return s;
				// }
			} else {
				return null;
			}
		} catch (java.io.UTFDataFormatException ex) {
			throw new IOException("数据过长", ex);
		} catch (IOException ex) {
			throw ex;
		} catch (Throwable e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * 序列化时写入字符串
	 * 
	 * @param out
	 * @param s
	 * @throws IOException
	 */
	public static void writeUTF(ObjectOutput out, String s) throws IOException {
		try {
			boolean b = (s != null);
			out.writeBoolean(b);
			if (b) {
				// if (s.length() > ZIP_SIZE) {
				// out.writeShort(1);
				// // byte[] by = new ZipUtil().zip(s.getBytes());
				// // int block = by.length / BLOCK_SIZE;
				// // if (by.length % BLOCK_SIZE > 0) {
				// // block++;
				// // }
				// // out.writeInt(block);
				// // for (int i = 0; i < block; i++) {
				// // String bs = new String(by);
				// // out.writeUTF(bs);
				// // }
				// int block = s.length() / BLOCK_SIZE;
				// int then = s.length() % BLOCK_SIZE;
				// out.writeInt(block);
				// out.writeInt(then);
				// int i = 0;
				// for (; i < block; i++) {
				// String bs = s.substring(i * BLOCK_SIZE, (i + 1)
				// * BLOCK_SIZE);
				// out.writeUTF(bs);
				// out.flush();
				// }
				// if (then > 0) {
				// String bs = s.substring(i * BLOCK_SIZE, i * BLOCK_SIZE
				// + then);
				// out.writeUTF(bs);
				// }
				// } else {
				// out.writeShort(0);
				out.writeUTF(s);
				// }
			}
			out.flush();
		} catch (java.io.UTFDataFormatException ex) {
			throw new IOException("数据过长:" + s.length(), ex);
		} catch (IOException ex) {
			throw ex;
		} catch (Throwable e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * 写入字符串数组
	 * 
	 * @param out
	 * @param ss
	 * @throws IOException
	 */
	public static void writeUTFs(ObjectOutput out, String[] ss)
			throws IOException {
		boolean b = (ss != null);
		out.writeBoolean(b);
		if (b) {
			int length = ss.length;
			out.writeInt(ss.length);
			for (int i = 0; i < length; i++) {
				writeUTF(out, ss[i]);
			}
		}
	}

	/**
	 * 全角转半角
	 * 
	 * @param QJstr
	 * @return String
	 */
	public static final String QBchange(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}

	/**
	 * @param sCheckBoxNoString
	 * @return boolean
	 */
	public static final boolean CheckBoxNoString(String sCheckBoxNoString) {

		// 可能以后需要考虑不同的ISO标准，如果有需要再增加参数吧

		// ##BD 集装箱号检验
		// ##PD sBoxNoString 集装箱号
		// ##RD 检验通过返回True，否则返回False

		// 标准箱号构成基本概念：采用ISO6346（1995）标准。
		// 标准集装箱箱号由11位编码组成，包括三个部分：
		// 1、 第一部分由4位英文字母组成。前三位代码 (Owner Code) 主要说明箱主、经营人，第四位代码说明集装箱的类型。列如CBHU
		// 开头的标准集装箱是表明箱主和经营人为中远集运。
		// 2、 第二部分由6位数字组成。是箱体注册码（Registration Code）, 用于一个集装箱箱体持有的唯一标识。
		// 3、 第三部分为校验码（Check Digit）由前4位字母和6位数字经过校验规则运算得到，用于识别在校验时是否发生错误。即第11位数字。
		// 根据校验规则箱号的每个字母和数字都有一个运算的对应值。箱号的前10位字母和数字的对应值从0到Z对应数值为0到38，11、22、33不能对11取模数，所以要除去。
		// 2、第N位的箱号对应值再分别乘以2的（N－1）次方 （N＝1，2，3………..10）
		// 例如：箱号为CBHU3202732的集装箱它的第1位代码为C，
		// 它的代码值＝代码的对应值×2的（1－1）次方 ＝13×1＝13。
		// 类推第2位代码为B
		// 它的代码值＝代码的对应值×2的（2－1 ）次方＝12×2＝24
		// 以此类推得到箱号前10位代码的代码值。
		// 将前10位的代码值乘积累加后对11取模
		// 箱号为CBHU3202732的集装箱前10位箱号的代码累加值＝4061，取11的模后为2，就是这个箱号第11位的识别码的数值。
		// 注意：前四位开头为：“HLCU”的集装箱号，不进行校验检查！！
		// 以此类推，就能得到校验码，最后比较前10位集装箱号计算出来的余数，与第11位校验码是否相同，相同则检验通过，不相同则提示报错。

		// 对11取模后当余数等于11时候就会出现两位10余码,所以对应码就取消了11的倍数,比如11,22,33等,但是运算下来还是有这种11倍数余码.
		// 如果校验码出现10,说明你取11模的时候没有判断出现2位余码,遇到10就后面添加减去10,最后校验是0就是标准校验码.
		// HCIU8009460
		// TWDU1001060

		sCheckBoxNoString = sCheckBoxNoString.trim();

		if (sCheckBoxNoString.length() != 11)
			return false;

		// HLCU开头的不需要检查
		if (sCheckBoxNoString.startsWith("HLCU"))
			return true;

		// 前4位要求是字母
		if (!sCheckBoxNoString.substring(0, 4).matches("[A-Z]*")) {
			return false;
		}

		// 后7要求是数字
		if (!sCheckBoxNoString.substring(4).matches("[0-9]*")) {
			return false;
		}

		String arrValueTable[][] = new String[36][2];

		int iAscii = 48;
		int iAsciiValue = 0;

		for (int i = 0; i < arrValueTable.length; i++) {

			if (i == 30)
				System.out.print("");

			arrValueTable[i] = new String[] { String.valueOf((char) iAscii),
					String.valueOf(iAsciiValue) };

			iAscii++;
			iAsciiValue++;

			if (iAscii == 58) {
				iAscii = 65;
			}

			if (iAsciiValue % 11 == 0) {
				iAsciiValue++;
			}
		}

		double iSumValue = 0;
		int iCurrValue = 0;

		for (int i = 0; i < sCheckBoxNoString.length() - 1; i++) {
			for (int j = 0; j < arrValueTable.length; j++) {
				if ((arrValueTable[j][0]).equals(sCheckBoxNoString.substring(i,
						i + 1))) {
					iCurrValue = Integer.parseInt(arrValueTable[j][1]);
					iSumValue = iSumValue + iCurrValue * Math.pow(2, i);
					continue;
				}
			}
		}
		// 比较前10位集装箱号计算出来的余数，与第11位校验码是否相同，相同则检验通过
		// 再除以10 是以为 会出现余数为10的情况
		return (iSumValue % 11 % 10 == Integer.parseInt(sCheckBoxNoString
				.substring(10, 11)));
	}

	/**
	 * @param str
	 * @return String
	 */
	public static String removeExcelIllegals(String str) {
		if (!isBlankOrNull(str)) {
			str = str.replaceAll("/", "").replaceAll(":", "")
					.replaceAll("[?]", "").replaceAll("[*]", "")
					.replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll("[\\\\]", "");
		}

		return str;
	}

	/**
	 * 合并数组
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static Object[] arrayCopy(Object[] array1, Object[] array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null) {
			return clone(array1);
		}
		Object[] joinedArray = (Object[]) Array.newInstance(array1.getClass()
				.getComponentType(), array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	private static Object[] clone(Object[] array) {
		if (array == null) {
			return null;
		}
		return ((Object[]) array.clone());
	}

	public static void main(String[] args) {
		String sCheckNo = "GESU2475463";
		System.out.print(CheckBoxNoString(sCheckNo));
		// String a[] = { "1", "true", "false_", "2", "3", "5" };
		// String b[] = { "1", "2-2", "3", "2", "3", "5" };
		// String c[] = { "2009-01-01", "2009-01-01 12:00",
		// "2009-12-14 16:26:42",
		// "12:00", "3", "5" };
		// DebugUtil.debug(isBoolean((Object[]) a));
		// DebugUtil.debug(isNumeric((Object[]) b));
		// DebugUtil.debug(isDate((Object[]) c));
		// DebugUtil.debug(string(2, "0"));
		// // new BigDecimal("123232.00").;
		// formatNum("123345", "######.00");
		// // DebugUtil.debug(isBoolean(false));
		// // @SuppressWarnings("unused")
		// // String[] t = splitHasNVL("kk,", ",");
		// // DebugUtil.debug(formatNum(123345, "###,###.00"));
		// // //
		// // ("42343\;23fsafa,\,werw\,,sw",";,")
		// // 用SplitEx结果是：“42343\”“23fsafa”“\”“werw\”“”“sw”
		// // 用SplitEx2 结果是：“42343;23fsafa”“,werw,”“sw”
		// String str = "42343\\;23fsafa,\\,werw\\,,sw";
		// DebugUtil.debug(str);
		// String[] strs = null;
		// // strs = splitEx(str, ",;", false);
		// // for (String s : strs) {
		// // DebugUtil.debug("xxx" + s);
		// // }
		// strs = splitEx2(str, ";,");
		// for (String s : strs) {
		// DebugUtil.debug("***" + s);
		// }
		// // strs = splitEx2(str, ";,");
		// // for (String s : strs) {
		// // DebugUtil.debug("---" + s);
		// // }
	}

}