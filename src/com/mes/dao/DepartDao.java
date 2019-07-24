package com.mes.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mes.annotaion.MyTransaction;
import com.mes.base.Dao;
public interface DepartDao<Depart> extends Dao<Depart>{
	public List<Depart> pageQueryData(Map<String, Object> map);
	public int getDefaultTotalSize();
	public List<Depart> pageQueryForParamData(Map<String, Object> map);
	public int pageQueryForParamDataSize(Map<String, Object> map);
	public Boolean exist(String deptName);
	public int addDepart(Depart depart);
	public Depart queryForId(Integer id);
	public void update(Depart depart);
	public void delete4Id(Integer id);
	public List<Depart> queryAllDepart();
}
