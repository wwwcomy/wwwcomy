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
String TABLE = request.getParameter("TABLE");
String FIELD = request.getParameter("FIELD");
String ID = request.getParameter("ID");
ArrayList<Qbq3Form> CKLIST = null;
SrvContext srvContext = new SrvContext(request, response);
try {
	User user = srvContext.getUser();
	if (user==null) {
	    throw new Warning(srvContext.trans(100032, "session_overtime"));
	}
	//Qbq3Form frm = srvContext.getDBM().findByPK("SDB_GOODS_TYPE", typeid, srvContext);
	Page pg = srvContext.getDBM().findByForm("T_DIC_WAREHOUSE", null, 100, 1, srvContext);
	CKLIST = pg.getData();
} catch(Throwable ex) {
    ex.printStackTrace();
	DebugUtil.error(ex);
	out.clear();
    out.print(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
out.clear();
%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<meta name="description" content="Haiyan">

	<link rel="stylesheet" href="../../comResource/css/haiyan-debug.css">
 	<link rel="stylesheet" href="../../comResource/js/ext/resources/css/xtheme-gray.css">
 	
 	<script src="../../comResource/js/ext/adapter/prototype/prototype-debug.js"></script>
 	<script src="../../comResource/js/ext/adapter/ext/ext-base-debug.js"></script>
 	<script src="../../comResource/js/ext/ext-all-debug.js"></script>
 	
 	<script src="../../comResource/js/haiyan-debug.js"></script>

 	<script src="../../comResource/js/locale/ext-lang-zh_CN_GB2312.js"></script>
 	<script src="../../comResource/js/locale/lang-zh_CN_GB2312.js"></script>
	<script type="text/javascript">
		(function(){
			Ext.BLANK_IMAGE_URL=(Ext.isIE6 || Ext.isIE7 || Ext.isAir)?'comResource/js/ext/resources/images/default/s.gif':'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',Hy.upload_service='/haiyan',Hy.language='lang-zh_CN_GB2312',Hy.helper='crm',Hy.skin='xtheme-gray',__currDateTime='2013-07-27 22:41:55';
		})();
		function getStore() {
			alert('getStore');
			// GRID0.getStore();
		}
		var GRID0 = {
			cols:<%=CKLIST.size()%>
			,init:function(){
				var h;
					h='<tr rowIndex={0}><td>&nbsp;</td><td></td><td></td><td></td>';
					<%
					for(int i=0;i<CKLIST.size();i++){
					%>
						h+='<td></td><td></td><td></td>';
					<%
					}
					%>
					h+='</tr>';
				this.template = new Ext.Template(h);
				this.template.compile();
				//this.store = new Ext.data.ArrayStore();
			}
			,addRow:function(){
				//$('grid0').children[0].children
				//$('grid0').appendChild('');
				var el=Ext.get('grid0'), args = [el.dom.children[0].children.length-3];
				this.template.append(el, args, true);
			}
		};
		GRID0.init();
	</script>
	<style>
		td {
			border:1px solid #000000;
			text-align:center;
			height:30px;
			margin:0px;
			padding:0px;
		}
		.td1 {
			width:50px;
			font-weight:bold;
		}
		.td2 {
			width:150px;
			font-weight:bold;
		}
	</style>
</head>
<body>
	<table id="grid0">
		<tr rowspan=3>
			<td class="td1" rowspan=3><button onclick="GRID0.addRow()">+</button></td>
			<td class="td1" rowspan=3>订单数</td>
			<td class="td1" rowspan=3>分配数</td>
			<td class="td1" rowspan=3>实出数</td>
			<%
			for(int i=0;i<CKLIST.size();i++){
			%>
				<td class="td2" colspan=3 >子订单 <%=(i+1)%></td>
			<%
			}
			%>
		</tr>
		<tr>
			<%
			for(int i=0;i<CKLIST.size();i++){
			Qbq3Form frm = CKLIST.get(i);
			%>
				<td class="td2" colspan=3 >仓库:<%=frm.get("NAME")%></td>
			<%
			}
			%>
		</tr>
		<tr>
			<%
			for(int i=0;i<CKLIST.size();i++){
			%>
				<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>
			<%
			}
			%>
		</tr>
	</table>
	<div id="WHOWNER_Fld" style="display:none;"></div>
	<script>
	var WHOWNER_Fld = new Ext.ux.form.PagingComboBox({
	   id: '__WHOWNER__NAME',
	   name: '__WHOWNER__NAME',
	   renderTo: 'WHOWNER_Fld',
	   renderIt: true,
	   appendStr: '', 
	   conditionFields: [],
	   restrictiveFields: [],
	   valueField: 'ID',
	   displayField: 'NAME',
	   display_fields: ['NAME'],
	   hidden_field: 'WHOWNER',
	   hidden_value: '',
	   value: '',
	   url: Hy.getContextName()+'/ext_jsonlist.do?&__opr_data=T_WM_OPERATOR&__maxNumPerPage=10',
	   query_field: 'NAME',
	   store_root: 'T_WM_OPERATOR',
	   store_fields: [
		   {name:'ID'}
		   ,{name:'CODE'}
		   ,{name:'NAME'}
		   ,{name:'SEX'}
		   ,{name:'USED'}
		   ,{name:'ISKZ'}
		   ,{name:'EMAIL'}
		   ,{name:'MOBILE_PHONE'}
		   ,{name:'USEDSTATUS'}
		   ,{name:'WFBILLID'}
		   ,{name:'HYFORMKEY'}
		   ,{name:'HYVERSION'}
	   ],
	   pageSize: 10,
	   disabled: false,
	   allowBlank: true, 
	   metastyle:true 
	});
	</script>
</body>
</html>