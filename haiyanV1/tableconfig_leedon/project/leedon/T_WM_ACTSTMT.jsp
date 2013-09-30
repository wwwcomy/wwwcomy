<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.haiyan.genmis.core.*"%>
<%@ page import="com.haiyan.genmis.core.exp.*"%>
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
	String suppID = srvContext.getParameter("__suppID");
	String month = srvContext.getParameter("__mon");
	String lastmon = srvContext.getParameter("__lastmon");
	int m = TypeConvert.toInt(month);
	int lastm =  TypeConvert.toInt(lastmon);
	if(lastm<201206){
		out.clear();
		out.print("{success:false}");
	}
	DBManager dbm = srvContext.getDBM();
	net.sf.json.JSONObject json = new net.sf.json.JSONObject();
	
	String sql = "select ALL_AMOUNT,BILL_PAY_AMOUNT,BILL_TIME from T_WM_ACCOUNTSTATEMENT where MONTH="+lastmon+" and SUPPLIER='"+suppID+"'";
	String[][] rs = dbm.getResultStrArray(sql, 3, null);
	String amount = "";
	if(rs.length>0) {
		json.put("success",true);
		int l = rs.length;
		for(int i = 0; i<1;i++){
			json.put("all",StringUtil.isBlankOrNull(rs[i][0])?0:rs[i][0]);
			json.put("payed",StringUtil.isBlankOrNull(rs[i][1])?0:rs[i][1]);
			json.put("getbill",StringUtil.isBlankOrNull(rs[i][1])?false:true);
		}
	}
	String sql2="select sum(OUT_ALL_AMOUNT) from V_WM_MONTHDTL where SUPP_MONTH='"+suppID+"_"+month+"'";
	String[][] rs1 = dbm.getResultStrArray(sql2, 1, null);
	if(rs1.length>0) {
		json.put("topay",StringUtil.isBlankOrNull(rs1[0][0])?0:rs1[0][0]);
	}
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