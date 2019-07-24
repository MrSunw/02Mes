package com.mes.controller;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mes.base.BaseServlet;
import com.mes.bean.Page;
import com.mes.common.CommonUtils;
import com.mes.common.JsonData;
import com.mes.dao.DepartDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.Depart;

//@Controller
public class DepartServlet extends BaseServlet {

	private static String RPATH = "r:/WEB-INF/jsp/";
	private static String FPATH = "f:/WEB-INF/jsp/";
	private DepartDao<Depart> departDao = (DepartDao<Depart>) DaoFactory.getInstance().getDaoByName("departDao");

	public JsonData InsertAjax(HttpServletRequest request, HttpServletResponse response) {
		JsonData result=new JsonData();
		Depart depart=CommonUtils.toBean(request.getParameterMap(),Depart.class);
		if(depart!=null) {
			Boolean flag=departDao.exist(depart.getDepartName());
			if(flag) {
				result.setSuccess(false);
			}else {
				departDao.addDepart(depart);
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

			List<Depart> datas = departDao.pageQueryForParamData(map);
			// 查询出来的总条数
			int totalsize = departDao.pageQueryForParamDataSize(map);
			// 最大页码
			int totalno = 0;
			// 2 2
			if (totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			// 分页对象
			Page<Depart> resultPage = new Page<Depart>();

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

			List<Depart> datas = departDao.pageQueryData(map);
			// 查询出来的总条数
			int totalsize = departDao.getDefaultTotalSize();
			// 最大页码
			int totalno = 0;
			// 2 2
			if (totalsize % pagesize == 0) {
				totalno = totalsize / pagesize;
			} else {
				totalno = totalsize / pagesize + 1;
			}
			// 分页对象
			Page<Depart> resultPage = new Page<Depart>();

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

	//跳转修改页面
public String pageUpdateAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

	int id=Integer.parseInt(request.getParameter("id"));
	int pag=Integer.parseInt(request.getParameter("pag"));
	Depart newdepart=departDao.queryForId(id);
	request.setAttribute("newdepart", newdepart);
	request.setAttribute("pag", pag);
	return FPATH+"depart/edit.jsp";
}
//修改
public JsonData updatatAjax(HttpServletRequest request, HttpServletResponse response) {
	JsonData result=new JsonData();
	Depart depart=CommonUtils.toBean(request.getParameterMap(),Depart.class);
	 int pag=Integer.parseInt(request.getParameter("pag"));
	if(depart!=null) {
		departDao.update(depart);
		result.setResult(pag);
		result.setSuccess(true);
		
	}
	return result;
}
//删除
public JsonData deletetAjax(HttpServletRequest request, HttpServletResponse response) {
	JsonData result=new JsonData();
	Depart depart=CommonUtils.toBean(request.getParameterMap(),Depart.class);
	if(depart!=null) {
		departDao.delete4Id(depart.getId());
		result.setSuccess(true);
	}
	return result;
}
//删除多行
public JsonData deletetsAjax(HttpServletRequest request, HttpServletResponse response) {
	JsonData result=new JsonData();
	String idstr=request.getParameter("idstr");
	String[] ids=idstr.split(",");
	for(int i=0;i<ids.length;i++) {
		if(ids[i]!=null) {
		int id=Integer.parseInt(ids[i]);
		departDao.delete4Id(id);
		result.setSuccess(true);
	}
	}
	return result;
}

}

