package com.iteye.wwwcomy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Technical speaking, this implementation is wrong, as this class only have a
 * <Thread:Object> Map.
 * 
 * Actually, each thread should have a thread map of its own, with a ThreadLocal
 * as the key, one object as the value. So when we call threadLocal.set(value),
 * it will actually call map.put(this, value), so we can have multiple thread
 * locals for one thread.
 * 
 * @author wwwcomy
 *
 */
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
