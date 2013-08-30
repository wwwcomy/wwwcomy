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
String ORDER_ID = request.getParameter("ORDER_ID");
ArrayList<Qbq3Form> CKLIST = null;
ArrayList<Qbq3Form> OUTPRELIST = null;
Page pg1 = null;
Page pg2 = null;
SrvContext srvContext = new SrvContext(request, response);
try {
	User user = srvContext.getUser();
	if (user==null) {
	    throw new Warning(srvContext.trans(100032, "session_overtime"));
	}
	//Qbq3Form frm = srvContext.getDBM().findByPK("SDB_GOODS_TYPE", typeid, srvContext);
	pg1 = srvContext.getDBM().findByForm("T_DIC_WAREHOUSE", null, 100, 1, srvContext);
	CKLIST = pg1.getData();
	
	Qbq3Form qfrm = new MapForm();
	qfrm.set("ORDER_ID", ORDER_ID);
	pg2 = srvContext.getDBM().findByForm("T_WM_OUTPRE", qfrm, 100, 1, srvContext);
	OUTPRELIST = pg2.getData(); // PRODUCTID NAME
	
	out.clear();
} catch(Throwable ex) {
	DebugUtil.error(ex);
	out.clear();
    out.println(Warning.getClientErr(ex.getMessage()));
} finally {
	srvContext.close();
}
out.println("------------------");
out.println("\n");
out.println("明细:"+pg1.toJSon());
out.println("\n");
out.println("------------------");
out.println("\n");
out.println("明细:"+pg2.toJSon());
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
			<td class="td1" rowspan=3><!--<button onclick="GRID0.addRow()">+</button>-->产品名</td>
			<td class="td1" rowspan=3>库存数</td>
			<td class="td1" rowspan=3>订单数</td>
			<td class="td1" rowspan=3>分配数</td>
			<td class="td1" rowspan=3>实出数</td>
			<%for(int i=0;i<CKLIST.size();i++){%>
				<td class="td2" colspan=3 >子订单 <%=(i+1)%></td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<CKLIST.size();i++){Qbq3Form frm=CKLIST.get(i);%>
				<td class="td2" colspan=3 >仓库:<%=frm.get("NAME")%></td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<CKLIST.size();i++){%>
				<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>
			<%}%>
		</tr>
	</table>
	<div id="WHOWNER_Fld" style="display:none;"></div>
	<script>
	(function(){
		Ext.BLANK_IMAGE_URL=(Ext.isIE6 || Ext.isIE7 || Ext.isAir)?'comResource/js/ext/resources/images/default/s.gif':'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',Hy.upload_service='/haiyan',Hy.language='lang-zh_CN_GB2312',Hy.helper='crm',Hy.skin='xtheme-gray', __currDateTime='2013-07-27 22:41:55';
	})();
	function getStore() {
		alert('getStore');
	}
	/*var GRIDDATA0 = new Ext.data.JsonStore({
		data: <%=pg2.toJSon()%>,
		fields:
		[ 
		   'ITEM_ID' 
		  ,'__operation' 
		  ,'__flag' 
		  ,'ORDER_ID' 
		  ,'OUT_CODE' 
		  ,'ITEM_ID_PK' 
		  ,'__PRODUCTID__WMCODE' 
		  ,'PRODUCTID' 
		  ,'PRODUCT_ID' 
		  ,'NAME' 
		  ,'WMCODE' 
		  ,'SUPP_CODE' 
		  ,'PRE' 
		  ,'OUT_COUNT' 
		  ,'OUT_RCOUNT' 
		  ,'IN_PRICE' 
		  ,'OUT_PRICE' 
		  ,'PRO_COUNT' 
		  ,'OUT_ALL_PRICE' 
		  ,'PACKING_NUM' 
		  , { name: 'CAPACITYNUM', type: 'float' } 
		  ,'WEIGHTNUM' 
		  ,'SUPPLIER' 
		  ,'WAREHOUSE' 
		  , { name: 'All_CAPACITYNUM', type: 'float' } 
		  , { name: 'All_WEIGHTNUM', type: 'float' } 
		  ,'REMAINDER_NUM' 
		  ,'LOGISTICS_NAME' 
		  ,'LOGISTICS_CODE' 
		  ,'LOGISTICS_MEMO' 
		  ,'HYVERSION' 
		  ,'BILL_STATUS' 
		  ,'WFBILLID' 
		  ,'HYFORMKEY' 
		]
	});*/
	//new Ext.data.ArrayStore(DATA0, {idProperty:'ITEM_ID'});
	var GRID0 = {
		FIXROW:3 // 固定行
		,FIXCOL:5 // 固定列
		,cm:<%=pg1.toJSon()%> // 列模型
		,rm:<%=pg2.toJSon()%> // 行模型
		,mapRow:{} // 行索引
		,mapCol:{} // 列索引
		,sumRow:{} // 行汇总
		,sumCol:{} // 列汇总
		//,store:GRIDDATA0
		,change:function(PID,WID,col,val) {
			var t;
			if (t=this.mapRow[PID]) {
				if (t[WID] && t[WID]['ROW']>=0) {
					var ROW = t[WID]['ROW'];
					this.rm[ROW]['OUT_PCOUNT']=val;
					this.mapRow[PID]={};
					this.sumRow[PID]['OUT_PCOUNT']=0;
					this.calModel(PID);
					this.getDataCellEl(ROW, 3).innerHTML=this.sumRow[PID]['OUT_PCOUNT'];
				}
			}
		}
		,getGridEl:function() {
			return Ext.get('grid0');
		}
		,getDataCellEl:function(r, c) {
			return this.getGridEl().dom.children[r+1].children[0].children[c];
		}
		,getRowCount:function() {
			return this.getGridEl().dom.children[0].children.length-this.FIXROW;
		}
		,calModel:function(prodID){
			this.rm.each(function(row, index) { // PRO_COUNT:库存数 OUT_COUNT:订单数(应出库) OUT_PCOUNT:分配数 OUT_RCOUNT:实出库
				if (prodID && prodID!=row['ITEM_ID'])
					return;

				var PID = row['ITEM_ID'];
				if (!this.mapRow[PID])
					this.mapRow[PID]={};
				this.mapRow[PID][row['WAREHOUSE']]={PNAME:row['NAME'], PRO_COUNT:row['PRO_COUNT']||0, OUT_PCOUNT:row['OUT_PCOUNT']||0, OUT_COUNT:row['OUT_COUNT']||0, OUT_RCOUNT:row['OUT_RCOUNT']||0, ROW:index};
				
				var WID = row['WAREHOUSE'];
				if (!this.mapCol[WID])
					this.mapCol[WID]={};
				this.mapCol[WID][row['ITEM_ID']]={PNAME:row['NAME'], PRO_COUNT:row['PRO_COUNT']||0, OUT_PCOUNT:row['OUT_PCOUNT']||0, OUT_COUNT:row['OUT_COUNT']||0, OUT_RCOUNT:row['OUT_RCOUNT']||0, ROW:index};
				
				if (!this.sumRow[PID])
					this.sumRow[PID]={};
				this.sumRow[PID]['PRO_COUNT']=(this.sumRow[PID]['PRO_COUNT']||0)*1+(row['PRO_COUNT']||0)*1;
				this.sumRow[PID]['OUT_COUNT']=(this.sumRow[PID]['OUT_COUNT']||0)*1+(row['OUT_COUNT']||0)*1;
				this.sumRow[PID]['OUT_PCOUNT']=(this.sumRow[PID]['OUT_PCOUNT']||0)*1+(row['OUT_PCOUNT']||0)*1;
				this.sumRow[PID]['OUT_RCOUNT']=(this.sumRow[PID]['OUT_RCOUNT']||0)*1+(row['OUT_RCOUNT']||0)*1;
			}, this);
		}
		,render:function(){
			this.calModel();
			this.rm.each(function(row, index) {
				var PID = row['ITEM_ID'];
				if (this.mapRow[PID]) {
					var args = [
						this.getRowCount() // 行号
						, row['ITEM_ID'] // 产品ID
						
						, row['NAME'] // 产品名称
						, this.sumRow[PID]['PRO_COUNT'] // 库存
						, this.sumRow[PID]['OUT_COUNT'] // 订单数（应出库）
						, this.sumRow[PID]['OUT_PCOUNT'] // 分配数
						, this.sumRow[PID]['OUT_RCOUNT'] // 实际出库
					];
					for (var i=0;i<this.cm.length;i++) {
						var WID = this.cm[i]['ID'];
						var T = this.mapRow[PID][WID];
						if (T) 
							args.push(WID, T['PRO_COUNT'], '<input class="td1" onchange="GRID0.change(\''+PID+'\',\''+WID+'\','+i+',this.value)" value="'+T['OUT_PCOUNT']+'"></input>', T['OUT_RCOUNT']);
						else
							args.push(WID, 0, 0, 0);
					}
					this.template.append(this.getGridEl(), args, true);
				}
			}, this);
		}
		,init:function(){
			var h;
			h='<tr rowIndex={0} rowID={1} >';
			h+='<td nowrap>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td>';
			for (var i=0;i<this.cm.length*4;i+=4) { // colID={'+(7+i)+'}
				h+='<td>{'+(8+i)+'}</td><td>{'+(9+i)+'}</td><td>{'+(10+i)+'}</td>';
			}
			h+='</tr>';
			this.template = new Ext.Template(h);
			this.template.compile();
		}
		,addRow:function(){
			var args = [this.getRowCount()];
			this.template.append(this.getGridEl(), args, true);
		}
	};
	GRID0.init();
	GRID0.render();
	/*var WHOWNER_Fld = new Ext.ux.form.PagingComboBox({
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
	});*/
	</script>
</body>
</html>