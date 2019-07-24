package com.mes.common;

import java.sql.Connection;

//连接的上下文工具,提供全局使用,保证线程变量的安�?
public class ConnectionContext {//想成�?个存放线程的容器//做成单例模式，保证线程安�?
	
	//饿汉�?
	
	private ConnectionContext() {}
	
	private static ConnectionContext instance=new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}
	
	//准备本地线程变量
	private ThreadLocal<Connection> connectionThreadLocal=//
			new ThreadLocal<Connection>();
	
	public void bind(Connection conn) {
		connectionThreadLocal.set(conn);
	}
	
	public Connection get() {
		return connectionThreadLocal.get();
	}
	
	public void remove() {
		connectionThreadLocal.remove();
	}
	
}
