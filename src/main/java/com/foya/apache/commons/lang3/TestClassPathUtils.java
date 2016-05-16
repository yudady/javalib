package com.foya.apache.commons.lang3;

import org.apache.commons.lang3.ClassPathUtils;

public class TestClassPathUtils {

	public static void main(String[] args) {
		final String actual = ClassPathUtils.toFullyQualifiedPath(TestSystemUtils.class, "Test.properties");
		System.out.println(actual);
	}

}
