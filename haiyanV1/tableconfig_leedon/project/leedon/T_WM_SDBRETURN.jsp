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
	String productID = srvContext.getParameter("__pid");
	String orderID = srvContext.getParameter("__orderID");
	DBManager dbm = srvContext.getDBM();
	String sql = "select distinct(BATCH) from T_WM_OUTDETAIL where PRODUCTID="+productID+" and HEADID='"+orderID+"'";
	String[][] rs = dbm.getResultStrArray(sql, 1, null);
	String maxBatch = "";
	if(rs.length>0) {
		int l = rs.length;
		for(int i = 0; i<l;i++){
			String tmp;
			tmp = rs[i][0];
			if(StringUtil.isBlankOrNull(tmp)){
				out.clear();
				return;
			}
			maxBatch = maxBatch+tmp+",";
		}
	}
	if(StringUtil.isBlankOrNull(maxBatch))
		return;
	net.sf.json.JSONObject json = new net.sf.json.JSONObject();
	json.put("success",true);
	json.put("data",maxBatch.substring(0, maxBatch.length() - 1));
	out.clear();
	out.print(json.toString());
} catch(Throwable ex) {
	DebugUtil.error(ex);
	out.clear();
    out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
%>