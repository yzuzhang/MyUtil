package com.jd.ptest.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构造多个参数化文件处理器
 * @author sqzhanglei7
 * @date 2017年8月9日
 */
public class MultiFileHandler extends AbstractHandler{
	
	private final int ZERO = 0;
	private Map<String, Boolean> LoopMap = new HashMap<String, Boolean>();
	private Map<String, AtomicInteger> AtomicMap = new HashMap<String, AtomicInteger>();
	private Map<String, List<LineContext>> ContextMap = new HashMap<String, List<LineContext>>();
	
	public MultiFileHandler() {
		
	}
	
	/**
	 * 默认UTF-8, 顺序循环读取参数
	 * @param fileName  文件名
	 * @param separator 分隔符
	 */
	public void initFile(String fileName, String separator){
		this.initFile(fileName, separator, UTF_8, true);
	}
	
	/**
	 * 默认按照顺序循环读取参数
	 * @param fileName	      文件名
	 * @param separator   分隔符
	 * @param charsetName 字符编码
	 */
	public void initFile(String fileName, String separator, String charsetName){
		this.initFile(fileName, separator, charsetName, true);
	}
	
	/**
	 * @param fileName		文件名
	 * @param separator		分隔符
	 * @param charsetName 	字符集
	 * @param isLoop        是否顺序读取参数
	 */
	public void initFile(String fileName, String separator, String charsetName, boolean isLoop){
		this.fileName = fileName;
		this.separator= separator;
		this.charsetName = charsetName;
		//
		LoopMap.put(fileName, isLoop);
		AtomicMap.put(fileName, new AtomicInteger(ZERO));
	}
	
	@Override
	public void initVarNames(String... varNames){
		super.initVarNames(varNames);
		ContextMap.put(this.fileName, this.list);
	}
	
	/**
	 * @param fileName 文件名
	 * @return  所有行参数
	 */
	public List<LineContext> getContextList(String fileName) {
		return ContextMap.get(fileName);
	}
	
	/**
	 * @param fileName 参数化文件名
	 * @return 顺序获取一行参数数据
	 */
	public LineContext next(String fileName) {
		return next(fileName, true);
	}
	
	@Override
	public LineContext next() {
		LineContext context = new LineContext();
		for(String fileName : LoopMap.keySet()){
			boolean isLoop = LoopMap.get(fileName);
			LineContext line = next(fileName, isLoop);
			context.putAll(line);
		}
		return context;
	}
	
	/**
	 * 获取指定文件的一行测试数据
	 * @param fileName 参数化文件名
	 * @param isLoop   是否循环获取 
	 * @return 获取一行参数数据
	 */
	public LineContext next(String fileName, boolean isLoop) {
		List<LineContext> list = ContextMap.get(fileName);
		if( isLoop ){
			AtomicInteger atomic = AtomicMap.get(fileName);
			return loop(list, atomic);
		}
		
		return random(list);
	}
	
	private LineContext loop(final List<LineContext> list, final AtomicInteger atomic){
		int size = list.size();
		atomic.compareAndSet(size, ZERO);
		int index = atomic.getAndIncrement();
		if( index >= size ){
			index = index % size;
			atomic.set(index+1);
		}
		return list.get(index);
	}
	
	private LineContext random(final List<LineContext> list){
		int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
	}

}
