package com.iteye.wwwcomy.designpattern.filterchain.web;

public class Main {
	public static void main(String[] args) {
		String msg = "Hello World :)<script>alert(123)</script>某某";
		Request req = new Request();
		req.setReq(msg);
		Response res = new Response();
		res.setRes("Response");

		FilterChain fc = new FilterChain();
		fc.addFilter(new HtmlFilter()).addFilter(new SensitiveFilter());

		fc.doFilter(req, res, fc);
		System.out.println(req.getReq());
		System.out.println(res.getRes());
	}
}
