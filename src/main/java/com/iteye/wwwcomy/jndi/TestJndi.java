package com.iteye.wwwcomy.jndi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestJndi {

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException {
		Context ctx = null;
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
		String name = "c:/myerp_error.log";
//		p.put(Context.PROVIDER_URL, "localhost");
		try {
			ctx = new InitialContext(p);
			Object obj = ctx.lookup(name);
			System.out.println("the name " + name + " is bound to object:" + obj);
		} finally {
		}
//		ctx.bind("test", "tcp.server");

	}

}
