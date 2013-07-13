package com.iteye.wwwcomy.designpattern.filterchain;

public class HtmlFilter implements IFilter {

	@Override
	public String doFilter(String str) {
		String tmp;
		tmp = str.replaceAll("<", "{").replaceAll(">", "}");
		return tmp;
	}

}
