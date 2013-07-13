package com.iteye.wwwcomy.procus;

import java.util.*;

public class WareHouse {
	private int max = 100;
	private List<Object> warehouse = new LinkedList<Object>();

	public int getSize() {
		return warehouse.size();
	}

	public synchronized boolean put(Object o) {
		if (getSize() > max)
			return false;
		else {
			warehouse.add(o);
			notifyAll();
		}
		return true;
	}
}
