package com.iteye.wwwcomy.lxn.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 类型转换之用
 * 
 * @author wangyh
 * 
 */
public class VariantConvertor {

	/**
	 * 判断数据是否为空
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isEmpty(Object v) {
		if (v == null) {
			return true;
		}
		if (v instanceof String) {
			return ((String) v).length() == 0;
		} else if (v instanceof Integer) {
			return ((Integer) v).equals(0);
		} else if (v instanceof Double) {
			return ((Double) v).equals(0.0);
		} else if (v instanceof Integer[]) {
			return ((Integer[]) v).length > 0 && ((Integer[]) v)[0] <= 0;
		} else if (v instanceof Double[]) {
			return ((Double[]) v).length > 0 && ((Double[]) v)[0] <= 0;
		} else if (v instanceof java.util.Date || v instanceof java.sql.Date) {
			return false;
		}
		assert false;
		return false;
	}

	/**
	 * d1 before d2?
	 * 
	 * @param d1
	 * @param d2
	 * @return boolean
	 */
	public static boolean before(Object d1, Object d2) {
		if (d1 instanceof java.util.Date && d2 instanceof java.util.Date) {
			return VariantConvertor.toDate(d1).before(
					VariantConvertor.toDate(d2));
		} else if (d1 instanceof java.lang.Integer
				&& d2 instanceof java.lang.Integer) {
			return VariantConvertor.toInt(d1) < VariantConvertor.toInt(d2);
		} else if (d1 instanceof java.lang.String
				&& d2 instanceof java.lang.String) {
			return VariantConvertor.toString(d1).compareTo(
					VariantConvertor.toString(d2)) < 0;
		} else if (StringUtil.isNumeric(d1) && StringUtil.isNumeric(d2)) {
			return VariantConvertor.toInt(d1) < VariantConvertor.toInt(d2);
		} else if ((StringUtil.isNumeric(d1) && d2 instanceof java.util.Date)
				|| (StringUtil.isNumeric(d2) && d1 instanceof java.util.Date)) {
			return VariantConvertor.toInt(d1) < VariantConvertor.toInt(d2);
		} else if (d1 instanceof String || d2 instanceof String) {
			return VariantConvertor.toInt(VariantConvertor.toDate(d1)) < VariantConvertor
					.toInt(VariantConvertor.toDate(d2));
		} else {
			// TODO 需要验证
			if (d1 != null && d2 == null) {
				return true;
			} else if (d1 == null && d2 != null) {
				return false;
			} else if (d1 == null && d2 == null) {
				return false;
			}
			throw new RuntimeException("非法类型");
		}
	}

	/**
	 * 从两个期间值中取一个大值
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Object getMaxPeriod(Object v1, Object v2) {
		// ' 从2个日期里取一个最大值ֵ
		if (VariantConvertor.before(v1, v2)) {
			return v2;
		} else {
			return v1;
		}
	}

	/**
	 * 从两个期间值中取一个小值
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Object getMinPeriod(Object v1, Object v2) {
		// ' 从2个日期里取一个最大值ֵ
		if (VariantConvertor.before(v1, v2)) {
			return v1;
		} else {
			return v2;
		}
	}

	/**
	 * 转化为Timestamp类型
	 * 
	 * @param value
	 * @return
	 */
	public static Timestamp toTimestamp(Object value) {
		if (StringUtil.isBlankOrNull(value)) {
			return null;
		}

		if (value instanceof String) {
			if ("null".equalsIgnoreCase(value.toString().trim()))
				return null;

			// java.util.Date d = DateUtil.getDate(
			// DateUtil.format(v, "yyyy-MM-dd HH:mm:ss"),
			// "yyyy-MM-dd HH:mm:ss");
			java.util.Date d = DateUtil.getDate(value.toString(),
					"yyyy-MM-dd HH:mm:ss");
			return new Timestamp(d.getTime());
		} else {
			java.util.Date d = VariantConvertor.toDate(value);
			if (d == null)
				return null;
			return new Timestamp(d.getTime());
		}
	}

