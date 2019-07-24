package com.mes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mes.base.BaseServlet;
import com.mes.bean.Page;
import com.mes.common.CommonUtils;
import com.mes.common.JsonData;
import com.mes.dao.RoleDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.Role;

//@Controller
public class RoleServlet extends BaseServlet {

	private static String RPATH = "r:/WEB-INF/jsp/";
	private static String FPATH = "f:/WEB-INF/jsp/";
	private RoleDao<Role> roleDao = (RoleDao<Role>) DaoFactory.getInstance().getDaoByName("roleDao");

	public JsonData roleInsertAjax(HttpServletRequest request, HttpServletResponse response) {
		JsonData result=new JsonData();
		Role role=CommonUtils.toBean(request.getParameterMap(),Role.class);
		System.out.println(role);
		if(role!=null) {
			Boolean flag=roleDao.existRole(role.getRoleName());
			if(flag) {
				result.setSuccess(false);
			}else {
				roleDao.insert(role);
				result.setSuccess(true);
			}
		}
		return result;
	}
	
	public JsonData pageQueryForParamsAjax(HttpServletRequest request, HttpServletResponse response) {
		// 准备好一个json对象
		JsonData result = new JsonData();
		try {
			// 1-2 2-2 3-2
			// 01 23 45
			// (pageno-1)*pagesize--(1-1)*2=0
			// (pageno-1)*pagesize--(2-1)*2=2
			// (pageno-1)*pagesize--4
			// 当前第几页
			int pageno = Integer.parseInt(request.getParameter("pageno"));
			// 每页多少条
			int pagesize = Integer.parseInt(request.getParameter("pagesize"));
			// 查询条件
			String search=request.getParameter("search");
			// 分页查询
			Map<String, Object> map = new HashMap<String, Object>();
			// mysql索引数据的起始位置
			map.put("start", (pageno - 1) * pagesize);
			// 索引起始位置后截取的条数
			map.put("size", pagesize);
			if(search!=null&&search.length()>0) {
				map.put("search",search);
			}

			List<Role> datas = roleDao.pageQueryForParamData(map);
			// 查询出来的总条数
			int totalsize = roleDao.pageQueryForParamDataSize(map);
			// 最大页码
			int totalno = 0;
			// 2 2
			if (totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			// 分页对象
			Page<Role> resultPage = new Page<Role>();

			resultPage.setDatas(datas);
			resultPage.setPageno(pageno);
			resultPage.setTotalno(totalno);
			resultPage.setTotalsize(totalsize);
			// json化page对象-------为什么要gson化两层 baseservlet 一个是这里的
			Gson gson = new Gson();
			String pageStr = gson.toJson(resultPage, Page.class);
			result.setResult(pageStr);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	// 用户分页
	public JsonData pageQueryAjax(HttpServletRequest request, HttpServletResponse response) {
		// 准备好一个json对象
		JsonData result = new JsonData();
		try {
			// 1-2 2-2 3-2
			// 01 23 45
			// (pageno-1)*pagesize--(1-1)*2=0
			// (pageno-1)*pagesize--(2-1)*2=2
			// (pageno-1)*pagesize--4
			// 当前第几页
			int pageno = Integer.parseInt(request.getParameter("pageno"));
			// 每页多少条
			int pagesize = Integer.parseInt(request.getParameter("pagesize"));
			// 分页查询
			Map<String, Object> map = new HashMap<String, Object>();
			// mysql索引数据的起始位置
			map.put("start", (pageno - 1) * pagesize);
			// 索引起始位置后截取的条数
			map.put("size", pagesize);

			List<Role> datas = roleDao.pageQueryData(map);
			// 查询出来的总条数
			int totalsize = roleDao.getDefaultTotalSize();
			// 最大页码
			int totalno = 0;
			// 2 2
			if (totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			// 分页对象
			Page<Role> resultPage = new Page<Role>();

			resultPage.setDatas(datas);
			resultPage.setPageno(pageno);
			resultPage.setTotalno(totalno);
			resultPage.setTotalsize(totalsize);
			// json化page对象-------为什么要gson化两层 baseservlet 一个是这里的
			Gson gson = new Gson();
			String pageStr = gson.toJson(resultPage, Page.class);
			result.setResult(pageStr);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
		// JsonData result=new JsonData();
		// result.setSuccess(true);
		// result.setMessage("好嗨哦");
		// List<Role> roles=new ArrayList<Role>();
		// roles.add(new Role(1,"经理","是个狠人","我设的"));
		// roles.add(new Role(2,"副经理","papi","xxx射的"));
		// roles.add(new Role(3,"秘书","开源小能手","开源软件"));
		// result.setResult(roles);
		// return result;
	}

}
