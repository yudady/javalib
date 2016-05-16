package com.foya.apache.commons.lang3;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

public class TestSystemUtils {

	@Test
	public void test01(){
		System.out.println(SystemUtils.getJavaIoTmpDir());
		System.out.println(SystemUtils.IS_OS_WINDOWS);
		System.out.println(SystemUtils.USER_DIR);
		System.out.println(SystemUtils.USER_HOME);
		System.out.println(SystemUtils.PATH_SEPARATOR);
	}
}
