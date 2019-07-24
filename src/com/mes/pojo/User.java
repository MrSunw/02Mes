package com.mes.pojo;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2900466913200185672L;
	
	private Integer id;
	private String username;
	private String loginacct;
	private String userpasswd;
	private String email;
	
	public User() {
	}
	public User(Integer id, String username, String loginacct, String userpasswd, String email) {
		super();
		this.id = id;
		this.username = username;
		this.loginacct = loginacct;
		this.userpasswd = userpasswd;
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginacct() {
		return loginacct;
	}
	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}
	
	public String getUserpasswd() {
		return userpasswd;
	}
	public void setUserpasswd(String userpasswd) {
		this.userpasswd = userpasswd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "MesUser [id=" + id + ", username=" + username + ", loginacct=" + loginacct + ", userpasswd="
				+ userpasswd + ", email=" + email + "]";
	}
}
