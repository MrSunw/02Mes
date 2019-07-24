package com.mes.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mes.common.ConnectionContext;
import com.mes.utils.MyQueryRunner;
import com.mes.utils.ReflectUtil;

public class BaseDao<T> implements Dao<T>  {

	
	private Class<T> clazz;
	protected MyQueryRunner queryRunner=new MyQueryRunner();
	public BaseDao() {
		this.clazz=ReflectUtil.getSupserGenericType(getClass());//使用�?个reflect工具类，返回T的类
	}
	@Override
	public int add(String sql, Object... args) {
		return update(sql,args);
	}

	@Override
	public int update(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			return  queryRunner.update(conn,sql,args);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询列表出现问题！！");
		}
	}

	@Override
	public int delete(String sql, Object... args) {
		return  update(sql,args);
	}

	@Override
	public T queryForBean(String sql, Object... args) {
     Connection conn=null;
     try {
		//获取连接
    	 conn=ConnectionContext.getInstance().get();
    	 return queryRunner.query(conn, sql, args,new BeanHandler<T>(clazz));
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("查询出现问题！！");
	}
	
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			return  queryRunner.query(conn,sql,args,new BeanListHandler<T>(clazz));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询列表出现问题！！");
		}
	}

}
