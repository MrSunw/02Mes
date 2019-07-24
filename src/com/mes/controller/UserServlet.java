package com.mes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.junit.Test;

import com.google.gson.Gson;
import com.mes.base.BaseServlet;
import com.mes.bean.Page;
import com.mes.common.CommonUtils;
import com.mes.common.JsonData;
import com.mes.dao.UserDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.User;
//@Controller
public class UserServlet extends BaseServlet{
	
	private static String RPATH="r:/WEB-INF/jsp/";
	private static String FPATH="f:/WEB-INF/jsp/";
	//@Resource
    //private UserDao<User> userDao;
	private UserDao<User> userDao=(UserDao<User>) DaoFactory.getInstance().getDaoByName("userDao");
	
	//用户分页
	public JsonData pageQueryAjax(HttpServletRequest request,HttpServletResponse response) {
		//准备好一个json对象
		JsonData result=new JsonData();
		try {
			int pageno=Integer.parseInt(request.getParameter("pageno"));
			int pagesize=Integer.parseInt(request.getParameter("pagesize"));
			//分页查询
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start",(pageno-1)*pagesize);
			map.put("size",pagesize);
			
			List<User> users=userDao.pageQueryData(map);
			//总条数
			int totalsize=users.size();
			//最大页码
			int totalno=0;
			if(totalsize%pagesize==0) {
				totalno=totalsize/pagesize;
			}else {
				totalno=totalsize/pagesize+1;
			}
			//分页对象
			Page<User> userPage=new Page<User>();
			
			userPage.setDatas(users);
			userPage.setPageno(pageno);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			//json化page对象
			Gson gson=new Gson();
			String pageStr = gson.toJson(userPage,Page.class);
			
//			result.setResult(userPage);
			result.setResult(pageStr);
			
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	//用户登录
	public JsonData loginAjax(HttpServletRequest request,HttpServletResponse response) {
		//准备好一个json对象
		JsonData result=new JsonData();
		//web2bean  2-to
		User user=CommonUtils.toBean(request.getParameterMap(), User.class);
		//query4Login  queryForLogin
		User dbUser= userDao.query4Login(user.getLoginacct());
		//比对密码
		if(dbUser!=null&&user.getUserpasswd().equals(dbUser.getUserpasswd())) {
			//在服务器搞一个空间，用来专门存储这个指定浏览器的登录用户状态
			HttpSession session=request.getSession();
			session.setAttribute("loginUser",dbUser);
			result.setSuccess(true);
		}else {
			result.setSuccess(false);
		}	
		
		return result;
	}

	public String exitPage(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session=request.getSession();
		session.invalidate();
		return FPATH+"login.jsp";
	}

}
