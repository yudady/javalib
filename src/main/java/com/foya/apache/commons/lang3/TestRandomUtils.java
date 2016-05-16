package com.foya.apache.commons.lang3;

import org.apache.commons.lang3.RandomUtils;

public class TestRandomUtils {

	public static void main(String[] args) {
		for(int i = 0 ; i < 10 ; i++){

			System.out.println(RandomUtils.nextInt(10, 20));
			System.out.println(RandomUtils.nextInt(0, Integer.MAX_VALUE));

		}

	}

}
