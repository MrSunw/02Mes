package com.mes.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mes.annotaion.ReadDataSource;
import com.mes.base.BaseDao;
import com.mes.dao.DepartDao;
import com.mes.pojo.Depart;

public class DepartDaoImpl extends BaseDao<Depart> implements DepartDao<Depart> {

	@Override
	public List<Depart> pageQueryData(Map<String, Object> map) {
		int start=(int) map.get("start");
		int size=(int) map.get("size");
		String sql="select * from mes_depart order by id desc limit ?,?";
		Object[] params=new Object[] {start,size};
		List<Depart> depart=queryForList(sql, params);
		return depart;
		
	}
	@Override
	public int getDefaultTotalSize() {
		String sql="select * from mes_depart";
		List<Depart> depart=queryForList(sql, null);
		return depart.size();
	}
	@Override
	public List<Depart> pageQueryForParamData(Map<String, Object> map) {
		int start=(int) map.get("start");
		int size=(int) map.get("size");
		String search=(String) map.get("search");
		String sql="select * from mes_depart where departname like ? or departdesc like ? order by id limit ?,?";
		Object[] params=new Object[] {"%"+search+"%","%"+search+"%",start,size};
		List<Depart> depart=queryForList(sql, params);
		return depart;
	}
	
	@Override
	public int pageQueryForParamDataSize(Map<String, Object> map) {
		String search=(String) map.get("search");
		String sql="select * from mes_depart where departname like ? or departdesc like ? ";
		Object[] params=new Object[] {"%"+search+"%","%"+search+"%"};
		List<Depart> depart=queryForList(sql, params);
		return depart.size();
	}
	@Override
	public Boolean exist(String departName) {
		String sql="select * from mes_depart where departname = ?";
		Object[] params=new Object[] {departName};
		Depart depart=queryForBean(sql, params);
		System.out.println(depart);
		if(depart!=null) {
			return depart.getDepartName().equals(departName)?true:false;
		}else {
			return false;
		}
	}
	@Override
	public int addDepart(Depart depart) {
		String sql="INSERT INTO mes_depart (departcode,departname,departdesc,departnum,departremake) VALUES (?,?,?,?,?)";
		Object[] params =new Object[] {depart.getDepartCode(),depart.getDepartName(),depart.getDepartDesc(),depart.getDepartNum(),depart.getDepartRemake()};
		return add(sql,params);
	}
	@Override
	public Depart queryForId(Integer id) {
		String sql="select * from mes_depart where id=?";
		Object[] params=new Object[] {id};
		Depart depart=queryForBean(sql, params);
		return depart;
	}
	@Override
	public void update(Depart depart) {
		String sql="UPDATE mes_depart SET departcode=?,departname=?,departdesc=?,departnum=?,departremake=? WHERE id=?";
		Object[] params=new Object[] {depart.getDepartCode(),depart.getDepartName(),depart.getDepartDesc(),depart.getDepartNum(),depart.getDepartRemake(),depart.getId()};
		update(sql, params);
	}
	@Override
	public void delete4Id(Integer id) {
		String sql="delete from mes_depart where id=?";
		Object[] params=new Object[] {id};
		delete(sql, params);
		
	}
	DataSource dataSource = ReadDataSource.getDataSource();
	@Override
	public List<Depart> queryAllDepart() {
		 QueryRunner queryRunner = new QueryRunner(dataSource);
		String sql="select * from mes_depart";
		List<Depart> depart=queryForList(sql, null);
		return depart;
	}
}
