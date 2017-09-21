package com.jd.ptest.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 循环获取参数化文件处理器
 * @author sqzhanglei7
 * @date 2017年8月8日
 */
public class LoopHandler extends AbstractHandler {
	
	private final int INDEX_ONE  = 1;
	private final int INDEX_ZERO = 0;
	private final ReentrantLock lock = new ReentrantLock();
	private final AtomicInteger ATOMIC = new AtomicInteger(INDEX_ZERO);
	
	public LoopHandler(String fileName, String separator){
		this.fileName = fileName;
		this.separator= separator;
	}
	
	public LoopHandler(String fileName, String separator, String charsetName){
		this.fileName = fileName;
		this.separator= separator;
		this.charsetName = charsetName;
	}
	
	@Override
	public LineContext next(){
		LineContext context = null;
		try {
            this.lock.lock();
            this.ATOMIC.compareAndSet(this.length, INDEX_ZERO);
            context = list.get(this.ATOMIC.getAndIncrement());
        } finally {
            this.lock.unlock();
        }
		
		return context;
	}
	
	@Deprecated
	protected LineContext nextBakup(){
		ATOMIC.compareAndSet(length, INDEX_ZERO);
		int index = ATOMIC.getAndIncrement();
		if( index >= length ){
			index = index % length;
			ATOMIC.set(index+1);
		}
		return list.get(index);
	}
	
	@Deprecated
	protected LineContext nextLine(){
		try {
			int index = ATOMIC.getAndIncrement();
			return this.list.get(index % this.length);
		} catch (Exception e) {
			ATOMIC.set(INDEX_ONE);
		}
		return this.list.get(INDEX_ZERO);
	}
	
}
