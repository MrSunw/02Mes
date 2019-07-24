package com.mes.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;

import com.mes.annotaion.MyTransaction;
import com.mes.base.Dao;
import com.mes.common.ConnectionContext;
import com.mes.utils.JdbcUtil;

public class DaoFactory {

	private Object target;
	private DaoFactory() {
		throw new RuntimeException("不可以反射");
	}
	private DaoFactory(int init) {}
	private static DaoFactory instance=new DaoFactory(1);
	
	public static DaoFactory getInstance() {
		if(instance==null) {
			synchronized(DaoFactory.class) {
				if(instance==null) {
					instance=new DaoFactory();
				}
			}
		}
		return instance;
	}
	
	//产生一个dao userDao--userDaoImpl
	public Dao getDaoByName(String daoName) {
		//获取这个dao-使用properties
		Properties prop=new Properties();
		InputStream in=DaoFactory.class.getClassLoader()//
				.getResourceAsStream("dao.properties");
//		InputStream in=new FileInputStream("dao.properties");
		try {
			//加载输入流，读取文件内容
			prop.load(in);
			//classPath=com.mes.dao.impl.UserDaoImpl
			String classPath=prop.getProperty(daoName);
			if(classPath==null) {
				throw new RuntimeException("没有文件");
			}
			//通过反射的方式将类的全路径名，实例化出一个类对象
			Dao targetDao=(Dao) Class.forName(classPath).newInstance();
			this.target=targetDao;
			//事务处理-采用的是动态代理技术
			targetDao=(Dao) getTransactionDao(targetDao);
			return targetDao;
		} catch (IOException e) {
			throw new RuntimeException("加载properties出问题");
		} catch (Exception e) {
			throw new RuntimeException("初始化dao出问题");
		}
	}
	private Object getTransactionDao(Object target) {
		
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),//
				target.getClass().getInterfaces(), //
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object result = null;
						//若方法有事务注解，则执行开启事务
						MyTransaction ann = method.getDeclaredAnnotation(MyTransaction.class);
						Connection conn=null;
						//判断注解不为空。取
                        if(null!=ann) {
                        //启动事务 以下实现真正的开启事务和关闭事务
                        //conn.rollback  conn.commit
                        String value=ann.value();
                        if(value.equals("openTransaction")) {
                        	try {
								//开启一个连接
                        		conn=JdbcUtil.getConnection();
                        		//开启事务
                        		conn.setAutoCommit(false); //begin
                        		//connection上下文绑定连接
                        		ConnectionContext.getInstance().bind(conn);
                        		result=method.invoke(target, args);// userDao.add update delete
                        		conn.commit();
							} catch (Exception e) {
								conn.rollback();
								throw new RuntimeException(e + "sql执行出问题，执行数据回滚");
							}finally {
								// 释放资源
								// 接触connection上下文的绑定状态
								ConnectionContext.getInstance().remove();
								//将connection放回连接池
								JdbcUtil.close(conn, null, null);
								return result;
							}
                        }
                        
                      }						
                      //不需要开启事务的
						try {
							// 开启一个连接
							conn = JdbcUtil.getConnection();
							// connection上下文绑定连接   statement-sql
							ConnectionContext.getInstance().bind(conn);
							result = method.invoke(target, args);// userDao.add update delete
						} catch (Exception e) {
							throw new RuntimeException(e + "sql执行出问题，执行数据回滚");
						} finally {
							ConnectionContext.getInstance().remove();
							JdbcUtil.close(conn, null, null);
							return result;
						}
				
			
						

					}
				});
		
		
	}
}
