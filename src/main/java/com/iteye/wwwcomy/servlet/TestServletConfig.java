package com.iteye.wwwcomy.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServletConfig extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(this.getServletConfig().getInitParameter("name"));
		@SuppressWarnings("rawtypes")
		Enumeration e = this.getInitParameterNames();
		while (e.hasMoreElements()) {
			String o = e.nextElement().toString();
			System.out.println(o);
		}
	}

}
