package com.iteye.wwwcomy.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class Factory {
	public static Base getInstance(MethodInterceptor proxy, Object... args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Base.class);
		enhancer.setCallback(proxy);
		Base base;

		if (args != null && args.length > 0) {
			int len = args.length;
			Class<?>[] clazzes = new Class[len];
			for(int i=0;i<len;i++) {
				Object o = args[i];
				if(o != null) {
					clazzes[i] = o.getClass();
				}
			}
			base = (Base) enhancer.create(clazzes, args);
		} else {
			base = (Base) enhancer.create();
		}

		return base;
	}
}
