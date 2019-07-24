package com.mes.dao.impl;

import java.util.List;
import java.util.Map;

import com.mes.base.BaseDao;
import com.mes.dao.RoleDao;
import com.mes.pojo.Role;
import com.mes.pojo.User;

public class RoleDaoImpl extends BaseDao<Role> implements RoleDao<Role> {

	@Override
	public List<Role> pageQueryData(Map<String, Object> map) {
		int start=(int) map.get("start");
		int size=(int) map.get("size");
		String sql="select * from mes_role order by id limit ?,?";
		Object[] params=new Object[] {start,size};
		List<Role> roles=queryForList(sql, params);
		return roles;
	}
	@Override
	public int getDefaultTotalSize() {
		String sql="select * from mes_role";
		List<Role> roles=queryForList(sql, null);
		return roles.size();
	}
	@Override
	public List<Role> pageQueryForParamData(Map<String, Object> map) {
		int start=(int) map.get("start");
		int size=(int) map.get("size");
		String search=(String) map.get("search");
		String sql="select * from mes_role where rolename like ? or roledesc like ? order by id limit ?,?";
		Object[] params=new Object[] {"%"+search+"%","%"+search+"%",start,size};
		List<Role> roles=queryForList(sql, params);
		return roles;
	}
	
	@Override
	public int pageQueryForParamDataSize(Map<String, Object> map) {
		String search=(String) map.get("search");
		String sql="select * from mes_role where rolename like ? or roledesc like ? ";
		Object[] params=new Object[] {"%"+search+"%","%"+search+"%"};
		List<Role> roles=queryForList(sql, params);
		return roles.size();
	}
	@Override
	public Boolean existRole(String roleName) {
		String sql="select * from mes_role where rolename = ?";
		Object[] params=new Object[] {roleName};
		Role role=queryForBean(sql, params);
		if(role!=null) {
			return role.getRoleName().equals(roleName)?true:false;
		}else {
			return false;
		}
	}
	@Override
	public void insert(Role role) {
		String sql="insert into mes_role (rolename,roledesc,roleremark) values (?,?,?)";
		Object[] params=new Object[] {role.getRoleName(),role.getRoleDesc(),role.getRoleRemark()};
		add(sql,params);
	}
}
