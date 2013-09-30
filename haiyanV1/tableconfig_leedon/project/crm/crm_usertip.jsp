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
	String cusID = srvContext.getParameter("__cusid");
	Table cusTable = ConfigUtil.getTable("T_CRM_CUSTOMER_QY");
	// 可控的srvContext.getDBM(false) TODO　不安全的可以SQL注入的 不过这个属于项目文件不用处理
	Qbq3Form cusForm = srvContext.getDBM().findByPK(cusTable, cusID, srvContext);
	String result = "";
	if (cusForm!=null) {
		if (!StringUtil.isBlankOrNull(cusForm.getParameter("__CREATE_USER_ID__NAME")))
			result += "创建人:"+cusForm.getParameter("__CREATE_USER_ID__NAME")+";\n";
		if (!StringUtil.isBlankOrNull(cusForm.getParameter("__USER_ID__NAME")))
			result += "客户经理:"+cusForm.getParameter("__USER_ID__NAME")+";\n";
		if (!StringUtil.isBlankOrNull(cusForm.getParameter("__USER_ID_1__NAME")))
			result += "项目经理:"+cusForm.getParameter("__USER_ID_1__NAME")+";\n";
		if (!StringUtil.isBlankOrNull(cusForm.getParameter("__USER_ID_2__NAME")))
			result += "经纪人:"+cusForm.getParameter("__USER_ID_2__NAME")+";\n";
		if (!StringUtil.isBlankOrNull(cusForm.getParameter("__USER_ID_3__NAME")))
			result += "电话销售:"+cusForm.getParameter("__USER_ID_3__NAME")+";\n";
	} else {
		result = "没有相关业务员";
	}
	out.clear();
	out.print(result);
	System.out.println("crm_usertip.jsp?ok");
} catch(Throwable ex) {
    // GeneralCore.send2Client(srvContext, ex.toString(), -1);
    ex.printStackTrace();
    out.print(ex.toString());
} finally {
	srvContext.close();
}
%>