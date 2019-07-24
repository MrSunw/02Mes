package com.mes.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//使用连接池，采用的c3p0连接数据库
public class JdbcUtil {

	//使用c3p0连接
	private static DataSource ds=new ComboPooledDataSource();
	public static DataSource getDataSource() {
		return ds;
	}
	//自定义一个获取连接池中的连接方法
		public static 	Connection getConnection()throws SQLException{
			return ds.getConnection();
		}
		
		public static void close(Connection conn,Statement state,ResultSet rs) {
			if(rs!=null) {
				try {
					rs.close();//oop--conn.close()----pool.add(conn)
				} catch (SQLException e) {
					throw new RuntimeException("关闭返回结果集失败！！！");
				}finally {
					rs=null;
				}
			}
			
			if(state!=null) {
				try {
					state.close();//pool.add(conn)
				} catch (SQLException e) {
					throw new RuntimeException("关闭statement失败！！�??");
				}finally {
					state=null;
				}
			}
			
			
			if(conn!=null) {//oop
				try {
					conn.close();//pool.add(conn)
				} catch (SQLException e) {
					throw new RuntimeException("根本就没有要关闭的连接！！！");
				}
			}
			
		}

}
