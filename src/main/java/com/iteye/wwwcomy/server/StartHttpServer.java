package com.iteye.wwwcomy.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

import com.iteye.wwwcomy.lxn.utils.DebugUtil;
import com.iteye.wwwcomy.lxn.utils.StringUtil;

public class StartHttpServer {
	
    public static void main(String[] args) throws Throwable {
		try {
//			Thread.sleep(10000);
	    	String docPath = null; // 脚本指定的contextPath
			if (args.length > 0) {
				docPath = args[0];
			}
			if (args.length>1) {
				System.setProperty("YIGO.CONFIG_BUNDLE_NAME", args[1]);
			}
			new StartHttpServer().startServer(docPath);
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
    }
    
	public void startServer(String docPath) throws Throwable {
		if (StringUtil.isBlankOrStrNull(docPath)) {
			ClassLoader cp = ClassLoader.getSystemClassLoader();
			URL url = cp.getResource("");
			String WEBINF = url.toString().substring(5, url.toString().length() - 9);
			docPath = WEBINF.substring(0, WEBINF.length() - 8);
		}
		//docPath = FilePathIngoreCase.getPath(docPath);
		System.setProperty("SYS_PATH", docPath);
//		String runTimePath
//		// = WEBINF + File.separator + "runtime";
//		= docPath + File.separator + "WEB-INF" + File.separator + "runtime";
		Server server = createServer(docPath);
		server.start();
	}

	protected Server createServer(String docPath) throws Throwable {
		String address = "0.0.0.0";
		int port = 8087;
		String sPort = "8080";
		if (!StringUtil.isBlankOrNull(sPort)) {
			port = Double.valueOf(sPort.split(":")[1]).intValue();
		}
		if (StringUtil.isEmpty(System.getProperty("jetty.host")))
			System.setProperty("jetty.host", address);
		if (StringUtil.isEmpty(System.getProperty("jetty.port")))
			System.setProperty("jetty.port", ""+port);
		XmlConfiguration configuration = new XmlConfiguration(
				new FileInputStream(docPath + File.separator + "WEB-INF" + File.separator + "jetty.xml"));
		Server server = (Server) configuration.configure();
		//Connector connector = createConnector(address, port);
		//server.addConnector(connector);
		createContext(docPath, server);
		return server;
	}

	protected WebAppContext createContext(String docPath, final Server server) throws Throwable {
		HandlerCollection contexts =  (HandlerCollection) server.getHandler();
//		contexts.addHandler(createRootCtx(docPath, server));
		WebAppContext context = contexts.getChildHandlerByClass(WebAppContext.class);
		String contextPath = "/" + "yigo";
		context.setContextPath(contextPath);
		ClassLoader cp = ClassLoader.getSystemClassLoader();
//		ClassLoader cp = Thread.currentThread().getContextClassLoader();
		context.setClassLoader(cp);
		
		FilterHolder filterHolder = new FilterHolder(GzipFilter.class);
		context.addFilter(filterHolder, "*.js", FilterMapping.ALL);
		context.addFilter(filterHolder, "*.css", FilterMapping.ALL);
		context.addFilter(filterHolder, "*.html", FilterMapping.ALL);
		context.addFilter(filterHolder, "*.jsp", FilterMapping.ALL);
		context.addFilter(filterHolder, "*.jpg", FilterMapping.ALL);
		context.addFilter(filterHolder, "*.gif", FilterMapping.ALL);
		
		ArrayList<String> contextPaths = new ArrayList<String>();
		contextPaths.add(docPath);
		//@Deprecated
		String projectPath = File.separator+"Module"+File.separator+"WebApp"; // 默认项目WebApp路径
		if (new File(projectPath).exists()) {
			contextPaths.add(projectPath);
		} 
		String[] webAppPaths = null;
		String s = ""; // 配置项目WebApp路径
		if (!StringUtil.isEmpty(s))
			webAppPaths = StringUtil.split(s, ";");
		if (webAppPaths != null)
			for (String path:webAppPaths) {
				if (new File(path).exists()) {
					contextPaths.add(path);
				} 
			}
		ResourceCollection resource = new ResourceCollection(contextPaths.toArray(new String[0]));
		context.setBaseResource(resource);
		context.addServlet(new ServletHolder(new HttpServlet() { // The stop servlet
			private static final long serialVersionUID = 1L;
			@Override
			public void service(ServletRequest req, ServletResponse resp)
					throws ServletException, IOException {
				try {
					DebugUtil.debug(">>> Stop server request from /STOP ...");
					server.stop();
					DebugUtil.debug(">>> Server stopped .");
				} catch (Exception e) {
					DebugUtil.debug(">>> Server stop error: " + e.getMessage() + ", force exit -1.");
					System.exit(-1);
				}
			}
		}), "/STOP");
		return context;
	}
	
}
