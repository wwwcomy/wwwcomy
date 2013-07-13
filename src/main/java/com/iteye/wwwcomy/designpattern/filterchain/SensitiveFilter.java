package com.iteye.wwwcomy.designpattern.filterchain;

public class SensitiveFilter implements IFilter {

	@Override
	public String doFilter(String str) {
		String tmp;
		tmp = str.replaceAll("某某", "xx");
		return tmp;
	}

}
