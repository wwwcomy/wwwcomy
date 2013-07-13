package com.iteye.wwwcomy;

import java.util.Date;
import java.util.Calendar;

/**
 * Java解惑61之日期游戏
 * 
 * Calander和Time的月份都是0-11，Date的getDay是getDayOfWeek，周日算0，周六算6
 * 
 * @author Liuxn
 * 
 */
public class DatingGame {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(1999, 5, 6);
		// cal.set(2013, 4, 26);
		System.out.print(cal.get(Calendar.YEAR) + " ");
		// System.out.print(cal.get(Calendar.DATE) + " ");
		Date d = (Date) cal.getTime();
		System.out.println(d.getDay());
	}

}
