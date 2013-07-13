package com.iteye.wwwcomy.lxn.utils;

/**
 * @author Liuxn
 *
 */
public class BooleanUtil {

	public static boolean shallowCheckVariable(Object o, boolean dealNullTrue) {
		if (o == null) {
			return dealNullTrue;
		} else {
			if (o instanceof Integer) {
				Integer intValue = (Integer) o;
				return intValue.intValue() != 0;
			} else if (o instanceof Double) {
				Double doubleValue = (Double) o;
				return doubleValue.doubleValue() != 0;
			} else if (o instanceof Boolean) {
				Boolean booleanValue = (Boolean) o;
				return booleanValue.booleanValue();
			} else if (o instanceof String) {
				String stringValue = (String) o;
				return stringValue.length() > 0;
			}
		}
		return false;
	}

	public static boolean deepCheckVariable(Object o, boolean dealNullTrue) {
		if (o == null) {
			return dealNullTrue;
		} else {
			if (o instanceof Integer) {
				Integer intValue = (Integer) o;
				return intValue.intValue() != 0;
			} else if (o instanceof Double) {
				Double doubleValue = (Double) o;
				return doubleValue.doubleValue() != 0;
			} else if (o instanceof Boolean) {
				Boolean booleanValue = (Boolean) o;
				return booleanValue.booleanValue();
			} else if (o instanceof String) {
				String stringValue = (String) o;
				return stringValue.equalsIgnoreCase("true");
			}
		}
		return false;
	}
}
