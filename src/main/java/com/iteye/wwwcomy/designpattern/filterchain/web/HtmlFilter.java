package com.iteye.wwwcomy.designpattern.filterchain.web;

public class HtmlFilter implements IFilter {

	@Override
	public void doFilter(Request req, Response res, FilterChain chain) {
		String tmp = req.getReq();
		req.setReq(tmp.replaceAll("<", "{").replaceAll(">", "}"));
		chain.doFilter(req, res, chain);
		res.setRes(res.getRes() + "处理完HTML");
	}

}
