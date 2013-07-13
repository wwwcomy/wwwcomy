package com.iteye.wwwcomy.lxn.utils;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

public class DateUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** */
	private static String DATE_DIVISION = "-";

	/** */
	private static String TIME_DIVISION = ":";

	/** */
	private static String DATE_TIME_DIVISION = " ";

	/** */
	private int m_year;

	/** */
	private int m_month;

	/** */
	private int m_day;

	/** */
	private int m_hour;

	/** */
	private int m_minute;

	/** */
	private int m_second;

	/**
	 * 
	 */
	public DateUtil() {
		GregorianCalendar now = new GregorianCalendar();
		m_year = now.get(1);
		m_month = now.get(2) + 1;
		m_day = now.get(5);
		m_hour = now.get(11);
		m_minute = now.get(12);
		m_second = now.get(13);
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @throws Throwable
	 */
	public DateUtil(int year, int month, int day, int hour, int minute, int second) throws Throwable {
		if (!isValid(year, month, day, hour, minute, second)) {
			throw new Exception("DateUtil(" + year + "," + month + "," + day + "," + hour + "," + minute + "," + second + "):无效的日期格式");
		} else {
			m_year = year;
			m_month = month;
			m_day = day;
			m_hour = hour;
			m_minute = minute;
			m_second = second;
			return;
		}
	}

	/**
	 * @param dateUtil
	 * @throws Throwable
	 */
	public DateUtil(DateUtil dateUtil) throws Throwable {
		if (dateUtil == null) {
			throw new Exception("DateUtil(" + dateUtil + "):无效参数!");
		}
		if (!dateUtil.isValid()) {
			throw new Exception("DateUtil(" + dateUtil + "):无效参数!");
		} else {
			m_year = dateUtil.m_year;
			m_month = dateUtil.m_month;
			m_day = dateUtil.m_day;
			m_hour = dateUtil.m_hour;
			m_minute = dateUtil.m_minute;
			m_second = dateUtil.m_second;
			return;
		}
	}

	/**
	 * @param date
	 * @throws Throwable
	 */
	public DateUtil(java.util.Date date) throws Throwable {
		String s = DateUtil.getStringValueNew(date);
		int r[] = getDateFields(s);
		m_year = r[0];
		m_month = r[1];
		m_day = r[2];
		m_hour = r[3];
		m_minute = r[4];
		m_second = r[5];
		if (!isValid(m_year, m_month, m_day, m_hour, m_minute, m_second)) {
			throw new Exception("DateUtil(" + date.toString() + "):无效的日期格式");
		} else {
			return;
		}
	}

	/**
	 * @param date
	 * @throws Throwable
	 */
	public DateUtil(String date) throws Throwable {
		int r[] = getDateFields(date);
		m_year = r[0];
		m_month = r[1];
		m_day = r[2];
		m_hour = r[3];
		m_minute = r[4];
		m_second = r[5];
		if (!isValid(m_year, m_month, m_day, m_hour, m_minute, m_second)) {
			throw new Exception("DateUtil(" + date + "):无效的日期格式");
		} else {
			return;
		}
	}

	public void setDate(Object v) throws Throwable {
		if (v == null)
			return;
		if (v instanceof String || v instanceof java.util.Date) {
			String s;
			if (v instanceof java.util.Date) {
				s = DateUtil.getStringValueNew((java.util.Date) v);
			} else {
				s = v.toString();
			}
			int r[] = getDateFields(s);
			m_year = r[0];
			m_month = r[1];
			m_day = r[2];
			m_hour = r[3];
			m_minute = r[4];
			m_second = r[5];
		} else {
			throw new Exception("SetDate(" + v + "):无效的日期值");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object v) {
		if (v == null) {
			return false;
		}
		if (!(v instanceof DateUtil)) {
			return false;
		}
		DateUtil d = (DateUtil) v;
		return m_year == d.m_year && m_month == d.m_month && m_day == d.m_day && m_hour == d.m_hour && m_minute == d.m_minute
				&& m_second == d.m_second;
	}

	/**
	 * @param days
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getAdvanceDays(int days) throws Throwable {
		DateUtil r = new DateUtil(this);
		r.advanceDays(days);
		return r;
	}

	/**
	 * @param days
	 */
	public void advanceDays(int days) {
		fromJulian(toJulian() + days);
	}

	/**
	 * @param j
	 */
	private void fromJulian(int j) {
		int ja = j;
		int JGREG = 0x231519;
		if (j >= JGREG) {
			int jalpha = (int) (((double) (float) (j - 0x1c7dd0) - 0.25D) / 36524.25D);
			ja += (1 + jalpha) - (int) (0.25D * (double) jalpha);
		}
		int jb = ja + 1524;
		int jc = (int) (6680D + ((double) (float) (jb - 0x253abe) - 122.09999999999999D) / 365.25D);
		int jd = (int) ((double) (365 * jc) + 0.25D * (double) jc);
		int je = (int) ((double) (jb - jd) / 30.600100000000001D);
		m_day = jb - jd - (int) (30.600100000000001D * (double) je);
		m_month = je - 1;
		if (m_month > 12) {
			m_month -= 12;
		}
		m_year = jc - 4715;
		if (m_month > 2) {
			m_year--;
		}
		if (m_year <= 0) {
			m_year--;
		}
	}

	/**
	 * @param t
	 * @return int[]
	 * @throws Throwable
	 */
	private static int[] getDateFields(String t) throws Throwable {
		// String dayAndtime[] = getStringsFromStr(t, DATE_TIME_DIVISION);
		String dayAndtime[] = StringUtil.split(t, DATE_TIME_DIVISION);
		if (dayAndtime.length != 2 && dayAndtime.length != 1) {
			throw new Exception("DateUtil.getDateFields(" + t + "):无效的日期格式");
		}
		int dates[] = getIntsFromStr(dayAndtime[0], DATE_DIVISION);
		if (dates.length != 3) {
			throw new Exception("DateUtil.getDateFields(" + t + "):无效的日期格式");
		}
		int times[] = new int[3];
		if (dayAndtime.length == 2) {
			int ts[] = getIntsFromStr(dayAndtime[1], TIME_DIVISION);
			if (ts.length > 3) {
				throw new Exception("DateUtil.getDateFields(" + t + "):无效的日期格式");
			}
			if (ts.length == 1) {
				times[0] = ts[0];
				times[1] = 0;
				times[2] = 0;
			}
			if (ts.length == 2) {
				times[0] = ts[0];
				times[1] = ts[1];
				times[2] = 0;
			}
			if (ts.length == 3) {
				times[0] = ts[0];
				times[1] = ts[1];
				times[2] = ts[2];
			}
		} else {
			times[0] = 0;
			times[1] = 0;
			times[2] = 0;
		}
		int r[] = new int[6];
		r[0] = dates[0];
		r[1] = dates[1];
		r[2] = dates[2];
		r[3] = times[0];
		r[4] = times[1];
		r[5] = times[2];
		return r;
	}

	/**
	 * @return String
	 */
	public String getDateString() {
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0" + String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0" + String.valueOf(m_day);
		return String.valueOf(m_year) + DATE_DIVISION + affMonth + DATE_DIVISION + affDay;
	}

	/**
	 * @return String
	 */
	public String getDateStringNew() {
		//
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0" + String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0" + String.valueOf(m_day);
		return String.valueOf(m_year) + affMonth + affDay;
	}

	/**
	 * @return String
	 */
	public String getFullString() {
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0" + String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0" + String.valueOf(m_day);
		String affHour = m_hour >= 10 ? String.valueOf(m_hour) : "0" + String.valueOf(m_hour);
		String affMinute = m_minute >= 10 ? String.valueOf(m_minute) : "0" + String.valueOf(m_minute);
		String affSecond = m_second >= 10 ? String.valueOf(m_second) : "0" + String.valueOf(m_second);
		return String.valueOf(m_year) + DATE_DIVISION + affMonth + DATE_DIVISION + affDay + DATE_TIME_DIVISION + affHour + TIME_DIVISION
				+ affMinute + TIME_DIVISION + affSecond;
	}

	// /**
	// * @return String
	// * @throws Throwable
	// */
	// public static String getStringValue(java.util.Date date) throws Throwable
	// {
	// // String s = DateUtil.getStringValue(date);
	// // int r[] = getDateFields(s);
	// // int m_year = r[0];
	// // int m_month = r[1];
	// // int m_day = r[2];
	// // int m_hour = r[3];
	// // int m_minute = r[4];
	// // int m_second = r[5];
	// // if (!isValid(m_year, m_month, m_day, m_hour, m_minute, m_second)) {
	// // throw new Exception("getStringValue(" + date + "):无效的日期格式");
	// // }
	// // String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0"
	// // + String.valueOf(m_month);
	// // String affDay = m_day >= 10 ? String.valueOf(m_day) : "0"
	// // + String.valueOf(m_day);
	// // String affHour = m_hour >= 10 ? String.valueOf(m_hour) : "0"
	// // + String.valueOf(m_hour);
	// // String affMinute = m_minute >= 10 ? String.valueOf(m_minute) : "0"
	// // + String.valueOf(m_minute);
	// // String affSecond = m_second >= 10 ? String.valueOf(m_second) : "0"
	// // + String.valueOf(m_second);
	// // return String.valueOf(m_year) + DATE_DIVISION + affMonth
	// // + DATE_DIVISION + affDay + DATE_TIME_DIVISION + affHour
	// // + TIME_DIVISION + affMinute + TIME_DIVISION + affSecond;
	//
	// // DateUtil dateUtil = new DateUtil(date);
	// // return dateUtil.getFullString();
	// }

	/**
	 * @return String
	 * @throws Throwable
	 */
	public static String getStringValueNew(java.util.Date date) throws Throwable {
		// SimpleDateFormat formatter = getDateFormat("yyyy-MM-dd HH:mm:ss");
		// return formatter.format(date);
		return getDateFormatStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @return int
	 */
	public int getYear() {
		return m_year;
	}

	/**
	 * @return int
	 */
	public int getQuarter() {
		return (m_month + 2) / 3;
	}

	/**
	 * @return int
	 */
	public int getMonth() {
		return m_month;
	}

	/**
	 * @return int
	 */
	public int getDay() {
		return m_day;
	}

	/**
	 * @return int
	 */
	public int getHour() {
		return m_hour;
	}

	/**
	 * @return int
	 */
	public int getMinute() {
		return m_minute;
	}

	/**
	 * @return int
	 */
	public int getSecond() {
		return m_second;
	}

	/**
	 * @param str
	 * @param token
	 * @return int[]
	 */
	private static int[] getIntsFromStr(String str, String token) {
		StringTokenizer st = new StringTokenizer(str, token);
		Vector<String> v = new Vector<String>();
		for (; st.hasMoreTokens(); v.add(st.nextToken())) {
		}
		int ints[] = new int[v.size()];
		for (int i = 0; i < v.size(); i++) {
			ints[i] = Double.valueOf((String) v.get(i)).intValue();

		}
		return ints;
	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getRealMonthEnd() throws Throwable {
		DateUtil r = getMonthStart();
		if (r.getMonth() == 1 || r.getMonth() == 3 || r.getMonth() == 5 || r.getMonth() == 7 || r.getMonth() == 8 || r.getMonth() == 10
				|| r.getMonth() == 12) {
			r.advanceDays(30);
		}
		if (r.getMonth() == 4 || r.getMonth() == 6 || r.getMonth() == 9 || r.getMonth() == 11) {
			r.advanceDays(29);
		}
		if (r.getMonth() == 2) {
			int year = r.getYear();
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				r.advanceDays(28);
			} else {
				r.advanceDays(27);
			}
		}
		return r;
	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getRealMonthStart() throws Throwable {
		return new DateUtil(m_year, m_month, 1, 0, 0, 0);
	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getMonthStart() throws Throwable {
		// return new DateUtil(m_year, m_month, 1, 0, 0, 0);
		int m_year_last = m_year;
		int m_month_last = m_month - 1;
		int m_day_last = m_day;
		if (m_month_last == 0) {
			if (m_day_last >= 2006) {
				m_month_last = m_month;
			} else {
				m_month_last = 12;
				m_year_last = m_year - 1;
			}
		}
		return new DateUtil(m_year_last, m_month_last, 1, 0, 0, 0);

	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getMonthStartEx() throws Throwable {
		// Created by Pengsa
		int m_year_last = m_year;
		int m_month_last = m_month - 1;
		int m_day_last = m_day;
		if (m_day_last >= 1) {
			m_month_last = m_month;
		}
		if (m_month_last == 0) {
			m_month_last = 12;
			m_year_last = m_year - 1;
		}
		return new DateUtil(m_year_last, m_month_last, 1, 0, 0, 0);
	}

	/**
	 * @return Date
	 */
	public Date getSqlDate() {
		return Date.valueOf(getDateString());
	}

	/**
	 * @return String
	 */
	public String getTimeString() {
		//
		String affHour = m_hour >= 10 ? String.valueOf(m_hour) : "0" + String.valueOf(m_hour);
		String affMinute = m_minute >= 10 ? String.valueOf(m_minute) : "0" + String.valueOf(m_minute);
		String affSecond = m_second >= 10 ? String.valueOf(m_second) : "0" + String.valueOf(m_second);
		return affHour + TIME_DIVISION + affMinute + TIME_DIVISION + affSecond;
	}

	/**
	 * @return int
	 * @throws Throwable
	 */
	public int getWeekDay() throws Throwable {
		DateUtil std = new DateUtil("2000-01-03");
		int days = daysBetween(std);
		int m = days % 7;
		if (m >= 0) {
			return m + 1;
		} else {
			return m + 8;
		}
	}

	/**
	 * @return int
	 * @throws Throwable
	 */
	public int getWeekDay(int firstday) throws Throwable {
		DateUtil std = new DateUtil("2000-01-03");
		int days = daysBetween(std);
		int m = (days + firstday) % 7;
		return m + 1;
	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getWeekEnd() throws Throwable {
		DateUtil r = getWeekStart();
		r.advanceDays(7);
		return r;
	}

	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getWeekStart() throws Throwable {
		DateUtil std = new DateUtil("2000-01-03");
		int days = daysBetween(std);
		int m = days % 7;
		DateUtil r;
		if (m >= 0) {
			r = getAdvanceDays(-m);
		} else {
			r = getAdvanceDays(-m - 7);
		}
		return r;
	}

	/**
	 * @return boolean
	 */
	public boolean isValid() {
		return isValid(m_year, m_month, m_day, m_hour, m_minute, m_second);
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return boolean
	 */
	public static boolean isValid(int year, int month, int day, int hour, int minute, int second) {
		if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
			return false;
		}
		if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
			return false;
		}
		switch (month) {
		case 1: // '\001'
		case 3: // '\003'
		case 5: // '\005'
		case 7: // '\007'
		case 8: // '\b'
		case 10: // '\n'
		case 12: // '\f'
			return true;

		case 4: // '\004'
		case 6: // '\006'
		case 9: // '\t'
		case 11: // '\013'
			return day <= 30;

		case 2: // '\002'
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				return day <= 29;
			}
			return day <= 28;
		}
		return false;
	}

	/**
	 * @param sDate
	 * @return boolean
	 * @throws Throwable
	 */
	public static boolean isValid(String sDate) throws Throwable {
		int r[];
		try {
			r = getDateFields(sDate);
		} catch (Throwable e) {
			return false;
		}
		return isValid(r[0], r[1], r[2], r[3], r[4], r[5]);
	}

	/**
	 * @return int
	 */
	private int toJulian() {
		int jy = m_year;
		if (m_year < 0) {
			jy++;
		}
		int jm = m_month;
		if (m_month > 2) {
			jm++;
		} else {
			jy--;
			jm += 13;
		}
		int jul = (int) (Math.floor(365.25D * (double) jy) + Math.floor(30.600100000000001D * (double) jm) + (double) m_day + 1720995D);
		int IGREG = 0x8fc1d;
		if (m_day + 31 * (m_month + 12 * m_year) >= IGREG) {
			int ja = (int) (0.01D * (double) jy);
			jul += (2 - ja) + (int) (0.25D * (double) ja);
		}
		return jul;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getFullString();
	}

	/**
	 * @param d
	 * @return String
	 * @throws Throwable
	 */
	public static String viewDate(String d) throws Throwable {
		try {
			DateUtil date = new DateUtil(d);
			return date.getDateString();
		} catch (Throwable e) {
			return "";
		}
	}

	/**
	 * @param date
	 * @return int
	 */
	public int daysBetween(DateUtil date) {
		return toJulian() - date.toJulian();
	}

	/**
	 * @param sd
	 * @param ed
	 * @return int
	 */
	public static int between(DateUtil sd, DateUtil ed) {
		return ed.toJulian() - sd.toJulian();
	}

	/**
	 * @param sd
	 * @param ed
	 * @return int
	 * @throws Throwable
	 */
	public static int between(java.util.Date sd, java.util.Date ed) throws Throwable {
		DateUtil sd1 = new DateUtil(sd);
		DateUtil ed1 = new DateUtil(ed);
		return ed1.toJulian() - sd1.toJulian();
	}

	/**
	 * @param sd
	 * @param ed
	 * @return int
	 * @throws Throwable
	 */
	public static int between(String sd, String ed) throws Throwable {
		DateUtil sd1 = new DateUtil(sd);
		DateUtil ed1 = new DateUtil(ed);
		return ed1.toJulian() - sd1.toJulian();
	}

	/**
	 * @param curPeriod
	 * @param period
	 * @return
	 */
	public static boolean after(String curPeriod, String period) {
		//
		if (compare(curPeriod, period) == -1)
			return true;
		else
			return false;
	}

	/**
	 * @param curPeriod
	 * @param period
	 * @return boolean
	 */
	public static boolean before(String curPeriod, String period) {
		return !after(curPeriod, period);
	}

	/**
	 * @param curPeriod
	 * @param period
	 * @return
	 */
	public static int compare(String curPeriod, String period) {
		//
		java.util.Date date1 = getDate(curPeriod, "yyyy-MM-dd");
		java.util.Date date2 = getDate(period, "yyyy-MM-dd");
		if (date1.before(date2))
			return 1;
		if (date1.after(date2))
			return -1;
		return 0;
	}

	/**
	 * @return Timestamp
	 */
	public static Timestamp getCurrTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @return Date
	 */
	public static Date getCurrDate() {
		return new Date(getCurrTime().getTime());
	}

	/**
	 * @param timeStr
	 * @param dateStyle
	 * @return java.util.Date
	 */
	public static java.util.Date getDate(String timeStr, String dateStyle) {
		try {
			return getDateParse(timeStr, dateStyle);
		} catch (Throwable ex) {
			throw new RuntimeException(ex.getCause());
		}
	}

	/**
	 * @param timeStr
	 * @param dateStyle
	 * @return String
	 */
	public static String format(String timeStr, String dateStyle) {
		String style = dateStyle == null ? "yyyy-MM-dd" : dateStyle;
		java.util.Date date = getDate(timeStr, style);
		return format(date, style);
		// return new SimpleDateFormat(style).format(date);
	}

	/**
	 * 将日期类型转化为字符串类型
	 * 
	 * @param timeStr
	 * @param sFormat
	 * @return String
	 */
	public static String format(java.util.Date date, String sFormat) {
		sFormat = sFormat == null ? "yyyy-MM-dd" : sFormat;
		// SimpleDateFormat sy1 = null;
		// return new SimpleDateFormat(style).format(date);
		// if ("YY-MM-DD".equalsIgnoreCase(sFormat)) {
		// sy1 = new SimpleDateFormat("yyyy-MM-dd");
		// String sDate = sy1.format(date);
		// return sDate.substring(2, sDate.length());
		// } else if ("YYMMDD".equalsIgnoreCase(sFormat)) {
		// sy1 = new SimpleDateFormat("yyyy-MM-dd");
		// String sDate = sy1.format(date);
		// return sDate.substring(2, sDate.length()).replaceAll("-", "");
		// } else {
		if ("YY-MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "yy-MM-dd";
		else if ("YYMMDD".equalsIgnoreCase(sFormat))
			sFormat = "yyMMdd";
		else if ("MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "MM-dd";
		else if ("MMDD".equalsIgnoreCase(sFormat))
			sFormat = "MMdd";
		else if ("YYYY-MM".equalsIgnoreCase(sFormat))
			sFormat = "yyyy-MM";
		else if ("YYYYMM".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMM";
		else if ("YYYY-MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "yyyy-MM-dd";
		else if ("YYYY-MM-DD HH:MM:SS".equalsIgnoreCase(sFormat))
			sFormat = "yyyy-MM-dd HH:mm:ss";
		else if ("YYYY".equalsIgnoreCase(sFormat))
			sFormat = "yyyy";
		else if ("YYYYMMDD".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMdd";
		else if ("YYYYMMDDHHSS".equalsIgnoreCase(sFormat)) {
			sFormat = "yyyyMMddHHSS";
		} else if ("YYYYMMDDHHMMSS".equalsIgnoreCase(sFormat)) {
			sFormat = "yyyyMMddHHmmss";
		} else if ("YY-MM".equalsIgnoreCase(sFormat)) {
			sFormat = "yy-MM";
		} else if ("YYMM".equalsIgnoreCase(sFormat)) {
			sFormat = "yyMM";
		}
		// sFormat = "yyyy-MM-dd";
		// sy1 = new SimpleDateFormat(sFormat);
		// return sy1.format(date).replaceAll("-", "");
		// sy1 = getDateFormat(sFormat);
		// return sy1.format(date);
		return getDateFormatStr(date, sFormat);
		// }
	}

	private static String getDateFormatStr(java.util.Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	private static java.util.Date getDateParse(String sDate, String format) throws Throwable {
		if (sDate.endsWith("Z")) { // 有TZ的格式偏移24小时
			sDate = StringUtil.replaceAll(sDate.substring(0, sDate.length() - 1), "T", " ");
			java.util.Date d = new SimpleDateFormat(format).parse(sDate);
			return dateAdd(d, 1);
		}
		return new SimpleDateFormat(format).parse(sDate);
	}

	// private static HashMap<String, SimpleDateFormat> dateFormats = new
	// HashMap<String, SimpleDateFormat>();

	/**
	 * 取缓冲的SimpleDateFormat对象 假设系统使用的format种类不会太多
	 * 
	 * @param format
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormat(String format) {
		return new SimpleDateFormat(format);
		// if (!dateFormats.containsKey(format)) {
		// dateFormats.put(format, new SimpleDateFormat(format));
		// }
		// return dateFormats.get(format);
	}

	public static String format2(java.util.Date date, SimpleDateFormat sf) {
		return sf.format(date);
	}

	// /**
	// * 将日期类型转化为字符串类型
	// *
	// * @param timeStr
	// * @param dateStyle
	// * @return String
	// */
	// public static String format(java.sql.Date date, String dateStyle) {
	// String style = dateStyle == null ? "yyyy-MM-dd" : dateStyle;
	// return new SimpleDateFormat(style).format(date);
	// }

	/**
	 * 将日期类型转化为字符串类型
	 * 
	 * @param timeStr
	 * @param dateStyle
	 * @return String
	 */
	public static String format(Object date, String dateStyle) {
		if (date == null) {
			return null;
		}
		// if (date instanceof java.sql.Date) {
		// return format((java.sql.Date) date, dateStyle);
		// } else
		if (date instanceof java.util.Date) {
			return format((java.util.Date) date, dateStyle);
		} else {
			String s = date.toString();
			if (s.length() == 0) {
				return null;
			}
			return format(s, dateStyle);
		}
	}

	/**
	 * @throws ParseException
	 */
	public static String getCurrTimeStr() throws ParseException {
		return getCurrTimeStr(null);
	}

	/**
	 * @param dateStyle
	 * @return String
	 * @throws ParseException
	 */
	public static String getCurrTimeStr(String dateStyle) throws ParseException {
		if (dateStyle == null)
			dateStyle = "yyyy-MM-dd HH.mm.ss";
		return format(new java.util.Date(), dateStyle);
		// return new SimpleDateFormat(dateStyle).format(new java.util.Date());
	}

	public static Integer[] getMonthBetween(Integer lFirstMonth, Integer lStartMonth) {
		// '##BD 取得启用前一个月份和启用月份之间的所有月份
		// '##PD lFirstMonth 第一个月份
		// '##PD lStartMonth 启用月份
		// '##RD 返回月份的数组 >=lFirstMonth <lStartMonth
		// #If DEBUG_VERSION Or WRITELOG Then
		// If lFirstMonth >= lStartMonth Then
		// err.Raise 12345, "", "Jzh impossible! The lFirstMonth >=
		// lStartMonth!"
		// End If
		// #End If
		if (lFirstMonth == lStartMonth)
			// '两个月份之间没有月份
			return new Integer[0];
		// '先将月份格式从Format转化为Long型
		Integer lLFirstMonth = FMonthToLMonth(lFirstMonth);
		Integer lLStartMonth = FMonthToLMonth(lStartMonth);
		// '给数组分配空间
		Integer[] lTempArray = new Integer[lLStartMonth - lLFirstMonth];
		// '将内容分别填写进去
		for (int lLoop = lLFirstMonth; lLoop < lLStartMonth; lLoop++)
			lTempArray[lLoop - lLFirstMonth] = LMonthToFMonth(lLoop);
		return lTempArray;
	}

	public static Integer LMonthToFMonth(int lLMonth) {
		// '##BD 将yyyy * 12 + MM - 1转化为yyyyMM
		// '##PD yyyy * 12 + MM - 1格式的月份值
		// '##RD lFMonth yyyyMM格式的月份值
		// mod --> %
		return (lLMonth / 12) * 100 + (lLMonth % 12) + 1;
	}

	public static Integer FMonthToLMonth(Integer lFMonth) {
		// '##BD 将yyyyMM转化为yyyy * 12 + MM - 1
		// '##PD lFMonth yyyyMM格式的月份值
		// '##RD yyyy * 12 + MM - 1格式的月份值
		// mod --> %
		return (lFMonth / 100) * 12 + (lFMonth % 100) - 1;
	}

	/**
	 * util.Date型日期转化sql.Date(年月日)型日期
	 * 
	 * @throws ParseException
	 * 
	 * @Param: p_utilDate util.Date型日期
	 * @Return: java.sql.Date sql.Date型日期
	 */
	// public static java.sql.Date toSqlDateFromUtilDate(java.util.Date
	// utilDate) {
	// java.sql.Date sqlDate = null;
	// if (utilDate != null) {
	// sqlDate = new java.sql.Date(utilDate.getTime());
	// }
	// return sqlDate;
	// }
	//
	// /**
	// * sql.Date型日期转化util.Date型日期
	// *
	// * @Param: sqlDate sql.Date型日期
	// * @Return: java.util.Date util.Date型日期
	// */
	// public static java.util.Date toUtilDateFromSqlDate(java.sql.Date sqlDate)
	// {
	// java.util.Date utilDate = null;
	// if (sqlDate != null) {
	// utilDate = new java.util.Date(sqlDate.getTime());
	// }
	// return utilDate;
	// }
	// Function SQLDateTimeFormStr(ByVal Str As String) As String
	// Select Case g_EnvDBType
	// Case BKGNTCore.edbtDB2
	// SQLDateTimeFormStr = "TIMESTAMP('" & Str & "')"
	// Case BKGNTCore.edbtOracle
	// Select Case Len(Str)
	// Case 26 'yyyy-mm-dd HH:MM:SS.ffffff
	// SQLDateTimeFormStr = "TO_DATE('" & Format(Str, "YYYY-MM-DD HH:mm:ss") &
	// "', 'YYYY-MM-DD HH24:MI:SS"".000000""')"
	// Case 19 'yyyy-mm-dd HH:MM:SS
	// SQLDateTimeFormStr = "TO_DATE('" & Format(Str, "YYYY-MM-DD HH:mm:ss") &
	// "', 'YYYY-MM-DD HH24:MI:SS')"
	// Case Is < 19
	// Str = Format(Str, "yyyy-mm-dd HH:MM:SS")
	// SQLDateTimeFormStr = "TO_DATE('" & Str & "', 'YYYY-MM-DD HH24:MI:SS')"
	// Case Else
	// Err.Raise 13, "SQLDateTimeFormStr(""" & Str & """)"
	// End Select
	// Case BKGNTCore.edbtSQLServer
	// Dim tDate As Date
	// tDate = GetDateByFormula(Str, "yyyy-MM-dd-HH.mm.ss.FFFFFF")
	// SQLDateTimeFormStr = "convert(datetime,'" & Format(CStr(tDate),
	// "yyyy-MM-dd HH:mm:ss") & "',21)"
	// End Select
	// End Function
	public static java.util.Date getDate(String value) throws Throwable {
		java.util.Date d = null;
		// try {
		if (value == null) {
			return d;
		}
		if (value.startsWith("'") && value.endsWith("'") && value.length() > 1) {
			value = value.substring(1, value.length() - 2);
		}
		if (value.length() > 10) {
			if (value.length() < 17) {
				value = value.substring(0, 10);
				d = getDateParse(value, "yyyy-MM-dd");
			} else {
				if (value.indexOf(" ") == 3)
					d = getDateParse(value, "EEE MMM dd HH:mm:ss zzz yyyy");
				else
					d = getDateParse(value, "yyyy-MM-dd HH:mm:ss");
			}
		} else if ((value.length() == 7) && (value.lastIndexOf("-") == 4)) {
			d = getDateParse(value, "yyyyy-MM");
		} else if (!"null".equalsIgnoreCase(value.trim()))
			d = getDateParse(value, "yyyy-MM-dd");
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		return d;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(format("2009-12-04", "yy-dd-MM"));
			// DebugUtil.debug(date2.toString());
			// DebugUtil.debug("Sun Jan 04 00:00:00 CST 2009".indexOf(" "));
			// DebugUtil.debug(">date:"
			// + new SimpleDateFormat().getCalendar().
			// .parse("Tuesday, April 12, 2009 AD"));
			// if (1 == 1)
			// return;
			// //
			// String sDate = "2007-01-01 12:40:30";
			// DebugUtil.debug(getDate(aa, "yyyy-MM-dd"));
			// java.util.Date date = getDate(sDate, "yyyy-MM-dd HH:mm:ss");
			// DebugUtil.debug("--" + getDateFormat("yyyy-MM-dd").format(date));
			// String timeStr = "2007-09-29-00.00.00";
			// String dateStyle = "yyyy-MM-dd-HH.MM.SS";
			// DebugUtil.debug(getDateString(timeStr, dateStyle));
			// timeStr = "2007-09-29";
			// dateStyle = "yyyy-MM-dd";
			// DebugUtil.debug(getDateString(timeStr, dateStyle));
			// DebugUtil.debug(getCurrDate());
			// DebugUtil.debug(getCurrTime());
			// DebugUtil.debug(getCurrTimeStr("yyyy-MM-dd HH.mm.ss"));
			// //
			// DebugUtil.debug(format(date, "yyyy-MM-dd HH:mm:ss"));
			// DebugUtil.debug(format(date, "yyyy-MM-dd"));
			// DebugUtil.debug(format(date, "yy-MM-dd"));
			// DebugUtil.debug(format(date, "yyyyMMdd"));
			// DebugUtil.debug(format(date, "yyMMdd"));
			// DebugUtil.debug(format(date, "MM-DD"));
			// DebugUtil.debug(format(date, "MMDD"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	//
	// public static Date getSqlDate(String value) {
	// return toSqlDateFromUtilDate(getDate(value));
	// }

	/**
	 * 获取2个时间中间隔的月数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getIntervalMonths(java.sql.Date startDate, java.sql.Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int startMonth = start.get(Calendar.MONTH);
		int startYear = start.get(Calendar.YEAR);
		int endMonth = end.get(Calendar.MONTH);
		int endYear = end.get(Calendar.YEAR);
		int interval = (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
		return interval;
	}

	/**
	 * 暂时先放在这~不知道放哪
	 * 
	 * @param nCurMonth
	 * @param nCycle
	 * @return
	 */
	public static int getPreviousMonth(int nCurMonth, int nCycle) {
		// '##BD 取得前一月份
		// '##PD lCurMonth 当前月份
		// '##PD lCycle 工资类别的发放周期
		// '##RD 前一月份

		return LMonthToFMonth(FMonthToLMonth(nCurMonth) - nCycle);
	}

	public static int getNextMonth(int nCurMonth, int nCycle) {
		// '##BD 取得下一月份
		// '##PD lCurMonth 当前月份
		// '##PD lCycle 工资类别的发放周期
		// '##RD 下一月份
		return LMonthToFMonth(FMonthToLMonth(nCurMonth) + nCycle);
	}

	public static java.util.Date dateAdd(String interval, int num, java.util.Date date) {

		Calendar start = Calendar.getInstance();
		start.setTime(date);
		int field = 0;
		if (interval.equalsIgnoreCase("d") || interval.equalsIgnoreCase("dd"))
			field = Calendar.DATE;
		else if (interval.equalsIgnoreCase("s"))
			field = Calendar.SECOND;
		else if (interval.equalsIgnoreCase("n"))
			field = Calendar.MINUTE;
		else if (interval.equalsIgnoreCase("h"))
			field = Calendar.HOUR;
		else if (interval.equalsIgnoreCase("w"))
			field = Calendar.DAY_OF_WEEK;
		else if (interval.equalsIgnoreCase("ww"))
			field = Calendar.WEEK_OF_YEAR;
		else if (interval.equalsIgnoreCase("y"))
			field = Calendar.DAY_OF_YEAR;
		else if (interval.equalsIgnoreCase("m") || interval.equalsIgnoreCase("mm"))
			field = Calendar.MONTH;
		else if (interval.equalsIgnoreCase("yyyy"))
			field = Calendar.YEAR;
		else if (interval.equalsIgnoreCase("q")) {
			field = Calendar.MONTH;
			num = num * 3;
		} else {
			// DebugUtil.error("dateAdd" + interval + "not impl");
		}
		start.add(field, num);
		return start.getTime();
	}

	/**
	 * 根据年月日得到Date对象
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static java.util.Date getDate(int year, int month, int date) {
		Calendar cale = Calendar.getInstance();
		cale.clear();
		cale.set(year, month - 1, date); // 不知道JDK怎样想的,月份要减1
		return cale.getTime();
	}

	public static java.util.Date getDate(int year, int month, int date, int hour, int min, int sec) {

		return getDate(getDate(year, month, date), hour, min, sec);
	}

	public static java.util.Date getDate(java.util.Date d, int hour, int min, int sec) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		return c.getTime();
	}

	/**
	 * 取某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static java.util.Date getLastDateInMonth(int year, int month) {
		Calendar cale = Calendar.getInstance();
		cale.clear();
		cale.set(Calendar.YEAR, year);
		cale.set(Calendar.MONTH, month - 1);
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cale.getTime();
	}

	/**
	 * 取某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static java.util.Date getFirstDateInMonth(int year, int month) {
		Calendar cale = Calendar.getInstance();
		cale.clear();
		cale.set(Calendar.YEAR, year);
		cale.set(Calendar.MONTH, month - 1);
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cale.getTime();
	}

	private static long millisecondsPerDay = 24 * 60 * 60 * 1000;

	public static java.util.Date dateAdd(java.util.Date date, int days) {
		java.util.Date result = date;
		long time = date.getTime();
		time += millisecondsPerDay * days;
		result.setTime(time);
		return result;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = (Date) sdf.parse(sdf.format(smdate));
		bdate = (Date) sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}