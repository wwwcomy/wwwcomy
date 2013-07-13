package com.iteye.wwwcomy.lxn.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * 类型转化,在四种类型之间转化,boolean,String,double,Null之间转化. 这个类不准创建.
 * 
 * @author jzh
 * 
 */
public class TypeConvert {
	private TypeConvert() {
	}

	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) throws Throwable {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 将四种类型转化为boolean型.
	 * 
	 * @param v
	 *            待转化的值.
	 * @return 转化后的Boolean类型
	 * @throws ParseException
	 */
	public static Boolean getBoolean(Object v) throws Throwable {
		if (v == null) {
			return false;
		} else if (v instanceof Boolean) {
			return (Boolean) v;
		} else if (v instanceof String) {
			// String类型,只有"true"(不区分大小写)被转化为true,其它都是false.
			if (StringUtil.isNumeric((String) v)) {
				if (Double.valueOf((String) v) == 0)
					return false;
				else
					return true;
			}
			return ((String) v).equalsIgnoreCase("true");
		} else if (v instanceof Double) {
			// Double类型,只有0被转化为false,其它都被转化为true.
			return !((Double) v == 0);
		} else if (v instanceof Integer) {
			return !((Integer) v == 0);
		} else if (v instanceof Long) {
			return !((Long) v == 0);
		} else {
			throw new Throwable("计算过程不应该出现其它类型.");
		}
	}

	/**
	 * 将四种类型转化为String类型.
	 * 
	 * @param v
	 *            待转化数据.
	 * @return String类型数据.
	 * @throws ParseException
	 */
	public static String getString(Object v) throws Throwable {
		if (v instanceof Boolean) {
			return (Boolean) v ? "true" : "false";
		} else if (v instanceof String) {
			String s = (String) v;
			s = StringUtil.replaceAll(s, "\\\\", "\\");
			return s;
		} else if (v instanceof Long) {
			return ((Long) v).toString();
		} else if (v instanceof Double) {
			// 如果double的数据是int的数据,那返回时按int返回.
			if (((Double) v) == (((Double) v).intValue()))
				return ((Integer) ((Double) v).intValue()).toString();
			else
				return ((Double) v).toString();
		} else if (v instanceof Float) {
			// 如果double的数据是int的数据,那返回时按int返回.
			if (((Float) v) == (((Float) v).intValue()))
				return ((Integer) ((Float) v).intValue()).toString();
			else
				return ((Float) v).toString();
		} else if (v instanceof Integer) {
			return ((Integer) v).toString();
		} else if (v instanceof Date) {
			return ((Date) v).toString();
		} else if (v instanceof BigDecimal) {
			return ((BigDecimal) v).toString();
		} else if (v == null) {
			throw new Throwable("null不应该参于计算.");
		} else if (v instanceof Integer[]) { // 字典多选的值
			return StringUtil.join((Integer[]) v, ";");
		}
		// else if (v == null) {
		// // TODO 会影响数值运算
		// return ""; // NOTICE 默认值在设置为空时，也能进行容错性的trigger，是否会影响其他公式需要讨论
		// }
		else {
			return "";
			// throw new ParseException("计算过程不应该出现其它类型:"
			// + (v == null ? "null" : "" + v.getClass()));
		}
	}

	/**
	 * 将四种类型转化为double类型.
	 * 
	 * @param v
	 *            待转化数据.
	 * @return Double类型的数据.
	 * @throws ParseException
	 */
	public static Double getDouble(Object v) throws Throwable {
		if (v == null) {
			return 0.0;
		} else if (v instanceof Boolean) {
			return (Boolean) v ? 1.0 : 0.0;
		} else if (v instanceof Long) {
			return ((Long) v).doubleValue();
		} else if (v instanceof String) {
			// 如果是空字符串,作0处理,这里还得再确定,先这样处理
			if (((String) v).isEmpty() || "null".equals(v))
				return 0.0;
			else
				return Double.parseDouble((String) v);
		} else if (v instanceof Double) {
			return (Double) v;
		} else if (v instanceof Float) {
			return ((Float) v).doubleValue();
		} else if (v instanceof Integer) {
			return ((Integer) v).doubleValue();
		} else if (v instanceof BigDecimal) {
			return ((BigDecimal) v).doubleValue();
		} else if (v instanceof Date) {
			return (double) ((Date) v).getTime();
		} else {
			throw new Throwable("计算过程不应该出现其它类型.");
		}
	}

	/**
	 * 将四种类型转化为BigDecimal类型.
	 * 
	 * @param v
	 *            待转化数据.
	 * @return BigDecimal类型的数据.
	 * @throws ParseException
	 */
	public static BigDecimal getBigDecimal(Object v) throws Throwable {
		if (v == null) {
			return BigDecimal.ZERO;
		} else if (v instanceof Boolean) {
			return (Boolean) v ? BigDecimal.ONE : BigDecimal.ZERO;
		} else if (v instanceof String) {
			// 如果是空字符串,作0处理,这里还得再确定,先这样处理
			if (((String) v).isEmpty() || "null".equals(v))
				return BigDecimal.ONE;
			else
				return new BigDecimal((String) v);
		} else if (v instanceof Double) {
			return BigDecimal.valueOf((Double) v);
		} else if (v instanceof Long) {
			return BigDecimal.valueOf((Long) v);
		} else if (v instanceof Integer) {
			return BigDecimal.valueOf((Integer) v);
		} else if (v instanceof Float) {
			return BigDecimal.valueOf((Float) v);
		} else if (v instanceof BigDecimal) {
			return (BigDecimal) v;
		} else if (v instanceof Date) {
			return BigDecimal.valueOf(((Date) v).getTime());
		} else {
			throw new Throwable("计算过程不应该出现其它类型.");
		}
	}

	/**
	 * 是否可以转化为double类型.
	 */
	public static boolean canGetDouble(Object v, boolean treatEmptyAsZero) {
		if (v instanceof Boolean) {
			return true;
		} else if (v instanceof String) {
			// 对空字符串的支持,作0处理,这里还需要再确定,先这么处理
			if (((String) v).isEmpty()) {
				if (treatEmptyAsZero) {
					return false;
				} else {
					return true;
				}
			}
			try {
				Double.parseDouble((String) v);
				return true;
			} catch (NumberFormatException e) {
				// 这是不处理错误
				return false;
			}
		} else if (v == null) {
			return false;
		} else if (v instanceof Double) {
			return true;
		} else if (v instanceof Float) {
			return true;
		} else if (v instanceof Integer) {
			return true;
		} else if (v instanceof Long) {
			return true;
		} else if (v instanceof BigDecimal) {
			return true;
		} else if (v instanceof Date) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 金额转化为大写.只能处理万亿级内的金额.
	 * 
	 * @param d
	 *            原金额.
	 * @return 大写字符串.
	 * @throws ParseException
	 */
	public static String rmbdx(double d) throws Throwable {
		return ChineseMoney.getChineseMoney(d);
		// return ChinaMoney.convert(d);
	}
	
	/**
	 * 汉字大写转换为阿拉伯数字，只能处理正整数和万亿级内的数值，注意汉字书写规范，转换失败返回-1
	 * @param s
	 * @return
	 * @throws Throwable
	 */
	public static int chinese2digit(String s) throws Throwable{
		return ChineseMoney.parseChineseNumber(s);
	}

	/**
	 * 数值转化为汉字.只能处理万亿级内的数值.
	 * 
	 * @param d
	 *            原数值.
	 * @return 汉字.
	 * @throws ParseException
	 */
	public static String chinesedx(double d, String sUnit, int scale)
			throws Throwable {
		return ChineseCharacter.getChineseCharacter(d, sUnit, scale);
		// return ChinaMoney.convert(d);
	}

	public static void main(String[] args) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("5864", "伍仟捌佰陆拾肆圆整");
			map.put("6000", "陆仟圆整");
			map.put("6136", "陆仟壹佰叁拾陆圆整");
			map.put("6272", "陆仟贰佰柒拾贰圆整");
			map.put("6408", "陆仟肆佰零捌圆整");
			map.put("6544", "陆仟伍佰肆拾肆圆整");
			map.put("6680", "陆仟陆佰捌拾圆整");
			map.put("6816", "陆仟捌佰壹拾陆圆整");
			map.put("6952", "陆仟玖佰伍拾贰圆整");
			map.put("7088", "柒仟零捌拾捌圆整");
			map.put("6816", "陆仟捌佰壹拾陆圆整");
			map.put("6952", "陆仟玖佰伍拾贰圆整");
			map.put("36628.337", "叁万陆仟陆佰贰拾捌圆叁角肆分");
			map.put("1299.99", "壹仟贰佰玖拾玖圆玖角玖分");
			map.put("36582.5", "叁万陆仟伍佰捌拾贰圆伍角整");
			map.put("18731358.5", "壹仟捌佰柒拾叁万壹仟叁佰伍拾捌圆伍角整");
			map.put("8051054", "捌佰零伍万壹仟零伍拾肆圆整");
			map.put("17657.07", "壹万柒仟陆佰伍拾柒圆零柒分");
			map.put("100000", "壹拾万圆整");
			map.put("16405.06", "壹万陆仟肆佰零伍圆零陆分");
			map.put("325.52", "叁佰贰拾伍圆伍角贰分");
			map.put("1010101", "壹佰零壹万零壹佰零壹圆整");
			map.put("107000", "壹拾万零柒仟圆整");
			map.put("10605.02", "壹万零陆佰零伍圆零贰分");
			map.put("2010001", "贰佰零壹万零壹圆整");
			map.put("32012012", "叁仟贰佰零壹万贰仟零壹拾贰圆整");
			String sRMB = "";
			for (String sKey : map.keySet()) {
				sRMB = map.get(sKey);

				if (!TypeConvert.rmbdx(Double.parseDouble(sKey)).equals(sRMB)) {
					System.out.println("金额：" + sKey + "转换错误 ："
							+ TypeConvert.rmbdx(Double.parseDouble(sKey))
							+ "，正确结果为：" + sRMB);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

class ChinaMoney {
	public static String convert(Double d) {
		String num = "零壹贰叁肆伍陆柒捌玖";
		String dw = "圆拾佰仟万亿";
		String m = d.toString();
		String mm[] = null;
		mm = m.split("\\.");
		String money = mm[0];

		String result = num.charAt(Integer.parseInt("" + mm[1].charAt(0)))
				+ "角" + num.charAt(Integer.parseInt("" + mm[1].charAt(1)))
				+ "分";

		for (int i = 0; i < money.length(); i++) {
			String str = "";
			int n = Integer.parseInt(money.substring(money.length() - i - 1,
					money.length() - i));
			str = str + num.charAt(n);
			if (i == 0) {
				str = str + dw.charAt(i);
			} else if ((i + 4) % 8 == 0) {
				str = str + dw.charAt(4);
			} else if (i % 8 == 0) {
				str = str + dw.charAt(5);
			} else {
				str = str + dw.charAt(i % 4);
			}
			result = str + result;
		}

		result = result.replaceAll("零([^亿万圆角分])", "零");
		result = result.replaceAll("亿零+万", "亿零");
		result = result.replaceAll("零+", "零");
		result = result.replaceAll("零([亿万圆])", "$1");
		result = result.replaceAll("^壹拾", "拾");

		return result;
	}
}

class ChineseMoney {
	public static String[] chineseDigits = new String[] { "零", "壹", "贰", "叁",
			"肆", "伍", "陆", "柒", "捌", "玖" };

	/**
	 * 把金额转换为汉字表示的数量，小数点后四舍五入保留两位
	 * 
	 * @param amount
	 * @return
	 */
	public static String getChineseMoney(double amount) {

		if (amount > 999999999999999.99 || amount < -999999999999999.99)
			throw new RuntimeException(
					"参数值超出允许范围 (-999999999999999.99 ～ 999999999999999.99)！");

		boolean negative = false;
		if (amount < 0) {
			negative = true;
			amount = amount * (-1);
		}

		long temp = Math.round(amount * 100);
		int numFen = (int) (temp % 10); // 分
		temp = temp / 10;
		int numJiao = (int) (temp % 10); // 角
		temp = temp / 10;
		// temp 目前是金额的整数部分

		int[] parts = new int[20]; // 其中的圆素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
		int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
		for (int i = 0;; i++) {
			if (temp == 0)
				break;
			int part = (int) (temp % 10000);
			parts[i] = part;
			numParts++;
			temp = temp / 10000;
		}

		boolean beforeWanIsZero = true; // 标志"万"下面一级是不是 0

		String chineseStr = "";
		for (int i = 0; i < numParts; i++) {

			String partChinese = partTranslate(parts[i]);
			if (i % 2 == 0) {
				if ("".equals(partChinese)) {
					beforeWanIsZero = true;
				} else {
					beforeWanIsZero = false;
				}
			}

			if (i != 0) {
				if (i % 2 == 0) {

					String s = partTranslate(parts[i - 1]);
					if ("".equals(s)) {
						chineseStr = "亿" + chineseStr;
					} else {
						chineseStr = "亿零" + chineseStr;
					}
				} else {
					if ("".equals(partChinese) && !beforeWanIsZero) {
						// 如果"万"对应的 part 为 0，而"万"下面一级不为 0，则不加"万"，而加"零"
						chineseStr = "零" + chineseStr;
					} else {
						if (parts[i - 1] > 0 && parts[i - 1] < 1000) {
							// 如果"万"的部分不为 0,而"万"后面的部分小于 1000 大于 0则万后面应该跟"零"
							chineseStr = "零" + chineseStr;
							// chineseStr = "万" + chineseStr;
						}
						// // 如果"万"的部分为0
						else if ((parts[i] % 10 == 0) && !beforeWanIsZero) {

							chineseStr = "零" + chineseStr;
							// chineseStr = "万" + chineseStr;
						}
						if (!"".equals(partChinese)) {
							chineseStr = "万" + chineseStr;
						}
					}
				}
			}
			chineseStr = partChinese + chineseStr;
		}

		// if ("".equals(chineseStr)){ // 整数部分为 0, 则表达为"零圆"
		// chineseStr = chineseDigits[0];
		// }
		if ((chineseStr).length() > 0) {
			chineseStr = chineseStr + "圆";
		} else {
			chineseStr += "零圆";
		}
		if (numFen == 0 && numJiao == 0) {
			chineseStr = chineseStr + "整";
		} else if (numFen == 0) { // 0 分，角数不为 0
			chineseStr = chineseStr + chineseDigits[numJiao] + "角整";
		} else { // "分"数不为 0
			if (numJiao == 0)
				chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";
			else
				chineseStr = chineseStr + chineseDigits[numJiao] + "角"
						+ chineseDigits[numFen] + "分";
		}
		if (negative) // 整数部分不为 0, 并且原金额为负数
			chineseStr = "负" + chineseStr;

		return chineseStr;

	}
	
	/**
	 * 把中文数字解析为阿拉伯数字(先转换所有大写Integer)
	 * 
	 * @param chineseNumber
	 *            中文数字
	 * @return 阿拉伯数字(Integer),如果是无法识别的中文数字则返回-1
	 */
	public static int parseChineseNumber(String chineseNumber) {
		chineseNumber = chineseNumber.replace("仟", "千");
		chineseNumber = chineseNumber.replace("佰", "百");
		chineseNumber = chineseNumber.replace("拾", "十");
		chineseNumber = chineseNumber.replace("玖", "九");
		chineseNumber = chineseNumber.replace("捌", "八");
		chineseNumber = chineseNumber.replace("柒", "七");
		chineseNumber = chineseNumber.replace("陆", "六");
		chineseNumber = chineseNumber.replace("伍", "五");
		chineseNumber = chineseNumber.replace("肆", "四");
		chineseNumber = chineseNumber.replace("叁", "三");
		chineseNumber = chineseNumber.replace("贰", "二");
		chineseNumber = chineseNumber.replace("壹", "一");
		return parseChineseNumber(chineseNumber, 1);
	}

	/**
	 * 把中文数字解析为阿拉伯数字(Integer)
	 * 
	 * @param preNumber
	 *            第二大的进位
	 * @param chineseNumber
	 *            中文数字
	 * @return 阿拉伯数字(Integer),如果是无法识别的中文数字则返回-1
	 */
	private static int parseChineseNumber(String chineseNumber, int preNumber) {
		int ret = 0;
		if (chineseNumber.indexOf("零") == 0) {
			int index = 0;
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1);
		} else if (chineseNumber.indexOf("亿") != -1) {
			int index = chineseNumber.indexOf("亿");
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(0, index);
			if (prefix.length() == 0) {
				prefix = "一";
			}
			String postfix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1) * 100000000 + parseChineseNumber(postfix, 10000000);
		} else if (chineseNumber.indexOf("万") != -1) {
			int index = chineseNumber.indexOf("万");
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(0, index);
			if (prefix.length() == 0) {
				prefix = "一";
			}
			String postfix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1) * 10000 + parseChineseNumber(postfix, 1000);
		} else if (chineseNumber.indexOf("千") != -1) {
			int index = chineseNumber.indexOf("千");
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(0, index);
			if (prefix.length() == 0) {
				prefix = "一";
			}
			String postfix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1) * 1000 + parseChineseNumber(postfix, 100);
		} else if (chineseNumber.indexOf("百") != -1) {
			int index = chineseNumber.indexOf("百");
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(0, index);
			if (prefix.length() == 0) {
				prefix = "一";
			}
			String postfix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1) * 100 + parseChineseNumber(postfix, 10);
		} else if (chineseNumber.indexOf("十") != -1) {
			int index = chineseNumber.indexOf("十");
			int end = chineseNumber.length();
			String prefix = chineseNumber.substring(0, index);
			if (prefix.length() == 0) {
				prefix = "一";
			}
			String postfix = chineseNumber.substring(index + 1, end);
			ret = parseChineseNumber(prefix, 1) * 10 + parseChineseNumber(postfix, 1);
		} else if (chineseNumber.equals("一")) {
			ret = 1 * preNumber;
		} else if (chineseNumber.equals("二")) {
			ret = 2 * preNumber;
		} else if (chineseNumber.equals("三")) {
			ret = 3 * preNumber;
		} else if (chineseNumber.equals("四")) {
			ret = 4 * preNumber;
		} else if (chineseNumber.equals("五")) {
			ret = 5 * preNumber;
		} else if (chineseNumber.equals("六")) {
			ret = 6 * preNumber;
		} else if (chineseNumber.equals("七")) {
			ret = 7 * preNumber;
		} else if (chineseNumber.equals("八")) {
			ret = 8 * preNumber;
		} else if (chineseNumber.equals("九")) {
			ret = 9 * preNumber;
		} else if (chineseNumber.length() == 0) {
			ret = 0;
		} else {
			ret = -1;
		}
		return ret;
	}
	

	/**
	 * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
	 * 
	 * @param amountPart
	 * @return
	 */
	private static String partTranslate(int amountPart) {

		if (amountPart < 0 || amountPart > 10000) {
			throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
		}

		String[] units = new String[] { "", "拾", "佰", "仟" };

		int temp = amountPart;

		String amountStr = new Integer(amountPart).toString();
		int amountStrLength = amountStr.length();
		boolean lastIsZero = true; // 在从低位往高位循环时，记录上一位数字是不是 0
		String chineseStr = "";

		for (int i = 0; i < amountStrLength; i++) {
			if (temp == 0) // 高位已无数据
				break;
			int digit = temp % 10;
			if (digit == 0) { // 取到的数字为 0
				if (!lastIsZero) // 前一个数字不是 0，则在当前汉字串前加"零"字;
					chineseStr = "零" + chineseStr;
				lastIsZero = true;
			} else { // 取到的数字不是 0
				chineseStr = chineseDigits[digit] + units[i] + chineseStr;
				lastIsZero = false;
			}
			temp = temp / 10;
		}
		return chineseStr;
	}
}

