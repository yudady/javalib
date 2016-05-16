package com.foya.apache.commons.lang3;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.Range;
import org.junit.Test;

public class TestRange {
	@Test
	public void test01() {
		//-------------------
		System.out.println("[Integer]");
		Range<Integer> rb = Range.between(-10, 20);
		System.out.println(rb.contains(-10));
		System.out.println(rb.contains(-11));

		//-------------------
		System.out.println("[Character]");
		Range<Character> chars = Range.between('a', 'z');

		System.out.println(chars.contains('b'));
		System.out.println(chars.contains('B'));

		//-------------------
		System.out.println("[Date]");
		Date datea = new GregorianCalendar(2010, 6, 1, 13, 45, 32).getTime();
		Date dateb = new GregorianCalendar(2016, 6, 1, 13, 45, 32).getTime();
		Date datec = new GregorianCalendar(2004, 6, 1, 13, 45, 32).getTime();
		Date dated = new GregorianCalendar(2014, 6, 1, 13, 45, 32).getTime();

		Range<Date> dates = Range.between(datea, dateb);
		System.out.println(dates.contains(datec));
		System.out.println(dates.contains(dated));

	}
}
