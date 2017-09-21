package com.jd.ptest.entity;

import java.util.HashMap;

/**
 * 参数化行数据封装类
 * @author yzuzhang
 * @date 2017年8月8日
 */
public class LineContext extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public LineContext() {
		super();
	}
	
	public LineContext put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public String getString(String key){
		return (String) get(key);
	}
	
	public String getString(String key, String defaultValue){
		String value = getString(key);
		return value==null ? defaultValue : value;
	}
	
	public int getIntValue(String key){
		return getIntValue(key, 0);
	}
	
	public int getIntValue(String key, int defaultValue){
		String value = getString(key);
		return value==null ? defaultValue : Integer.parseInt(value);
	}
	
	public long getLongValue(String key) {
		return getLongValue(key, 0);
	}
	
	public long getLongValue(String key, long defaultValue) {
		String value = getString(key);
		return value==null ? defaultValue : Long.parseLong(value);
	}
	
}
