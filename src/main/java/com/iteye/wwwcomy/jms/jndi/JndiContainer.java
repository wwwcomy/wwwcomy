package com.iteye.wwwcomy.jms.jndi;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiContainer {
	private Context ctx;

	public void init() throws Exception {
		//context初始化 文件管理
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file:/c:/sample");
		ctx = new InitialContext(env);
		loadServices();
	}

	// 从配置文件JNDIContainer.properties中读取JndiService的实现，绑定到Context中。
	private void loadServices() throws Exception {
		InputStream in = getClass().getResourceAsStream(
				"JndiContainer.properties");
		Properties props = new Properties();
		props.load(in);

		// inject jndiservice
		String s = props.getProperty("JndiServiceClass");
		Object obj = Class.forName(s).newInstance();
		if (obj instanceof JndiService) {
			JndiService service = (JndiService) obj;
			String[] ss = props.getProperty("JndiServiceProperty").split(";");
			for (int i = 0; i < ss.length; i++)
				service.setProperty(i, ss[i]);
			ctx.rebind(props.getProperty("JndiServiceName"), service);
		}
	}

	public void close() throws NamingException {
		ctx.close();
	}

	public Context getContext() {
		return ctx;
	}
}
