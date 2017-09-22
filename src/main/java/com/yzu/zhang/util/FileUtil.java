package com.yzu.zhang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 * @author yzuzhang
 * @date 2017年8月8日
 */
public class FileUtil {
	
	private FileUtil() {
		
	}
	
	public static List<String> getListFromResource(String fileName){
		return getListFromResource(fileName, Constants.UTF_8);
	}
	
	/**
	 * 获取文件内容List
	 * @param fileName    文件名称
	 * @param charsetName 字符编码
	 */
	public static List<String> getListFromResource(String fileName, String charsetName){
		List<String> list;
		File file = new File(fileName);
		if( file.exists() && file.isFile() ){
			// 使用绝对路径进行读取
			list = readLinesFromPath(fileName, charsetName);
			return list;
		}
		
		URL resourceUrl = ClassLoader.getSystemClassLoader().getResource(fileName);
		if( null == resourceUrl ){
			resourceUrl = FileUtil.class.getClassLoader().getResource(fileName);
		}
		
		if( resourceUrl != null ){
			String filePath = resourceUrl.getPath();
			System.out.println(fileName+" : filePath---->"+ filePath);
			list = readLinesFromPath(filePath, charsetName);
		} else {
			list = readLinesFromStream(fileName, charsetName);
		}
		
		return list;
	}
	
	/**
	 * 通过输入流的方式进行读取
	 * @param fileName
	 * @param charsetName
	 */
	public static List<String> readLinesFromStream(String fileName, String charsetName) {
		InputStream in = null;
		BufferedReader br = null;
		InputStreamReader isReader = null;
		List<String> lines = new ArrayList<String>();
		
		try {
			in = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
			
			isReader = new InputStreamReader(in, charsetName);
			br = new BufferedReader(isReader);
			
			String line = null;
			while ((line=br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.out.println(fileName+": ERROR----->"+e.getMessage());
		} finally {
			CloseUtil.close(br, isReader, in);
		}
		
		return lines;
	}
	
	/**
	 * @param filePath    文件路径
	 * @param charsetName 字符编码
	 */
	public static List<String> readLinesFromPath(String filePath, String charsetName) {
		InputStream in = null;
		BufferedReader br = null;
		InputStreamReader isReader = null;
		List<String> lines = new ArrayList<String>();
		
		try {
			in = new FileInputStream(filePath);
			isReader = new InputStreamReader(in, charsetName);
			br = new BufferedReader(isReader);
			
			String line = null;
			while ((line=br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.out.println(filePath+": ERROR----->"+e.getMessage());
		} finally {
			CloseUtil.close(br, isReader, in);
		}
		
		return lines;
	}
	
	public static List<String> getListFromPath(String fileName, String charsetName){
		List<String> list = null;
		String filePath = MyUtil.getUserDir() + fileName;
		
		File file = new File(filePath);
		if( file.exists() && file.isFile() ){
			list = readLinesFromPath(filePath, charsetName);
			return list;
		}
		
		return list;
	}
	
	/**
	 * 向文件里写入操作
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @param content  写入内容
	 * @param append   true:尾部增加  false:覆盖原文件
	 */
	public static void write(String directory, String fileName, String content, boolean append){
	    try {
	    	createMkdirAndFile(directory, fileName);
	    	File file = new File(directory, fileName);
	    	writeToFile(file, content, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String fileName, String content, boolean append) {
		File file = new File(fileName);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		createNewFile(file);
		// 写入操作
    	writeToFile(file, content, append);
    }
	
	public static void writeToFile(File file, String content, boolean append) {
    	FileWriter writer = null;
    	try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(file, append);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	CloseUtil.close(writer);
        }
    }
	
	public static void createMkdirAndFile(String directory, String filename) throws IOException {
		File dir = new File(directory);
		forceMkdir(dir);
		File file = new File(directory, filename);
		createNewFile(file);
	}
	
	public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message =
                    "File "
                        + directory
                        + " exists and is "
                        + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                if (!directory.isDirectory())
                {
                    String message ="Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }
	
	public static void createNewFile(File file) {
		if( file.isFile() && !file.exists() ){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