class ChineseCharacter {
	public static String[] chineseDigits = new String[] { "零", "一", "二", "三",
			"四", "五", "六", "七", "八", "九" };

	/**
	 * 把数值转换为汉字表示的数值
	 * 
	 * @param amount
	 * @return
	 */
	public static String getChineseCharacter(double value, String sUnit,
			int scale) {

		if (value > 999999999999999.99 || value < -999999999999999.99)
			throw new RuntimeException(
					"参数值超出允许范围 (-999999999999999.99 ～ 999999999999999.99)！");

		String Prefix = "";
		if (value < 0) {
			value = -value;

			Prefix = "负";
		}
		BigDecimal dval = new BigDecimal(value);
		dval = dval.setScale(scale, BigDecimal.ROUND_HALF_UP);
		// if(StringUtil.instr(dval + "","E")>-1){
		// dval = big.setScale(10,BigDecimal.ROUND_CEILING);
		// }
		long zhen = dval.longValue();
		String[] aTmp = StringUtil.split(dval + "", ".");

		int xiao = 0;
		String sXiao = "";

		if (aTmp.length > 1) {
			xiao = VariantConvertor.toInt(aTmp[1]);
			sXiao = aTmp[1];
		}

		String strTemp = "";

		// 直接为零
		String ConvertChinese = "";
		if (zhen == 0 && xiao == 0) {
			ConvertChinese = "零" + sUnit;
			return ConvertChinese;
		}

		// 最高上限 10 ^ 16

		// 兆
		long ntemp = (long) (zhen / Math.pow(10, 12));
		zhen = (long) (zhen - ntemp * Math.pow(10, 12));

		boolean nChanged = false;
		if (ntemp != 0) {
			strTemp = mConvert4(ntemp) + "兆";
			nChanged = true;
		}

		// 亿
		ntemp = (long) (zhen / 100000000);
		zhen = zhen - ntemp * 100000000; // zhen Mod 100000000

		if (ntemp != 0) {
			if (ntemp < 1000 && nChanged) {
				strTemp = strTemp + "零";
			}

			strTemp = mConvert4(ntemp) + "亿";
			nChanged = true;
		}

		// 万
		ntemp = (long) (zhen / 10000);
		zhen = zhen % 10000;

		if (ntemp != 0) {
			if (ntemp < 1000 && nChanged) {
				strTemp = strTemp + "零";
			}

			strTemp = strTemp + mConvert4(ntemp) + "万";
			nChanged = true;
		}

		// 万以下
		ntemp = zhen;

		if (ntemp != 0) {
			if (ntemp < 1000 && nChanged) {
				strTemp = strTemp + "零";
			}

			strTemp = strTemp + mConvert4(ntemp);
			nChanged = true;
		}

		// 小数
		if (xiao != 0) {
			if ("".equals(strTemp)) {
				strTemp = "零";
			}
			strTemp = strTemp + "点";
			for (int i = 0; i < 10; i++) {
				sXiao = StringUtil.replaceAll(sXiao,
						VariantConvertor.toString(i), chineseDigits[i]);
			}
			strTemp = strTemp + sXiao;
		}
		strTemp = strTemp + sUnit;
		return Prefix + strTemp;
	}

