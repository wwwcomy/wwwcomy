package com.iteye.wwwcomy.designpattern.filterchain;

public class Main {
	public static void main(String[] args) {
		String msg = "Hello World :)<script>alert(123)</script>某某";
		// 多个过滤器实际上可以看做一个过滤器，所以可以多个addFilter
		FilterChain fc = new FilterChain();
		fc.addFilter(new HtmlFilter()).addFilter(new SensitiveFilter());

		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new FaceFilter()).addFilter(fc);

		MsgProcessor mp = new MsgProcessor(fc2);
		mp.setMsg(msg);
		System.out.println(mp.process());
	}
}
