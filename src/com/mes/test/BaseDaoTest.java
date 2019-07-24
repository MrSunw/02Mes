package com.mes.test;

import org.junit.Test;

import com.mes.dao.UserDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.User;

public class BaseDaoTest {
	@Test
	public void test2() {
		UserDao userDao=(UserDao) DaoFactory.getInstance().getDaoByName("userDao");
		User user=new User();
		user.setLoginacct("login33");
		user.setUsername("用户2a");
		user.setUserpasswd("passw01f");
		user.setEmail("111111@163.comf");
		userDao.addUser(user);
	}
	
	@Test
	public void test() {
		UserDao userDao=(UserDao) DaoFactory.getInstance().getDaoByName("userDao");
		userDao.queryTest();
	}
	
}
