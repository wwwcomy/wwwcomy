package com.iteye.wwwcomy.designpattern.filterchain.web;

public interface IFilter {
	public void doFilter(Request req, Response res, FilterChain chain);
}
