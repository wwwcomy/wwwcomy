<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.haiyan.genmis.core.*"%>
<%@ page import="com.haiyan.genmis.core.exception.*"%>
<%@ page import="com.haiyan.genmis.core.db.*"%>
<%@ page import="com.haiyan.genmis.core.right.*"%>
<%@ page import="com.haiyan.genmis.core.struts.*"%>
<%@ page import="com.haiyan.genmis.core.paging.*"%>
<%@ page import="com.haiyan.genmis.castorgen.*"%>
<%@ page import="com.haiyan.genmis.castorgen.types.*"%>
<%@ page import="com.haiyan.genmis.util.*"%>
<%@ page import="com.haiyan.genmis.view.render.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="net.sf.json.*"%>
<%!
PluginOperation getPluginOperation(SrvContext context, Table table) {
	String pluginName = context.getPluginName();
	if (!StringUtil.isBlankOrNull(pluginName)) {
		PluginOperation[] plugins = table.getPluginOperation();
		if (plugins != null) {
			for (int i = 0; i < plugins.length; i++) {
				if (plugins[i].getName().equals(pluginName)) {
					return plugins[i];
				}
			}
		}
	}
	throw new Warning("不存在的插件:"+pluginName);
}
%>
<%
// <action path="/leedon_interface" scope="request" forward="/project/leedon/INTERFACE.jsp"/>
// http://oa.leedongifted.com:8087/haiyan/leedon_interface.do?__opr_data=T_WM_SDBPRODUCT&__opr_name=plugin&__pluginName=syncView&__out=json&PRODUCT_ID=1774
SrvContext srvContext = new SrvContext(request, response);
try {
	//User user = srvContext.getUser();
	//if (user==null) {
	//    throw new Warning(srvContext.trans(100032, "session_overtime"));
	//}
	Core core = srvContext.getCore();
	Table table = ConfigUtil.getTable(request.getParameter("__opr_data"));
	PluginOperation plugin = getPluginOperation(srvContext, table);
	core.doExecutePlugin(srvContext, table, plugin, (Qbq3Form)null);
    
	//srvContext.write("{success:true}");
	out.clear();
    out.print("[{success:true}]");
} catch(Throwable ex) {
	DebugUtil.error(ex);
	//out.clear();
    //out.print(Warning.getClientErr(ex.getMessage()));
	//srvContext.write(Warning.getClientErr(ex.getMessage()));
	JSONObject json = new JSONObject();
	json.put("success",false);
	json.put("error",ex.getMessage());
	out.clear();
    out.print("["+json+"]");
} finally {
	srvContext.close();
}
%>