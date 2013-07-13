package com.iteye.wwwcomy.designpattern.filterchain;

public class FaceFilter implements IFilter {

	@Override
	public String doFilter(String str) {
		String tmp;
		tmp = str.replaceAll(":\\)", "^O^");
		return tmp;
	}

}
