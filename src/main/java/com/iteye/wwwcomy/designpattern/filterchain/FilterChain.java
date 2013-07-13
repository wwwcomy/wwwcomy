package com.iteye.wwwcomy.designpattern.filterchain;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements IFilter {
	private List<IFilter> filters = new ArrayList<IFilter>();

	public FilterChain addFilter(IFilter f) {
		filters.add(f);
		return this;
	}

	public String doFilter(String str) {
		String r = str;
		for (IFilter f : filters) {
			r = f.doFilter(r);
		}
		return r;
	}
}
