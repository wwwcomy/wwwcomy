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
	Table table = ConfigUtil.getTable("T_CRM_MIANSHI");
	String MS_ADDR_QU = srvContext.getParameter("MS_ADDR_QU");
    String MS_ADDR_LU = srvContext.getParameter("MS_ADDR_LU");
    String MS_ADDR_LO = srvContext.getParameter("MS_ADDR_LO");
    String MS_ADDR_CE = srvContext.getParameter("MS_ADDR_CE");
    MS_ADDR_QU = MS_ADDR_QU==null?"":MS_ADDR_QU;
    MS_ADDR_LU = MS_ADDR_LU==null?"":MS_ADDR_LU;
    MS_ADDR_LO = MS_ADDR_LO==null?"":MS_ADDR_LO;
    MS_ADDR_CE = MS_ADDR_CE==null?"":MS_ADDR_CE;
	MS_ADDR_QU = java.net.URLDecoder.decode(MS_ADDR_QU, "UTF-8");
	MS_ADDR_LU = java.net.URLDecoder.decode(MS_ADDR_LU, "UTF-8");
	MS_ADDR_LO = java.net.URLDecoder.decode(MS_ADDR_LO, "UTF-8");
	MS_ADDR_CE = java.net.URLDecoder.decode(MS_ADDR_CE, "UTF-8");
	String ID = srvContext.getParameter("ID"); 
	//
	String sql = "";
	if (!StringUtil.isBlankOrNull(ID)) {
		sql += " and t_1.ID not like '"+ID+"' ";
	}
	sql += " and upper(t_1.MS_ADDR_QU) like '%"+MS_ADDR_QU.toUpperCase()+"%' ";
    sql += " and upper(t_1.MS_ADDR_LU) like '%"+MS_ADDR_LU.toUpperCase()+"%' ";
    sql += " and upper(t_1.MS_ADDR_LO) like '%"+MS_ADDR_LO.toUpperCase()+"%' ";
    sql += " and upper(t_1.MS_ADDR_CE) like '%"+MS_ADDR_CE.toUpperCase()+"%' ";
	// 
	ArrayList<Qbq3Form> ava_result = new ArrayList<Qbq3Form>();
	ArrayList<Qbq3Form> disava_result = new ArrayList<Qbq3Form>();
	Page page2 = srvContext.getDBM().findByFilter(table, sql, Page.MAXCOUNTPERQUERY, 1, srvContext);
	ArrayList list = (ArrayList) page2.getData();
	System.out.println(">checkMsAddr");
	for (int i = 0; i < list.size(); i++) {
		Qbq3Form uForm = (Qbq3Form) list.get(i);
		System.out.println(">cusName:"+uForm.getParameter("NAME")
			+",available:"+uForm.getParameter("AVAILABLE"));
		if ("1".equals(uForm.getParameter("AVAILABLE")))
			ava_result.add(uForm);
		else if ("0".equals(uForm.getParameter("AVAILABLE")))
			disava_result.add(uForm);
	}
	// // 
	if (ava_result.size() > 0) {
		out.clear();
		out.print("{error:'该面试地址已存在'}");
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