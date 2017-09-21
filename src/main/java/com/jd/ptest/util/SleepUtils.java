package com.jd.ptest.util;

import java.util.Random;

public class SleepUtils {
	
	public static Random random = new Random();
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 随机休眠
	 */
	public static void sleepRandom(int n) {
		int millis = random.nextInt(n);
		SleepUtils.sleep(millis);
	}
}
