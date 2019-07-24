package com.mes.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Test;

import com.mes.dao.DepartDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.Depart;
import com.mes.utils.JdbcUtil;

public class Jdbctest {

	@Test
	public void jdbc() {
		try {
			System.out.println(JdbcUtil.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void jdbc1() {
//		System.out.println(ConnectionContext.getInstance());
		DepartDao<Depart> departDao=(DepartDao<Depart>) DaoFactory.getInstance().getDaoByName("departDao");
//		List<Depart> datas = departDao.pageQueryData(map);
//		Depart depart=new Depart("11","2","色");
//		System.out.println(depart);
//		departDao.addDepart(depart);
//		System.out.println(departDao.exist("sd"));
	List<Depart> departs=departDao.queryAllDepart();
	for(Depart departss:departs) {
		System.out.println(departss);
	}
	}

}
