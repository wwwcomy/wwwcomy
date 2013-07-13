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
	// session user
	User user = srvContext.getUser();
	if (user==null) {
	    throw new Warning(srvContext.trans(100032, "session_overtime"));
	}
	// System.out.println("======"+srvContext.getParameter("__affirmUserID"));
	// System.out.println("======"+srvContext.getParameterValues("__selectedID"));
	String[] selectedIDS = srvContext.getParameterValues("__selectedID");
	String affirmUserID = srvContext.getParameter("__affirmUserID");
	String affirmNextTime = srvContext.getParameter("__affirmNextTime");
	String ids = StringUtil.toArrString(selectedIDS, ",", "'");
	Table bfjlTable = ConfigUtil.getTable("T_CRM_CUSTOMER_DTL");
	// //
	srvContext.getDBM().executeUpdate("update T_DIC_ORGA set USER_ID_2='"+affirmUserID+"' "
		+" where ID in ("+ids+") ");
	GeneralCore core = new GeneralCore(srvContext);
	for (int i=0;i<selectedIDS.length;i++) {
		Qbq3Form bfjl = new MapForm(); 
		// //
		bfjl.setParameter("USER_ID", affirmUserID); 
		bfjl.setParameter("NEXT_CONTACT_DATE", affirmNextTime);
		// bfjl.setParameter("JOURNAL_TIME", "");
		bfjl.setParameter("CUSTOMER_ID", selectedIDS[i]); 
		bfjl.setParameter("CONTENT", "被指定预约拜访的客户记录"); 
		bfjl.setParameter("ADDRESS", " ");
		bfjl.setParameter("ACTIVER", "-1");
		bfjl.setParameter("ACTIVER_POSITION", "-1");
		bfjl.setParameter("ACTIVER_PHONE", "-1");
		bfjl.setParameter("AVAILABLE", "1");
		bfjl.setParameter("STATUS", "0");
		bfjl.setParameter("TYPE_", "2");
		// SrvContext srvContext, Table table, Qbq3Form form
		bfjl = core.doSaveOne(srvContext, bfjlTable, bfjl); 
		// inerceptor 生成待办事宜 (多余)
		//haiyan.crm.interceptor.DICJournalInterceptor.generate(bfjlTable, bfjl,
        //	"生成待办事宜", srvContext);
	}
	srvContext.commit();
	out.clear();
	out.print("{success:true}");
	DebugUtil.debug("crm_affirmuser.jsp?ok");
} catch(Throwable ex) {
    DebugUtil.error(ex);
    out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
%>