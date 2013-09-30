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
	String affirmWHID = srvContext.getParameter("__affirmWarehouseID");
	Qbq3Form q1 = srvContext.getDBM().findByPK("T_DIC_WAREHOUSE", affirmWHID, srvContext);
	if (q1==null)
		out.print("{success:false}");
		
	String affirmWHOwnerID = srvContext.getParameter("__affirmWhownerID");
	Qbq3Form q2 = srvContext.getDBM().findByPK("SYSOPERATOR_COMBO", affirmWHOwnerID, srvContext);
	if (q2==null)
		out.print("{success:false}");
		
	user.setProperty("WAREHOUSEID", affirmWHID);
	user.setProperty("WAREHOUSENAME", q1.get("NAME"));
		
	user.setProperty("WHOWNERID", affirmWHOwnerID);
	if (q2!=null)
		user.setProperty("WHOWNERNAME", q2.get("NAME"));
	srvContext.setUser(user);
	out.print("{success:true}");
	
	System.out.println("leedon_affirmwarehouse.jsp?ok:"+user.getProperty("WAREHOUSEID")+","+user.getProperty("WAREHOUSENAME"));
	System.out.println("leedon_affirmwhowner.jsp?ok:"+user.getProperty("WHOWNERID")+","+user.getProperty("WHOWNERNAME"));
} catch(Throwable ex) {
	DebugUtil.error(ex);
    out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
%>