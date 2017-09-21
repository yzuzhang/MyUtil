package com.jd.ptest.entity;

import java.util.ArrayList;
import java.util.List;

import com.jd.ptest.entity.LineContext;
import com.jd.ptest.util.FileUtil;

/**
 * 抽象处理类
 * @author yzuzhang
 * @date 2017年8月8日
 */
public abstract class AbstractHandler {
	
	protected int length = 0;//参数文件行数
	protected List<LineContext> list;
	protected final String UTF_8 = "UTF-8";
	
	protected String fileName;//文件名
	protected String separator;//分隔符
	protected String charsetName = UTF_8;//字符
	
	protected AbstractHandler(){
		
	}
	
	/**
	 * @param varNames 初始化变量
	 */
	public void initVarNames(String... varNames){
		if(null == fileName){
			throw new NullPointerException("参数化文件没有进行初始化");
		}
		if(null==varNames || varNames.length==0){
			throw new NullPointerException("调用方法初始化参考: initVarNames(\"a\",\"b\")");
		}
		
		List<String> lineList = FileUtil.getListFromResource(fileName, charsetName);
		if( lineList==null || lineList.size()==0 ){
			throw new RuntimeException(fileName + ": 文件内容为空！");
		}
		
		length = lineList.size();
		list = new ArrayList<LineContext>(length);
		
		initContextList(lineList, varNames);
	}
	
	private void initContextList(List<String> lineList, String... varNames) {
		String[] values;
		LineContext context = null;
		int varLength = varNames.length;
		
		for( String line : lineList ) {
			values = line.split(separator);
			if( varLength == values.length ){
				context = new LineContext();
				for (int i = 0; i < varLength; i++) {
					context.put(varNames[i], values[i]);
				}
				list.add(context);
			} else {
				System.out.println("参数化数据格式不准确: "+line);
			}
		}
	}
	
	public void clear() {
        this.list.clear();
        this.length = 0;
    }
	
	public int getLength(){
		return this.length;
	}
	
	/**
	 * @return 获取下一行参数化数据
	 */
	public abstract LineContext next();
	
}