	// Used by ConvertChinese
	private static String mConvert4(long zhen) {
		if (zhen == 0) {
			return "";
		}
		String[] bDigits = { "", "", "十", "百", "千" };

		int zhenCount = 0;
		zhenCount = ((Long) zhen).toString().length();
		int nScale = 0;
		for (int i = 1; i <= zhenCount; i++) {
			if (nScale == 0) {
				nScale = 1;
			} else {
				nScale = nScale * 10;
			}
		}

		long[] nDigits = new long[5];
		for (int i = zhenCount; i > 0; i--) {
			nDigits[i] = (long) (zhen / nScale);
			zhen = zhen % nScale;
			nScale = nScale / 10;
		}
		String strTemp = chineseDigits[(int) nDigits[zhenCount]]
				+ bDigits[zhenCount];
		int lastZero = 0;
		String zeroStr = "";
		for (int i = zhenCount - 1; i >= 1; i--) {

			if (nDigits[i] != 0) {
				strTemp = strTemp + zeroStr + chineseDigits[(int) nDigits[i]]
						+ bDigits[i];
				lastZero = 0;
				zeroStr = "";
			} else {
				if (lastZero != 1) {
					zeroStr = "零";
					lastZero = 1;
				}
			}

		}
		return strTemp;
	}
}