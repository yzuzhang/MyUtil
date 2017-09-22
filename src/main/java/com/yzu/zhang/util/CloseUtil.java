package com.yzu.zhang.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class CloseUtil {
	
	/**
	 * 关闭资源
	 * @param closeables
	 */
	public static void close(Closeable... closeables){
		if( closeables != null )
		{
			for(Closeable closeable : closeables)
			{
				if(closeable != null){
					try {
						closeable.close();
					} catch (IOException e) {
						//ignore
					}
				}
			}
		}
	}
	
	/**
	 * 关闭soccket
	 * @param socket
	 */
	public static void closeSocket(Socket socket){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
