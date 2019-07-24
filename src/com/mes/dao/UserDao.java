package com.mes.dao;

import java.util.List;
import java.util.Map;

import com.mes.annotaion.MyTransaction;
import com.mes.base.Dao;

public interface UserDao<User> extends Dao<User>{
	@MyTransaction
	public void addUser(User user);
	
	public User queryTest() ;

	public User query4Login(String loginacct);

	public List<User> pageQueryData(Map<String, Object> map);

}
