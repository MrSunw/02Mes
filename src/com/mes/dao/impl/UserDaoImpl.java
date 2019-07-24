package com.mes.dao.impl;

import java.util.List;
import java.util.Map;

import com.mes.base.BaseDao;
import com.mes.dao.UserDao;
import com.mes.pojo.User;

public class UserDaoImpl  extends BaseDao<User> implements UserDao<User> {

	@Override
	public void addUser(User user) {
		String sql="insert into mes_user (username,loginacct,userpasswd,email) values(?,?,?,?)";
		Object[] params=new Object[] {user.getUsername(),user.getLoginacct(),user.getUserpasswd(),user.getEmail()};
		add(sql,params);
		
	}

	@Override
	public User queryTest() {
		String sql="select * from mes_user where id=?";
		Object[] params=new Object[] {1};
		User user=queryForBean(sql, params);
		return user;
		
	}

	@Override
	public User query4Login(String loginacct) {
		String sql="select * from mes_user where loginacct=?";
		Object[] params=new Object[] {loginacct};
		User user=queryForBean(sql, params);
		return user;
	}

	@Override
	public List<User> pageQueryData(Map<String, Object> map) {
		int start=(int) map.get("start");
		int size=(int) map.get("size");
		String sql="select * from mes_user order by createtime desc limit ?,?";
		Object[] params=new Object[] {start,size};
		List<User> users=queryForList(sql, params);
		return users;
	}

}
