package com.iteye.wwwcomy.jms.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class JndiClass {
	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		try {
			JndiContainer container = new JndiContainer();
			container.init();

			// JNDI客户端使用标准JNDI接口访问命名服务。
			Context ctx = container.getContext();
			JndiService jndiService = (JndiService) ctx.lookup("JndiService");
			System.out.println("service field is:" + jndiService.getField());
			jndiService.test1();
			container.close();
			// System.out.println("the name " + name + " is bound to object:" +
			// obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testSimple() {
		Hashtable env = new Hashtable();

		String name = "C:/aaa.txt";
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.fscontext.RefFSContextFactory");
			Context ctx = new InitialContext(env);
			Object obj = ctx.lookup(name);
			System.out.println("the name " + name + " is bound to object:"
					+ obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
