package com.iteye.wwwcomy.lxn.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class contains basic methods for performing validations that return the
 * correctly typed class based on the validation performed.
 * 
 * @version $Revision: 478334 $ $Date: 2006-11-22 21:31:54 +0000 (Wed, 22 Nov
 *          2006) $
 */
public class GenericTypeValidator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks if the value can safely be converted to a byte primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Byte value.
	 */
	public static Byte formatByte(String value) {
		if (value == null) {
			return null;
		}

		try {
			return new Byte(value);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a byte primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if null)
	 * @return the converted Byte value.
	 */
	public static Byte formatByte(String value, Locale locale) {
		Byte result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getNumberInstance(locale);
			} else {
				formatter = NumberFormat.getNumberInstance(Locale.getDefault());
			}
			formatter.setParseIntegerOnly(true);
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= Byte.MIN_VALUE
						&& num.doubleValue() <= Byte.MAX_VALUE) {
					result = new Byte(num.byteValue());
				}
			}
		}

		return result;
	}

	/**
	 * Checks if the value can safely be converted to a short primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Short value.
	 */
	public static Short formatShort(String value) {
		if (value == null) {
			return null;
		}

		try {
			return new Short(value);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a short primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if
	 *            null)vv
	 * @return the converted Short value.
	 */
	public static Short formatShort(String value, Locale locale) {
		Short result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getNumberInstance(locale);
			} else {
				formatter = NumberFormat.getNumberInstance(Locale.getDefault());
			}
			formatter.setParseIntegerOnly(true);
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= Short.MIN_VALUE
						&& num.doubleValue() <= Short.MAX_VALUE) {
					result = new Short(num.shortValue());
				}
			}
		}

		return result;
	}

	/**
	 * Checks if the value can safely be converted to a int primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Integer value.
	 */
	public static Integer formatInt(String value) {
		if (value == null) {
			return null;
		}

		try {
			return Double.valueOf(value).intValue();
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to an int primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if null)
	 * @return the converted Integer value.
	 */
	public static Integer formatInt(String value, Locale locale) {
		Integer result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getNumberInstance(locale);
			} else {
				formatter = NumberFormat.getNumberInstance(Locale.getDefault());
			}
			formatter.setParseIntegerOnly(true);
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= Integer.MIN_VALUE
						&& num.doubleValue() <= Integer.MAX_VALUE) {
					result = num.intValue();
				}
			}
		}

		return result;
	}

	/**
	 * Checks if the value can safely be converted to a long primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Long value.
	 */
	public static Long formatLong(String value) {
		if (value == null) {
			return null;
		}

		try {
			return new Long(value);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a long primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if null)
	 * @return the converted Long value.
	 */
	public static Long formatLong(String value, Locale locale) {
		Long result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getNumberInstance(locale);
			} else {
				formatter = NumberFormat.getNumberInstance(Locale.getDefault());
			}
			formatter.setParseIntegerOnly(true);
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= Long.MIN_VALUE
						&& num.doubleValue() <= Long.MAX_VALUE) {
					result = new Long(num.longValue());
				}
			}
		}

		return result;
	}

	/**
	 * Checks if the value can safely be converted to a float primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Float value.
	 */
	public static Float formatFloat(String value) {
		if (value == null) {
			return null;
		}

		try {
			return new Float(value);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a float primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if null)
	 * @return the converted Float value.
	 */
	public static Float formatFloat(String value, Locale locale) {
		Float result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getInstance(locale);
			} else {
				formatter = NumberFormat.getInstance(Locale.getDefault());
			}
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= (Float.MAX_VALUE * -1)
						&& num.doubleValue() <= Float.MAX_VALUE) {
					result = new Float(num.floatValue());
				}
			}
		}

		return result;
	}

	/**
	 * Checks if the value can safely be converted to a double primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return the converted Double value.
	 */
	public static Double formatDouble(String value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String
				&& ("true".equalsIgnoreCase(value) || "false"
						.equalsIgnoreCase(value)))
			return null;
		try {
			return new Double(value);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a double primitive.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The locale to use to parse the number (system default if null)
	 * @return the converted Double value.
	 */
	public static Double formatDouble(String value, Locale locale) {
		Double result = null;

		if (value != null) {
			NumberFormat formatter = null;
			if (locale != null) {
				formatter = NumberFormat.getInstance(locale);
			} else {
				formatter = NumberFormat.getInstance(Locale.getDefault());
			}
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);

			// If there was no error and we used the whole string
			if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length()) {
				if (num.doubleValue() >= (Double.MAX_VALUE * -1)
						&& num.doubleValue() <= Double.MAX_VALUE) {
					result = new Double(num.doubleValue());
				}
			}
		}

		return result;
	}

	/**
	 * <p>
	 * 
	 * Checks if the field is a valid date. The <code>Locale</code> is used with
	 * <code>java.text.DateFormat</code>. The setLenient method is set to
	 * <code>false</code> for all.
	 * </p>
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The Locale to use to parse the date (system default if null)
	 * @return the converted Date value.
	 */
	public static Date formatDate(String value, Locale locale) {
		Date date = null;

		if (value == null) {
			return null;
		}

		try {
			DateFormat formatter = null;
			if (locale != null) {
				formatter = DateFormat
						.getDateInstance(DateFormat.SHORT, locale);
			} else {
				formatter = DateFormat.getDateInstance(DateFormat.SHORT,
						Locale.getDefault());
			}

			formatter.setLenient(false);

			date = formatter.parse(value);
		} catch (ParseException e) {
			// Bad date so return null
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * <p>
	 * Checks if the field is a valid date. The pattern is used with
	 * <code>java.text.SimpleDateFormat</code> . If strict is true, then the
	 * length will be checked so '2/12/1999' will not pass validation with the
	 * format 'MM/dd/yyyy' because the month isn't two digits. The setLenient
	 * method is set to <code>false</code> for all.
	 * </p>
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param datePattern
	 *            The pattern passed to <code>SimpleDateFormat</code>.
	 * @param strict
	 *            Whether or not to have an exact match of the datePattern.
	 * @return the converted Date value.
	 */
	public static Date formatDate(String value, String datePattern,
			boolean strict) {
		Date date = null;

		if (value == null || datePattern == null || datePattern.length() == 0) {
			return null;
		}

		try {
			SimpleDateFormat formatter = DateUtil.getDateFormat(datePattern);
			formatter.setLenient(false);

			date = formatter.parse(value);

			if (strict) {
				if (datePattern.length() != value.length()) {
					date = null;
				}
			}
		} catch (ParseException e) {
			// Bad date so return null
			e.printStackTrace();
		}

		return date;
	}

	// /**
	// * <p>
	// * Checks if the field is a valid credit card number.
	// * </p>
	// * <p>
	// *
	// * Reference Sean M. Burke's <a
	// * href="http://www.ling.nwu.edu/~sburke/pub/luhn_lib.pl"> script</a> .
	// * </p>
	// *
	// * @param value
	// * The value validation is being performed on.
	// * @return the converted Credit Card number.
	// */
	// public static Long formatCreditCard(String value) {
	// return GenericValidator.isCreditCard(value) ? new Long(value) : null;
	// }

}