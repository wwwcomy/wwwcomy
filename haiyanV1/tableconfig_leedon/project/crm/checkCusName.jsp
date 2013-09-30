<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.haiyan.genmis.core.*"%>
<%@ page import="com.haiyan.genmis.core.db.*"%>
<%@ page import="com.haiyan.genmis.core.exception.*"%>
<%@ page import="com.haiyan.genmis.core.struts.*"%>
<%@ page import="com.haiyan.genmis.core.right.*"%>
<%@ page import="com.haiyan.genmis.core.paging.*"%>
<%@ page import="com.haiyan.genmis.view.render.*"%>
<%@ page import="com.haiyan.genmis.util.*"%>
<%@ page import="com.haiyan.genmis.castorgen.*"%>
<%@ page import="com.haiyan.genmis.castorgen.types.*"%>
<%@ page import="haiyan.component.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%
SrvContext srvContext = new SrvContext(request, response);
try {
	//
	Table table = ConfigUtil.getTable("T_DIC_ORGA");
	String currCusID = srvContext.getParameter("ID");
	String currCusName = srvContext.getParameter("NAME");
	String currAddress = srvContext.getParameter("ADDRESS");
	currCusName = java.net.URLDecoder.decode(currCusName, "UTF-8");
	currAddress = java.net.URLDecoder.decode(currAddress, "UTF-8");
	//
	String sql = "";
	if (!StringUtil.isBlankOrNull(currCusID)) {
		sql += " and t_1.ID not like '"+currCusID+"' ";
	}
	sql += " and t_1.NAME like '"+currCusName+"' "
		+ " and t_1.ADDRESS like '"+currAddress+"' ";
	// 
	ArrayList<Qbq3Form> ava_result = new ArrayList<Qbq3Form>();
	ArrayList<Qbq3Form> disava_result = new ArrayList<Qbq3Form>();
	Page page2 = srvContext.getDBM().findByFilter(table, sql, Page.MAXCOUNTPERQUERY, 1, srvContext);
	ArrayList list = (ArrayList) page2.getData();
	System.out.println(">currCusName:"+currCusName);
	System.out.println(">currAddress:"+currAddress);
	for (int i = 0; i < list.size(); i++) {
		Qbq3Form uForm = (Qbq3Form) list.get(i);
		System.out.println(">uCusName:"
			+",name:"+uForm.getParameter("NAME")
			+",addr:"+uForm.getParameter("ADDRESS")
			+",ava:"+uForm.getParameter("AVAILABLE"));
		if ("1".equals(uForm.getParameter("AVAILABLE")))
			ava_result.add(uForm);
		else if ("0".equals(uForm.getParameter("AVAILABLE")))
			disava_result.add(uForm);
	}
	// 
	if (ava_result.size() > 0) {
		out.clear();
		out.print("{error:'该客户已存在'}");
	} else {
		out.clear();
		out.print("{success:true}");
	}
} catch(Throwable ex) {
	DebugUtil.error(ex);
	out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
%>