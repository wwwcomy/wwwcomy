//package com.iteye.wwwcomy.server;
//
//import java.io.File;
//import java.net.URL;
//import java.util.List;
//
//import org.apache.catalina.Host;
//import org.apache.catalina.Manager;
//import org.apache.catalina.ServerFactory;
//import org.apache.catalina.connector.Connector;
//import org.apache.catalina.core.StandardContext;
//import org.apache.catalina.core.StandardEngine;
//import org.apache.catalina.core.StandardServer;
//import org.apache.catalina.core.StandardWrapper;
//import org.apache.catalina.session.StandardManager;
//import org.apache.catalina.startup.Embedded;
//import org.apache.commons.modeler.util.IntrospectionUtils;
//
//import com.bokesoft.myerp.common.DebugUtil;
//import com.bokesoft.myerp.common.SharedBundle;
//import com.bokesoft.myerp.common.StringUtil;
//import com.bokesoft.myerp.common.io.FilePathIngoreCase;
//
//import test.start.entity.InitParam;
//import test.start.entity.Servlet;
//import test.start.entity.ServletMapping;
//import test.start.entity.TestConnector;
//import test.start.entity.TestDigesterConnector;
//import test.start.entity.TestWebapp;
//
//public class StartHttpServer_ {
//
//	public static void main(String[] args) throws Throwable {
//		try {
//			String docPath = null;
//			if (args.length > 0) {
//				docPath = args[0];
//			}
//			if (args.length>1) {
//				System.setProperty("YIGO.CONFIG_BUNDLE_NAME", args[1]);
////				SharedBundle.CONFIG_BUNDLE_NAME = args[1];
////				SharedBundle.clearPropertiesCache();
//			}
//			System.out.println(">>>>>>>SharedBundle.CONFIG_BUNDLE_NAME:"+SharedBundle.CONFIG_BUNDLE_NAME);
//			new StartHttpServer_().startServer(docPath);
//		} catch (Throwable e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
//
//	private Embedded embedded = null;
//
//	/**
//	 * @throws Throwable
//	 */
//	public void startServer(String docPath) throws Throwable {
//		int port = 8087;
//		String sPort = SharedBundle.getProperties("APP_SERVER", null);
//		if (!StringUtil.isBlankOrNull(sPort)) {
//			port = Double.valueOf(sPort.split(":")[1]).intValue();
//		}
//		CONTEXTNAME = SharedBundle.getProperties("APP_SERVICE", "yigo");
//		// String path = System.getProperty("user;.dir");
//		if (StringUtil.isBlankOrStrNull(docPath)) {
//			ClassLoader cp = ClassLoader.getSystemClassLoader();
//			URL url = cp.getResource("");
//			// URL[] urls = new URL[3];
//			String WEBINF = url.toString().substring(5, url.toString().length() - 9);
//			docPath = WEBINF.substring(0, WEBINF.length() - 8);
//		}
//		docPath = FilePathIngoreCase.getPath(docPath);
//		System.setProperty("SYS_PATH", docPath);
//		String runTimePath
//			// = WEBINF + File.separator + "runtime";
//			// = docPath + File.separator + "WEB-INF" + File.separator + "runtime";
//			= docPath;
//		String address = "0.0.0.0";
//		// System.out.println("***runtime:" + runTimePath);
//		// DebugUtil.debug("***runtime:"+runTimePath);
//		// System.setProperty("catalina.home", path);
//		// System.setProperty("catalina.base", runTimePath);
//		StandardServer server = (StandardServer) ServerFactory.getServer();
//		server.setGlobalNamingContext(new javax.naming.InitialContext());
//		// //
//		embedded = new Embedded();
//		embedded.setUseNaming(false);
//		// //embedded.setCatalinaHome(path);
//		embedded.setCatalinaBase(runTimePath);
//		// //create engine
//		StandardEngine engine = (StandardEngine) embedded.createEngine();
//		engine.setName("tomcat-"+port);
//		engine.setDefaultHost(address);
//		engine.setJvmRoute("jvm1");
//		// //realm
//		// MemoryRealm realm = new MemoryRealm();
//		// realm.setPathname("catalina-users.xml");
//		// engine.setRealm(realm);
//		// //host
//		Host host = embedded.createHost(address, File.separator);
//		host.setDeployOnStartup(true);
//		host.setAutoDeploy(true);
//		host.setRealm(engine.getRealm());
//		engine.addChild(host);
//
//		// String logPath = runTimePath + File.separator + "logs";
//		// AccessLogValve al = new AccessLogValve();
//		// al.setDirectory((new File(logPath)).getAbsolutePath());
//		// al.setPattern("%h %l %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-Agent}i\"");
//		// al.setPrefix("access_");
//		// al.setSuffix(".log");
//		// al.setResolveHosts(true);
//		// al.setRotatable(true);
//		// engine.addValve(al);
//
//		embedded.addEngine(engine);
//		// create context
//		createContext(docPath, host);
//		// create connect
//		createConnect(docPath, address, port, "http");
//		// 开启服务
//		embedded.initialize();
//		embedded.start();
//	}
//
//	private void createConnect(String path, String address, int port,
//			String scheme) throws Throwable {
//		// 这里测试一个服务只需创建一个容器连接器,ajp和https测试都用不到
//		TestDigesterConnector dtc = new TestDigesterConnector();
//		List<TestConnector> list = dtc.digester().getList();
//		TestConnector tc = list.get(0);
//
//		Connector connector = embedded.createConnector(address, port, scheme);
//		connector.setAttribute("allowTrace", tc.getAllowTrace());
//		connector.setAttribute("emptySessionPath", tc.getEmptySessionPath());
//		connector.setAttribute("enableLookups", tc.getEnableLookups());
//		connector.setAttribute("maxPostSize", tc.getMaxPostSize());
//		connector.setAttribute("protocol", tc.getProtocol());
//		connector.setAttribute("proxyName", tc.getProxyName());
//		connector.setAttribute("proxyPort", tc.getProxyPort());
//		connector.setAttribute("redirectPort", tc.getRedirectPort());
//		connector.setAttribute("scheme", scheme);
//		connector.setAttribute("secure", tc.getSecure());
//		// connector.setAttribute("clientAuth", "false");
//		// connector.setAllowTrace(true);
//		// connector.setUseBodyEncodingForURI(true);
//		connector.setURIEncoding(tc.getURIEncoding());
//		connector.setAttribute("URIEncoding", tc.getURIEncoding());
//		connector.setAttribute("useBodyEncodingForURI", tc.getUseBodyEncodingForURI());
//		connector.setAttribute("xpoweredBy", tc.getXpoweredBy());
//		connector.setAttribute("acceptCount", tc.getAcceptCount());
//		connector.setAttribute("address", address);
//		connector.setAttribute("bufferSize", tc.getBufferSize());
//		connector.setAttribute("compressableMimeType", tc.getCompressableMimeType());
//		connector.setAttribute("compression", tc.getCompression());
//		connector.setAttribute("connectionLinger", tc.getConnectionLinger());
//		connector.setAttribute("connectionTimeout", tc.getConnectionTimeout());
//		connector.setAttribute("disableUploadTimeout", tc.getDisableUploadTimeout());
//		connector.setAttribute("maxHttpHeaderSize", tc.getMaxHttpHeaderSize());
//		connector.setAttribute("maxKeepAliveRequests", tc.getMaxKeepAliveRequests());
//		connector.setAttribute("maxSpareThreads", tc.getMaxSpareThreads());
//		connector.setAttribute("maxThreads", tc.getMaxThreads());
//		connector.setAttribute("minSpareThreads", tc.getNoCompressionUserAgents());
//		connector.setAttribute("noCompressionUserAgents", tc.getNoCompressionUserAgents());
//		connector.setAttribute("restrictedUserAgents", tc.getRestrictedUserAgents());
//		connector.setAttribute("server", tc.getServer());
//		connector.setAttribute("port", "" + port);
//		connector.setAttribute("socketBuffer", tc.getSocketBuffer());
//		connector.setAttribute("strategy", tc.getStrategy());
//		connector.setAttribute("tcpNoDelay", tc.getTcpNoDelay());
//		connector.setAttribute("threadPriority", tc.getThreadPriority());
//		// keystore
//		IntrospectionUtils.setProperty(connector, "keystore", path
//				+ File.separator + "WEB-INF" + File.separator + File.separator
//				+ "lib" + File.separator + "yigo.keystore");
//		IntrospectionUtils.setProperty(connector, "keypass", "123456");
//		embedded.addConnector(connector);
//	}
//
//	private static String CONTEXTNAME = "yigo";
//
//	private void createContext(String path, Host host) throws Throwable {
//		CONTEXTNAME = SharedBundle.getProperties("APP_SERVICE", "yigo");
//		// create a context
//		TestWebapp tw = new TestWebapp();
//		List<ServletMapping> smapList = tw.digester().getMappingList();
//		List<Servlet> sList = tw.getServletsList();
//
//		// create a context
//		// System.out.println("***context:" + path);
//		StandardContext context = (StandardContext) embedded.createContext("/" + CONTEXTNAME, path);
//		{
////			TestVirtualDirContext fdc = new TestVirtualDirContext();
////			fdc.setDocBase(path);
////			fdc.setVirtualClasspath2(SharedBundle.getConfigPath(null)+File.separator+"Module"+File.separator+"WebApp");
////			context.setResources(fdc);
//		}
//		context.setLoader(embedded.createLoader(context.getClass().getClassLoader()));
//		context.setJ2EEApplication(CONTEXTNAME);
//		context.setJ2EEServer(CONTEXTNAME);
//		context.setDisplayName(CONTEXTNAME);
//		context.setDocBase(path);
//		context.setDistributable(false);
//		context.setCrossContext(false);
//		context.setAllowLinking(true);
//		context.setReloadable(false);
//		Manager mgr = new StandardManager();
//		context.setManager(mgr);
//		// //
//		context.getServletContext().setAttribute("_serverId", "default-server");
//		context.getServletContext().setAttribute("componentName", CONTEXTNAME);
//
//		// 处理默认servlet
//		for (int i = 0; i < smapList.size(); i++) {
//			if (smapList.get(i).getServletName().equals("default")) {
//				StandardWrapper Servlet = new StandardWrapper();
//				for (int j = 0; j < sList.size(); j++) {
//					if (sList.get(j).getServletName().equals("default")) {
//						Servlet.setServletClass(sList.get(j).getServletCalss());
//						Servlet.setServletName(sList.get(j).getServletName());
//						Servlet.setLoadOnStartup(Integer.parseInt(sList.get(j).getLoadOnStratup()));
//						List<InitParam> list = sList.get(j).getInitParamList();
//						for (int k = 0; k < list.size(); k++) {
//							Servlet.addInitParameter(
//									list.get(k).getParamName(), list.get(k).getParamValue());
//						}
//						for (int w = 0; w < smapList.size(); w++) {
//							if (smapList.get(i).getURLPattern().equals("dafault")) {
//								Servlet.addMapping("/");
//							} else if (smapList.get(i).getURLPattern().equals("jsp")) {
//								Servlet.addMapping(smapList.get(i).getURLPattern());
//							}
//						}
//
//						context.addChild(Servlet);
//						context.addServletMapping(smapList.get(i).getURLPattern(), smapList.get(i).getServletName());
//					}
//
//				}
//
//			} else if (smapList.get(i).getServletName().equals("jsp")) {
//				StandardWrapper jspServlet = new StandardWrapper();
//
//				for (int j = 0; j < sList.size(); j++) {
//					if (sList.get(j).getServletName().equals("jsp")) {
//
//						jspServlet.setServletClass(sList.get(j).getServletCalss());
//						jspServlet.setServletName(sList.get(j).getServletName());
//						jspServlet.setLoadOnStartup(Integer.parseInt(sList.get(j).getLoadOnStratup()));
//
//						List<InitParam> list = sList.get(j).getInitParamList();
//						for (int k = 0; k < list.size(); k++) {
//							jspServlet.addInitParameter(list.get(k).getParamName(), list.get(k).getParamValue());
//						}
//
//						for (int w = 0; w < smapList.size(); w++) {
//							if (smapList.get(i).getServletName().equals("dafault")) {
//								jspServlet.addMapping("/");
//							} else if (smapList.get(i).getServletName().equals("jsp")) {
//								jspServlet.addMapping(smapList.get(i).getURLPattern());
//							}
//						}
//
//						context.addChild(jspServlet);
//						context.addServletMapping("*.jsp", "jsp");
//					}
//				}
//			}
//		}
//
//		// Realm
//		context.setRealm(host.getRealm());
//		host.addChild(context);
//		// context.getMapper().setDefaultHostName(host.getName());
//	}
//
//}
