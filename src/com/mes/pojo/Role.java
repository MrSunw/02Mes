package com.mes.pojo;

public class Role {
	private Integer id;
	private String roleName;
	private String roleDesc;
	private String roleRemark;
	
	public Role() {
		super();
	}
	public Role(Integer id, String roleName, String roleDesc, String roleRemark) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleRemark = roleRemark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", roleRemark=" + roleRemark
				+ "]";
	}
}
