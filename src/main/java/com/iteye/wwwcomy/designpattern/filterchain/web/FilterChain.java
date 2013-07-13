package com.iteye.wwwcomy.designpattern.filterchain.web;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements IFilter {
	private List<IFilter> filters = new ArrayList<IFilter>();
	int index = 0;

	public FilterChain addFilter(IFilter f) {
		filters.add(f);
		return this;
	}

	@Override
	public void doFilter(Request req, Response res, FilterChain chain) {
		if (index == filters.size())
			return;
		IFilter f = filters.get(index);
		index++;
		f.doFilter(req, res, chain);

	}
}
