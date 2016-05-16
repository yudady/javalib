package com.foya.apache.commons.lang3;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class TestDateUtils {
	public static void main(String[] args) {
		Date datea = new GregorianCalendar(2004, 6, 1, 13, 45, 32).getTime();
		System.out.println(DateFormatUtils.format(datea, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("      ");

		System.out.println(DateFormatUtils.format(DateUtils.addDays(datea, 10), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.addDays(datea, -10), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.addWeeks(datea, 1), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("      ");
		System.out.println(DateFormatUtils.format(datea, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("      ");
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.SECOND), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.MINUTE), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.HOUR), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.DATE), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.MONTH), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(DateUtils.ceiling(datea, Calendar.YEAR), "yyyy-MM-dd HH:mm:ss"));

	}
}