	/**
	 * 将数据转化为Date类型
	 * 
	 * @param value
	 * @return Date
	 */
	public static Date toDate(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Long) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = (Long) value;
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Integer) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = (Integer) value;
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Double) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = ((Double) value).longValue();
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof String) {
			if ("".equals(value)) {
				return null;
			} else if (StringUtil.isNumeric(value)) {
				Double d = Double.parseDouble((String) value);
				int l = d.intValue();
				l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
				return new Date(l);
			}
			try {
				return DateUtil.getDate("" + value);
				// return new Simple DateFormat("yyyy-MM-dd").parse((String)
				// value);
			} catch (Exception e) {
				System.out.println("时间处理错误 ， 传入的时间值为：[" + value + "]");
				if (StringUtil.isNumeric(value)) {
					return toDate(new Double("" + value).intValue());
				} else if ((("" + value).length() == 8)
						&& (("" + value).lastIndexOf(":") == 5)) {
					return DateUtil.getDate(("" + value), "HH:mm:ss");
				}
				// e.printStackTrace();
				throw new RuntimeException(e);
			} catch (Throwable e) {
				if (StringUtil.isNumeric(value))
					return toDate(new Double("" + value).intValue());
				// e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// else if (value instanceof Boolean) { // TODO 未实现公式返回的值
		// DebugUtil.error(">某公式没实现返回Boolean型日期值");
		// return new Date();
		// }
		// assert false;
		return null;
	}

	/**
	 * 将数据转化为int型
	 * 
	 * @param value
	 * @return int
	 */
	public static int toInt(Object value) {
		if (value == null) {
			return 0;
		} else if (value instanceof String) {
			if (!StringUtil.isNumeric(value)) {
				return 0;
			}
			Double d = Double.parseDouble((String) value);
			return d.intValue();
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Long) {
			return ((Long) value).intValue();
		} else if (value instanceof Double) {
			return ((Double) value).intValue();
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? -1 : 0;
		} else if (value instanceof Date) { // 将日期转化为int型,为了公式计算方便
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			return Long.valueOf(
					(((Date) value).getTime() + 1000 * 60 * 60 * 8)
							/ (1000 * 60 * 60 * 24)).intValue();
		} else if (value instanceof BigDecimal) {
			throw new RuntimeException("No expectation");
			// BigDecimal bd = (BigDecimal)o;
			// return bd.intValue();
		} else if (value instanceof Float) {
			return ((Float) value).intValue();
		} else if (value instanceof Object[]) {
			if (((Object[]) value).length == 1) {
				return toInt(((Object[]) value)[0]);
			}
		}
		// assert false;
		return 0;
	}

	/**
	 * 将数据转化为long型
	 * 
	 * @param value
	 * @return long
	 */
	public static long toLong(Object value) {
		if (value == null) {
			return 0;
		} else if (value instanceof String) {
			if (!StringUtil.isNumeric(value)) {
				return 0;
			}
			Double d = Double.parseDouble((String) value);
			return d.longValue();
		} else if (value instanceof Integer) {
			return ((Integer) value).longValue();
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Double) {
			return ((Double) value).longValue();
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? -1 : 0;
		} else if (value instanceof Date) { // 将日期转化为int型,为了公式计算方便
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			return Long.valueOf((((Date) value).getTime() + 1000 * 60 * 60 * 8)
					/ (1000 * 60 * 60 * 24));
		} else if (value instanceof BigDecimal) {
			throw new RuntimeException("No expectation");
			// BigDecimal bd = (BigDecimal)o;
			// return bd.intValue();
		} else if (value instanceof Float) {
			return ((Float) value).longValue();
		} else if (value instanceof Object[]) {
			if (((Object[]) value).length == 1) {
				return toLong(((Object[]) value)[0]);
			}
		}
		// assert false;
		return 0;
	}

	/**
	 * @param value
	 * @return
	 */
	public static Integer[] toIntArray(Object value) {
		Integer[] v;
		if (value == null) {
			return null;
		} else if (value instanceof Object[]) {
			Object[] t = (Object[]) value;
			v = new Integer[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = VariantConvertor.toInt(t[i]);
			}
			return v;
		} else if (value instanceof Integer[]) {
			return (Integer[]) value;
		} else if (value instanceof Integer) {
			v = new Integer[1];
			v[0] = (Integer) value;
			return v;
		} else {
			return null;
		}

	}

	/**
	 * @param value
	 * @return
	 */
	public static double[] toDoubleArray(Object value) {
		double[] v;
		if (value == null) {
			return null;
		} else if (value instanceof Object[] || value instanceof Integer[]) {
			Object[] t = (Object[]) value;
			v = new double[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = VariantConvertor.toDouble(t[i]).doubleValue();
			}
			return v;
		} else if (value instanceof double[]) {
			return (double[]) value;
		} else if (value instanceof Double) {
			v = new double[1];
			v[0] = Double.parseDouble(value.toString());
			return v;
		} else if (value instanceof Integer) {
			v = new double[1];
			v[0] = VariantConvertor.toDouble(value);
			return v;
		} else {
			return null;
		}
	}

	/**
	 * @param value
	 * @return
	 */
	public static java.util.Date[] toDateArray(Object value) {
		java.util.Date[] v;
		if (value == null) {
			return null;
		} else if (value instanceof Object[]) {
			Object[] t = (Object[]) value;
			v = new java.util.Date[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = VariantConvertor.toDate(t[i]);
			}
			return v;
		} else if (value instanceof java.util.Date[]) {
			return (java.util.Date[]) value;
		} else if (value instanceof java.util.Date) {
			v = new java.util.Date[1];
			v[0] = (java.util.Date) value;
			return v;
		} else {
			return null;
		}
	}

	/**
	 * 将Double BigDecimal转换成的字符串中小数点后多余的零去掉
	 * 
	 * @param value
	 * @return String
	 */
	public static String adaptFormat(String value) {
		String newValue = value;
		if (value.indexOf('.') >= 0) {
			int index = value.length() - 1;
			int lastIndex = 0;
			while (index > 0) {
				if (value.charAt(index) == '.') {
					lastIndex = index;
					break;
				} else if (value.charAt(index) != '0') {
					lastIndex = index + 1;
					break;
				}
				index--;
			}
			if (lastIndex > 0 && lastIndex < value.length()) {
				newValue = value.substring(0, lastIndex);
			}
		}
		return newValue;
	}

	/**
	 * 将数据转化为一个字符串型
	 * 
	 * @param value
	 * @return String
	 */
	public static String toString(Object value) {
		if (value == null) {
			return "";
		} else if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Integer) {
			return ((Integer) value).toString();
		} else if (value instanceof Double) {
			// 转string不需要科学计数
			BigDecimal b = new BigDecimal(value.toString());

			return adaptFormat(b.toString());
			// 原来的做法会造成小数精度缺省
			// if ((Double) value % 1 == 0)
			// return new DecimalFormat("#0").format((Double) value);
			// return new DecimalFormat("#0.000000").format((Double) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? "true" : "false";
		} else if (value instanceof Timestamp) {
			return DateUtil.format((Timestamp) value, "yyyy-MM-dd HH:mm:ss");
			// DateFormat dateFormat = new Simple DateFormat("yyyy-MM-dd
			// HH:mm:ss"); return dateFormat.format((Timestamp) value);
		} else if (value instanceof Time) {
			return DateUtil.format((Time) value, "yyyy-MM-dd HH:mm:ss");
			// Date Format dateFormat = new Simple DateFormat("yyyy-MM-dd
			// HH:mm:ss"); return dateFormat.format((Time) value);
		} else if (value instanceof Date) {
			return DateUtil.format((Date) value, "yyyy-MM-dd");
			// Date Format dateFormat = new Simple DateFormat("yyyy-MM-dd");
			// return dateFormat.format((Date) value);
			// return value.toString();
		} else if (value instanceof Integer[]) {
			return StringUtil.join((Object[]) value, ";");
		} else if (value instanceof Long) {
			return ((Long) value).toString();
		}
		// assert false;
		return "";
	}

	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean toBool(Object obj) {
		if (StringUtil.isNumeric(obj)) {
			if (toInt(obj) == 0) {
				return false;
			}
			return true;
		}
		if (StringUtil.isBoolean(obj)) {
			return new Boolean(obj.toString());
		}
		return false;
	}

	/**
	 * 将数据转化为boolean型
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean toBoolean(Object value) {
		if (value == null) {
			return false;
		} else if (value instanceof String) {
			if (StringUtil.isNumeric((String) value)) {
				if (Double.valueOf((String) value) == 0)
					return false;
				else
					return true;
			}
			return Boolean.parseBoolean((String) value);
		} else if (value instanceof Integer) {
			return !value.equals(0);
		} else if (value instanceof Double) {
			return !value.equals(0.0);
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		}
		assert false;
		return false;
	}

	// // cast to int
	// public static Integer toInt(Object o) throws TypeIncompatibleException {
	// if (o == null)
	// return null;
	// if (o instanceof Integer) {
	// Integer i = (Integer) o;
	// return i;
	// } else if (o instanceof Double) {
	// Double d = (Double) o;
	// return d.intValue();
	// } else if (o instanceof BigDecimal) {
	// throw new TypeIncompatibleException("No expectation");
	// // BigDecimal bd = (BigDecimal)o;
	// // return bd.intValue();
	// } else if (o instanceof String) {
	// try {
	// // if (StringUtil.isNumeric(o))
	// return Integer.parseInt((String) o);
	// } catch (NumberFormatException e) {
	// throw new TypeIncompatibleException(
	// "Incompatible type or unsupported data format");
	// }
	// } else {
	// throw new TypeIncompatibleException(
	// "Incompatible type or unsupported data format");
	// }
	// }

	// cast to double
	public static Double toDouble(Object o) throws RuntimeException {
		if (o == null)
			return 0.0;
		if (o instanceof Double) {
			Double d = (Double) o;
			return d;
		} else if (o instanceof Integer) {
			Integer i = (Integer) o;
			return new Double(i);
		} else if (o instanceof BigDecimal) {
			// throw new TypeIncompatibleException("No expectation");
			BigDecimal bd = (BigDecimal) o;
			return bd.doubleValue();
		} else if (o instanceof String) {
			try {
				// 处理空字符串
				if (o.toString().length() == 0) {
					return 0.0;
				}
				// if (StringUtil.isNumeric(o))
				return Double.parseDouble((String) o);
			} catch (NumberFormatException e) {
				throw new RuntimeException(
						"Incompatible type or unsupported data format");
			}
		} else if (o instanceof Date) { // 将日期转化为int型,为了公式计算方便
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			return new Double(toInt(o));
		} else {
			throw new RuntimeException(
					"Incompatible type or unsupported data format");
		}
	}

	// cast to BigDecimal
	public static BigDecimal toBigDecimal(Object o) throws RuntimeException {
		if (o == null)
			return BigDecimal.ZERO;
		if (o instanceof Double) {
			return BigDecimal.valueOf((Double) o);
		} else if (o instanceof Integer) {
			return BigDecimal.valueOf((Integer) o);
		} else if (o instanceof Long) {
			return BigDecimal.valueOf((Long) o);
		} else if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		} else if (o instanceof String) {
			if (((String) o).isEmpty())
				return BigDecimal.ZERO;
			else
				return new BigDecimal((String) o);
		} else if (o instanceof Date) { // 将日期转化为int型,为了公式计算方便
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			return BigDecimal.valueOf(((Date) o).getTime());
		} else {
			throw new RuntimeException(
					"Incompatible type or unsupported data format");
		}
	}

	// cast to shallow boolean
	public static Boolean toShallowBoolean(Object o, boolean dealNullTrue)
			throws RuntimeException {
		return BooleanUtil.shallowCheckVariable(o, dealNullTrue);
	}

	// cast to deep boolean
	public static Boolean toDeepBoolean(Object o, boolean dealNullTrue)
			throws RuntimeException {
		return BooleanUtil.deepCheckVariable(o, dealNullTrue);
	}

	// cast to string
	public static String toString(Object o, String valueWhenNull) {
		if (o == null)
			return valueWhenNull;
		return o.toString();
	}

	/**
	 * 判断数据是否相同,根据前一个对象的类型对第二个对象进行比较. 这个方法会导致 1 isEquals 1.1,使用此方法时要确保类型相同.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isEquals(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		} else if (o1 instanceof Long) {
			return ((Long) o1).equals(toLong(o2));
		} else if (o1 instanceof Integer) {
			return ((Integer) o1).equals(toInt(o2));
		} else if (o1 instanceof String) {
			return ((String) o1).equals(toString(o2));
		} else if (o1 instanceof Double) {
			return ((Double) o1).compareTo(toDouble(o2)) == 0 ? true : false;
		} else if (o1 instanceof Date) {
			return ((Date) o1).equals(toDate(o2));
		} else {
			throw new RuntimeException("比较时未处理的类型" + o1 + ":" + o2);
		}
	}

	/**
	 * @param value
	 * @return
	 */
	public static Object[] toObjectArray(Object value) {
		Object[] v;
		if (value == null) {
			return null;
		} else if (value instanceof Object[]) {
			return (Object[]) value;
		} else {
			v = new Object[1];
			v[0] = value;
			return v;
		}
	}

	/**
	 * 按照给定的类型进行转换处理
	 * 
	 * @param paras
	 * @param paraTypes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] toParameterTypes(Object paras[], Class[] paraTypes) {
		for (int index = 0; index < paraTypes.length; index++) {
			Class<?> type = paraTypes[index];
			if (type == Integer.class || type == int.class) {
				paras[index] = VariantConvertor.toInt(paras[index]);
			} else if (type == Double.class || type == double.class) {
				paras[index] = VariantConvertor.toDouble(paras[index]);
			} else if (type == Long.class || type == long.class) {
				paras[index] = VariantConvertor.toLong(paras[index]);
			} else if (type == Float.class || type == float.class) {
				paras[index] = Float.parseFloat(VariantConvertor
						.toString(paras[index]));
			} else if (type == Boolean.class || type == boolean.class) {
				paras[index] = VariantConvertor.toBoolean(paras[index]);
			} else if (type == Date.class) {
				paras[index] = VariantConvertor.toDate(paras[index]);
			} else if (type == BigDecimal.class) {
				paras[index] = VariantConvertor.toBigDecimal(paras[index]);
			}
		}
		return paras;
	}

	// 测试adaptFormat函数
	public static void main(String[] args) {
		System.out.println("Convert 1.000 To: " + adaptFormat("1.000"));
		System.out.println("Convert 1.010 To: " + adaptFormat("1.010"));
		System.out.println("Convert 1.011 To: " + adaptFormat("1.011"));
		System.out.println("Convert -1.011 To: " + adaptFormat("-1.011"));
		System.out.println("Convert 0.000 To: " + adaptFormat("0.000"));
		System.out.println("toTimestamp 2011-12-13 18:29:52 toTimestamp "
				+ toTimestamp("2011-12-13 18:29:52"));
		System.out.println("toTimestamp null toTimestamp " + toTimestamp(null));
		System.out.println("toTimestamp new Date() toTimestamp "
				+ toTimestamp(new Date()));
		System.out.println("toTimestamp long 123 toTimestamp "
				+ toTimestamp(123));
		System.out.println("toTimestamp long 15000 toTimestamp "
				+ toTimestamp(15000));
	}
}