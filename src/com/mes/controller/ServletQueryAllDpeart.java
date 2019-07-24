package com.mes.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mes.dao.DepartDao;
import com.mes.factory.DaoFactory;
import com.mes.pojo.Depart;

@WebServlet(name = "ServletQueryAllClasss", value = "/ServletQueryAllClasss")
public class ServletQueryAllDpeart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DepartDao<Depart> departDao = (DepartDao<Depart>) DaoFactory.getInstance().getDaoByName("departDao");
        List<Depart> DepartList;
        DepartList = departDao.queryAllDepart();
		request.setAttribute("DepartList", DepartList);
		request.getRequestDispatcher("/WEB-INF/jsp/depart/depart.jsp").forward(request, response);
		
    }
}