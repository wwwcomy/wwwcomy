package com.iteye.wwwcomy.designpattern.filterchain.web;

public class SensitiveFilter implements IFilter {

	@Override
	public void doFilter(Request req, Response res, FilterChain chain) {
		String tmp = req.getReq();
		req.setReq(tmp.replaceAll("某某", "xx"));
		chain.doFilter(req, res, chain);
		res.setRes(res.getRes() + "处理完sensitive");
	}
}
