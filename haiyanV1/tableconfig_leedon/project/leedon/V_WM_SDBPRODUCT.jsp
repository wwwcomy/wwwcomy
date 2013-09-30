<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.haiyan.genmis.core.*"%>
<%@ page import="com.haiyan.genmis.core.exception.*"%>
<%@ page import="com.haiyan.genmis.core.db.*"%>
<%@ page import="com.haiyan.genmis.core.right.*"%>
<%@ page import="com.haiyan.genmis.core.struts.*"%>
<%@ page import="com.haiyan.genmis.core.paging.*"%>
<%@ page import="com.haiyan.genmis.castorgen.Table"%>
<%@ page import="com.haiyan.genmis.castorgen.types.*"%>
<%@ page import="com.haiyan.genmis.util.*"%>
<%@ page import="com.haiyan.genmis.view.render.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%
SrvContext srvContext = new SrvContext(request, response);
try {
	User user = srvContext.getUser();
	if (user==null) {
	    throw new Warning(srvContext.trans(100032, "session_overtime"));
	}
	String model = srvContext.getParameter("__model");
	String typeid = srvContext.getParameter("__typeid");
	//Qbq3Form qf = new MapForm();
	//qf.set("TYPE_ID", typeid);
	Qbq3Form frm = srvContext.getDBM().findByPK("SDB_GOODS_TYPE", typeid, srvContext);
	if (frm==null) {
		out.clear();
		out.print("{success:false,type:"+typeid+"}");
		return;
	}
	String PROPS = frm.get("PROPS");
	String k = "i:"+model+";";
	int c = PROPS.indexOf(k);
	if (c>=0) {
		String s1 = PROPS.substring(c+k.length());
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("success",true);
		String re = s1.substring(0, s1.indexOf(";"));
		if(!"".equals(re))
			re = re.substring(re.indexOf("\"")+1,re.lastIndexOf("\""));
			//
		json.put("data",re);
		out.clear();
		out.print(json.toString());
	} else {
		out.clear();
		out.print("{success:false}");
	}
} catch(Throwable ex) {
	DebugUtil.error(ex);
	out.clear();
    out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
%>