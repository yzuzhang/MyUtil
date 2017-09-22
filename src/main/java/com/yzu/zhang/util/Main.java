package com.yzu.zhang.util;

import com.yzu.zhang.entity.*;

/**
 * 工具使用示例
 * @author yzuzhang
 * @date 2017年9月13日
 */
public class Main {
	
	public static void main(String[] args) {
		//一次读取多个文件
		//multiFileHandlerDemo();
		
		//顺序读取参数化文件
		loopHandlerDemo();
	}
	
	/**
	 * 顺序读取参数化文件
	 */
	public static void loopHandlerDemo() {
		AbstractHandler handler = new LoopHandler("user_pins.txt", ",");
		//handler = new LoopHandler("user_pins.txt", ",", "UTF-8");//指定编码格式
		handler.initVarNames("pin");
		//handler.initVarNames("pin", "age", "gender", "marriage");
		System.out.println("参数总行数----->"+ handler.getLength());
		
		//获取一行参数
		LineContext context = handler.next();
		String pin = context.getString("pin", "zhang");
		int age = context.getIntValue("age", 35);
		String gender = context.getString("gender");
		
		System.out.println(pin+", age: "+age+ ", gender: "+gender);
	}
	
	/**
	 * /随机获取参数化文件里一行数据
	 */
	public static void randomHandlerDemo() {
		AbstractHandler handler = new RandomHandler("user_profile.txt", ",", "UTF-8");
		handler.initVarNames("pin", "age", "gender", "marriage");
		
		//随机获取一行参数
		LineContext context = handler.next();
		String pin = context.getString("pin", "zhang");
		int age = context.getIntValue("age", 35);
		String gender = context.getString("gender");
		
		System.out.println(pin+", age: "+age+ ", gender: "+gender);
	}
	
	/**
	 * 一次读取多个文件
	 */
	public static void multiFileHandlerDemo(){
		MultiFileHandler handler = new MultiFileHandler();
		//handler.initFile("user_pins.txt", ",");
		handler.initFile("user_pins.txt", ",", "UTF-8", false);//逗号分隔,随机读取
		handler.initVarNames("pin");
		
		handler.initFile("user_profile.txt", ",", "UTF-8", true);//逗号分隔,顺序读取
		handler.initVarNames("age", "gender", "marriage");
		
		//获取一行参数
		LineContext context = handler.next();
		String pin = context.getString("pin", "zhang");
		int age = context.getIntValue("age", 35);
		String gender = context.getString("gender");
		
		System.out.println(pin+", age: "+age+ ", gender: "+gender);
	}
	
}
