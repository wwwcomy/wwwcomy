package com.iteye.wwwcomy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map values = Collections.synchronizedMap(new HashMap());

	@SuppressWarnings("unchecked")
	public Object get() {
		Thread curThread = Thread.currentThread();
		Object o = values.get(curThread);
		if (o == null && !values.containsKey(curThread)) {
			o = initialValue();
			values.put(curThread, o);
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public void set(Object newValue) {
		values.put(Thread.currentThread(), newValue);
	}

	public Object initialValue() {
		return null;
	}
}
