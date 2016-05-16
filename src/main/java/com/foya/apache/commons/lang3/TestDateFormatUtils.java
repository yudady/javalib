package com.foya.apache.commons.lang3;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class TestDateFormatUtils {

	public static void main(String[] args) {
		System.out.println(DateFormatUtils.format(new Date(), "yyyy/MM-dd"));
		System.out.println(DateFormatUtils.format(new Date(), "yyyy/MM/dd hh:mm:ss"));

	}

}
