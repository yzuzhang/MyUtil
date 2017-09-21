package com.jd.ptest.entity;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机获取参数化文件数据处理器
 * @author sqzhanglei7
 * @date 2017年8月8日
 */
public class RandomHandler extends AbstractHandler{
	
	public RandomHandler(String fileName, String separator){
		this.fileName = fileName;
		this.separator= separator;
	}
	
	public RandomHandler(String fileName, String separator, String charsetName){
		this.fileName = fileName;
		this.separator= separator;
		this.charsetName = charsetName;
	}
	
	@Override
	public LineContext next(){
		int index = ThreadLocalRandom.current().nextInt(this.length);
        return this.list.get(index);
	}
	
}
