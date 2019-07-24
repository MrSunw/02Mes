package com.mes.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.mes.base.BaseServlet;
//用来转发页面
public class DispatcherServlet extends BaseServlet{
	private static String RPATH="r:/WEB-INF/jsp/";
	private static String FPATH="f:/WEB-INF/jsp/";
	//  /DispatcherServlet/method=loginPage?name=ddd&age=10
	//登录页面
	public String loginPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"login.jsp";
	}
	//欢迎页面
	public String mainPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"main.jsp";
	}
	//角色页面
	public String rolePage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"role/role.jsp";
	}
	//角色页面
	public String roleAddPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"role/add.jsp";
	}
	//用户页面
	public String userPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"user/user.jsp";
	}
	//用户增加页面
	public String userAddPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"user/add.jsp";
	}
	//用户修改页面跳转
	public String userUpdatePage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"user/edit.jsp";
	}
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//
		HttpSession session=request.getSession();
		session.invalidate();
		return RPATH+"index.jsp";
	}
	//部门页面
	public String departPage(HttpServletRequest request,HttpServletResponse response) {
		return "/ServletQueryAllClasss";
	}
	//部门增加页面
	public String departAddPage(HttpServletRequest request,HttpServletResponse response) {
		return FPATH+"depart/add.jsp";
	}

	//部门修改页面
		public String departUpdatePage(HttpServletRequest request,HttpServletResponse response) {
			return FPATH+"depart/edit.jsp";
		}
}
