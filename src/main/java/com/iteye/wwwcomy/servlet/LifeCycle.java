package com.iteye.wwwcomy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LifeCycle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Do Get Method");
	}

	@Override
	public void destroy() {
		System.out.println("Destroy");
	}

	@Override
	public void init() throws ServletException {
		System.out.println("Init");
	}

}
