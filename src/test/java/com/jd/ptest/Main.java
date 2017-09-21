package com.jd.ptest;

import com.jd.ptest.entity.AbstractHandler;
import com.jd.ptest.entity.LineContext;
import com.jd.ptest.entity.LoopHandler;
import com.jd.ptest.entity.MultiFileHandler;
import com.jd.ptest.entity.RandomHandler;
import com.jd.ptest.util.MyUtil;

public class Main {
	
	private static int length = 2000 * 10000;
	
 	public static void main(String[] args) {
 		//txtLoopEntity();
 		//scvLoopEntity();
 		multiFileHandler();
	}
 	
 	public static void scvLoopEntity() {
 		RandomHandler entity = new RandomHandler("test.csv", ",", "UTF-8");
		entity.initVarNames("name", "password");
		Main.runLoop(entity);
	}
 	
 	public static void txtLoopEntity() {
 		LoopHandler entity = new LoopHandler("sdk_customer_params.txt", ",", "UTF-8");
		entity.initVarNames("userPin","token","appId","venderId","groupId","venderName","entry");
		Main.runLoop(entity);
	}
 	
 	public static void multiFileHandler() {
 		String testFile = "user_pins100W.txt";
 		String sdkFile = "sdk_customer_params.txt";
 		MultiFileHandler handler = new MultiFileHandler();
 		handler.initFile(testFile, ",", "UTF-8", false);
 		handler.initVarNames("pin");
 		
 		handler.initFile(sdkFile, ",", "UTF-8", false);
 		handler.initVarNames("userPin","token","appId","venderId","groupId","venderName","entry");
 		
 		for (int i = 0; i < 1; i++) {
			LineContext context = handler.next();
 			System.out.println(context);
		}
 		
 		runMultiHandler(handler, testFile, sdkFile);
 	}
 	
 	protected static void runMultiHandler(final MultiFileHandler handler,final String testFile,final String sdkFile) {
		Runnable runnable = new Runnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				for (int i = 0; i < Main.length; i++) {
					//LineContext context = handler.next();
					LineContext test = handler.next(testFile, true);
					LineContext sdk = handler.next(sdkFile, true);
				}
			}
		};
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		Thread thread4 = new Thread(runnable);
		Thread thread5 = new Thread(runnable);
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		System.out.println(MyUtil.getNow()+" start....");
		long start = System.currentTimeMillis();
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
			long ts = System.currentTimeMillis() - start;
			int tps = 5*1000*(int) (length/ts);
			System.out.println("处理耗时---->"+ ts +"ms, TPS="+tps+"/s");
			System.out.println(MyUtil.getNow()+" end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
 	
 	protected static void runLoop(AbstractHandler handler) {
 		
		MyRunnable runnable = new MyRunnable(handler);
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		Thread thread4 = new Thread(runnable);
		Thread thread5 = new Thread(runnable);
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		System.out.println(MyUtil.getNow()+" start....");
		long start = System.currentTimeMillis();
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
			
			long ts = System.currentTimeMillis() - start;
			int tps = 5*1000*(int) (length/ts);
			System.out.println("处理耗时---->"+ ts +"ms, TPS="+tps+"/s");
			System.out.println(MyUtil.getNow()+" end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
 	private static class MyRunnable implements Runnable{
		private AbstractHandler handler;
		
		public MyRunnable(AbstractHandler handler){
			this.handler = handler;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < Main.length; i++) {
				@SuppressWarnings("unused")
				LineContext context = handler.next();
			}
		}
	}
}
