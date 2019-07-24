package com.mes.base;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.mes.common.JsonData;
import com.mes.factory.DaoFactory;

/**
 * BaseServlet用来作为其它Servlet的父类
 *   一个类多个请求处理方法，每个请求处理方法的原型与service相同！ 原型 = 返回值类型 + 方法名称 + 参数列表
 */
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Field[] fields=this.getClass().getDeclaredFields();
		Field[] elementsFields=new Field[fields.length];
		try {
			int count=0;
			for(Field field:fields) {
				field.setAccessible(true);
				String filedName=field.getName();
				if(filedName.endsWith("Dao")) {
					Dao targetDao=DaoFactory.getInstance().getDaoByName(filedName);
					field.set(this, targetDao);
					elementsFields[count++]=field;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		/**
		 * 1. 获取method参数，它是用户想调用的方法 2. 把方法名称变成Method类的实例对象 3. 通过invoke()来调用这个方法
		 */
		String methodName = request.getParameter("method");
		Method method = null;
		/**
		 * 2. 通过方法名称获取Method对象
		 */
		try {
			method = this.getClass().getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("您要调用的方法：" + methodName + "它不存在！", e);
		}
		
		/**
		 * 3. 通过method对象来调用它
		 */
		try {//returnResult
			Object  resultObj=method.invoke(this, request, response);
			//如果是json字符串，就直接写回数据给页面
			if(resultObj!=null&&resultObj instanceof JsonData) {
				//这里要把resultobj也转换成json格式
				JsonData jsonData=(JsonData) resultObj;
				Gson gson = new Gson();
				String jsonStr = gson.toJson(jsonData,JsonData.class);
				//"{"success":"true","result":"{"":"","":""}","message":"info---"}"
				response.getWriter().print(jsonStr);
				return ;
			}
			
			//如果不是json封装类，则得到跳转路径
			String result=(String)resultObj;
			//"r:/WEB-INF/jsp/index.jsp"
			if(result != null && !result.trim().isEmpty()) {//如果请求处理方法返回不为空
				int index = result.indexOf(":");//获取第一个冒号的位置
				if(index == -1) {//如果没有冒号，使用转发
					request.getRequestDispatcher(result).forward(request, response);
				} else {//如果存在冒号
					String start = result.substring(0, index);//分割出前缀
					String path = result.substring(index + 1);//分割出路径
					if(start.equals("f")) {//前缀为f表示转发
						request.getRequestDispatcher(path).forward(request, response);
					} else if(start.equals("r")) {//前缀为r表示重定向
						response.sendRedirect(request.getContextPath() + path);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			for(int i=0;i<elementsFields.length;i++) {
				elementsFields[i]=null;
			}
		}
	}
}
