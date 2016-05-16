package com.foya.apache.commons.lang3;

import org.apache.commons.lang3.ClassUtils;

public class TestClassUtils {

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(ClassUtils.getShortClassName(TestSystemUtils.class));
		System.out.println(ClassUtils.getPackageName(TestSystemUtils.class));
		Class<?> class1 = ClassUtils.getClass("com.foya.lang3.TestSystemUtils");
		System.out.println(class1.getName());
	}

}
