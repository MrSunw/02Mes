package com.mes.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class MyQueryRunner extends QueryRunner{

	//批量数据操作
	@Override
	public int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {
		int[] result=super.batch(conn, sql, params);
		return super.batch(conn, sql, params);
	}
	//查询操作，传参数的方法
	@Override
	public <T> T query(Connection conn, String sql, Object param, ResultSetHandler<T> rsh) throws SQLException {
		T result=super.query(conn, sql, param, rsh);
		return result;
	}
	//查询不带参数的方法
	@Override
	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh) throws SQLException {
		T result=super.query(conn, sql,rsh);
		return result;
	}
	@Override
	public int update(Connection conn, String sql, Object param) throws SQLException {
		int result=super.update(conn, sql, param);
		return result;
	}
	@Override
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		int result=super.update(conn, sql, params);
		return result;
	}
	
	@Override
	public int update(Connection conn, String sql) throws SQLException {
		int result=super.update(conn, sql);
		return result;
	}
}
