<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.haiyan.genmis.core.*"%>
<%@ page import="com.haiyan.genmis.core.exception.*"%>
<%@ page import="com.haiyan.genmis.core.db.*"%>
<%@ page import="com.haiyan.genmis.core.right.*"%>
<%@ page import="com.haiyan.genmis.core.paging.*"%>
<%@ page import="com.haiyan.genmis.castorgen.Table"%>
<%@ page import="com.haiyan.genmis.castorgen.types.*"%>
<%@ page import="com.haiyan.genmis.util.*"%>
<%@ page import="com.haiyan.genmis.view.render.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="net.sf.json.*"%>
<%
String TABLE = request.getParameter("TABLE");
String FIELD = request.getParameter("FIELD");
String ORDER_ID = request.getParameter("ORDER_ID");
if (StringUtil.isEmpty(ORDER_ID))
	ORDER_ID = request.getParameter("ID");
SrvContext srvContext = null;
try {
	srvContext = new SrvContext(request, response);
	User user = srvContext.getUser();
	if (user==null) {
	    throw new Warning("session_overtime");
	}
	Qbq3Form qfrm = null;
	
	qfrm = new MapForm();
	qfrm.set("USEDSTATUS", "1");
	Page WAREHOUSE_DATAS = srvContext.getDBM().findByForm("T_DIC_WAREHOUSE_COMBO", qfrm, 100, 1, srvContext); // 列模型：仓库
	ArrayList<Qbq3Form> CKLIST = WAREHOUSE_DATAS.getData();
	
	qfrm = new MapForm();
	qfrm.set("ORDER_ID", ORDER_ID);
	Page OUTPRE_DATAS = srvContext.getDBM().findByForm("T_WM_OUTPRE2", qfrm, 5000, 1, srvContext); // 编辑值模型：应出库明细（应出库订单）
	
	JSONObject billModel = null;
	ArrayList PRODUCTID = new ArrayList();
	ArrayList<Qbq3Form> LIST = OUTPRE_DATAS.getData();
	//for (Qbq3Form bean:OUTPRE_DATAS.getData()) {
	for (int i=0;i<LIST.size();i++) {
		Qbq3Form bean = (Qbq3Form)LIST.get(i);
		if (!PRODUCTID.contains(bean.get("PRODUCTID"))) {
			JSONObject json = new JSONObject();
			json.put("PRODUCTID", bean.get("PRODUCTID"));
			json.put("OUT_CODE", bean.get("OUT_CODE"));
			PRODUCTID.add(bean.get("PRODUCTID"));
			if (billModel==null)
				billModel=bean.toJSon();
		}
	}
	if (billModel==null) 
		throw new Warning("billModel lost");
		
	//System.out.println(PRODUCTID.size());
	String temp = PRODUCTID.toString();
	String filterPROD = PRODUCTID.size()>0?temp.substring(1,temp.length()-1):"-1";
		
	Page PRODUCT_DATAS = srvContext.getDBM().findByFilter("T_WM_SDBPRODUCT_COMBO", " and t_1.ID in ("+filterPROD+")", 5000, 1, srvContext); // 行模型：产品
	
	// TODO 从V_WM_STOCK3查库存(产品-仓库-库存查询，映射到pg2中每个bean的PRODUCTID-WAREHOUSE-PRO_KCCOUNT)
	String sql = "select PRODUCTID,WAREHOUSE,sum(PRO_COUNT) from V_WM_STOCK3 where PRODUCTID in ("+filterPROD+") group by PRODUCTID,WAREHOUSE"; // 仓库、产品汇总
	JSONArray KUCUN_JARRAY = srvContext.getDBM().getJSonArray(sql, new IDB2JSon(){ // 固定值模型：库存参考
		public JSONObject rs2json(ResultSet rs) {
			try {
				JSONObject json = new JSONObject();
				json.put("PRODUCTID", rs.getString(1));
				json.put("WAREHOUSE", rs.getString(2));
				json.put("PRO_KCCOUNT", rs.getString(3));
				//json.put("PRO_KCCOUNT", "100"); // NOTICE 测试 
				//json.put(rs.getString(1)+":"+rs.getString(2), rs.getString(3));
				return json;
			}catch(Throwable e){
				throw new RuntimeException(e);
			}
		}
	});
	// NOTICE 测试 
	/*
	JSONObject json = new JSONObject();
	json.put("PRODUCTID", "1886");
	json.put("WAREHOUSE", "201");
	json.put("PRO_KCCOUNT", "500");
	json.put("PRO_COUNT", "0");
	KUCUN_JARRAY.add(json);
	json.put("PRODUCTID", "1886");
	json.put("WAREHOUSE", "405");
	json.put("PRO_KCCOUNT", "300");
	json.put("PRO_COUNT", "0");
	KUCUN_JARRAY.add(json);
	*/
	//json.put("PRODUCTID", "1886");
	//json.put("WAREHOUSE", "ce3458ed-7a0a-489a-9a94-0f0ad9d5144e");
	//json.put("PRO_KCCOUNT", "100");
	//json.put("PRO_COUNT", "0");
	//KUCUN_JARRAY.add(json);
	/*
	ORDER_ID="20130104178141";
	*/
	out.clear();
	//out.println("应出库明细："+OUTPRE_DATAS.getSize());
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
	<style>
		td {
			border:1px solid #000000;
			text-align:center;
			height:30px;
			margin:0px;
			padding:0px;
		}
		.td0 {
			width:150px;
			font-weight:bold;
		}
		.td1 {
			width:50px;
			font-weight:bold;
		}
		.td2 {
			width:150px;
			font-weight:bold;
		}
		.tdl {
			color:green;
		}
	</style>
</head>
<body>
	<table id="grid0">
		<tr rowspan=3>
			<td class="td0" rowspan=3><!--<button onclick="GRID0.addRow()">+</button>-->产品名</td>
			<td class="td1" rowspan=3>库存数</td>
			<td class="td1" rowspan=3>应出数</td>
			<td class="td1" rowspan=3>分配数</td>
			<td class="td1" rowspan=3>实出数</td>
			<%int CKSIZE = CKLIST.size();
			for(int i=0;i<CKSIZE;i++){%>
				<td class="td2" colspan=3 >子订单 <%=(i+1)%></td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<CKSIZE;i++){
				Qbq3Form frm=(Qbq3Form)CKLIST.get(i);%>
				<td class="td2" colspan=3 >仓库:<%=frm.get("NAME")+":"+frm.get("ID")%></td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<CKSIZE;i++){%>
				<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>
			<%}%>
		</tr>
	</table>
	<div id="WHOWNER_Fld" style="display:none;"></div>
	<script>
	//new Ext.data.ArrayStore(DATA0, {idProperty:'ITEM_ID'});
	(function(){
		Ext.BLANK_IMAGE_URL=(Ext.isIE6 || Ext.isIE7 || Ext.isAir)?'comResource/js/ext/resources/images/default/s.gif':'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',Hy.upload_service='/haiyan',Hy.language='lang-zh_CN_GB2312',Hy.helper='crm',Hy.skin='xtheme-gray', __currDateTime='2013-07-27 22:41:55';
	})();
	var STORE = new Ext.data.JsonStore({
		autoDestroy: true,
		tableName: 'T_WM_OUTPRE2',
		getId:function() {return 'T_WM_OUTPRE2';},
		fields:[ 
		   'ITEM_ID' 
		  ,'__operation' 
		  ,'__flag' 
		  ,'ORDER_ID' 
		  ,'OUT_CODE'
		  ,'__PRODUCTID__WMCODE' 
		  ,'PRODUCTID' 
		  ,'PRODUCT_ID' 
		  ,'NAME' 
		  ,'WMCODE' 
		  ,'SUPP_CODE' 
		  ,'PRE' 
		  ,'OUT_COUNT' // 订单数
		  ,'OUT_RCOUNT' // 实际数
		  ,'OUT_PCOUNT' // 分配数
		  ,'PRO_COUNT' // 数量
		  ,'IN_PRICE' 
		  ,'OUT_PRICE' 
		  ,'OUT_ALL_PRICE' 
		  ,'PACKING_NUM' 
		  , { name: 'CAPACITYNUM', type: 'float' } 
		  ,'SUPPLIER' 
		  ,'WEIGHTNUM' 
		  ,'WAREHOUSE' 
		  , { name: 'All_CAPACITYNUM', type: 'float' } 
		  , { name: 'All_WEIGHTNUM', type: 'float' } 
		  ,'REMAINDER_NUM' 
		  ,'LOGISTICS_NAME' 
		  ,'LOGISTICS_CODE' 
		  ,'LOGISTICS_MEMO' 
		  ,'WFBILLID' 
		  ,'BILL_STATUS' 
		  ,'HYVERSION' 
		  ,'HYFORMKEY' 
		]
	});
	function getStore() {
		STORE.removeAll();
		STORE.loadData(GRID0.dm);
		return STORE;
	}
	var GRID0 = {
		FIXROW:3 // 固定行
		,FIXCOL:5 // 固定列
		,ORDER_ID:'<%=ORDER_ID%>'
		,vm:<%=KUCUN_JARRAY.toString()%> // 固定值模型 V_WM_STOCK3(库存)
		,cm:<%=WAREHOUSE_DATAS.toJSon()%> // 列模型 T_DIC_WAREHOUSE(仓库)
		,rm:<%=PRODUCT_DATAS.toJSon()%> // 行模型 T_WM_SDBPRODUCT(效率处理，仅限当前出库单的产品)
		,dm:<%=OUTPRE_DATAS.toJSon()%> // 编辑值模型 T_WM_OUTPRE2(出库明细)
		,bm:<%=billModel==null?"{}":billModel.toString()%> // record model(表单字段模板)
		,hasData:{} // 判断出库明细是否存在，如果存在在判断是否已经做过出库分配
		,mapData:{} // 数据索引
		//,mapRow:{} // 行索引
		//,mapCol:{} // 列索引
		,sumRow:{} // 行汇总
		,sumCol:{} // 列汇总
		//,store:STORE
		,createRecord:function(PID,WID,rowIndex,colIndex,val) {
			/*
			{
				"__PRODUCTID__WMCODE":rm[rowIndex]['SDB_NAME'],
				"PRODUCT_ID":rm[rowIndex]['PRODUCT_ID'],
				"SUPPLIER":"301",
				"OUT_RCOUNT":"10",
				"AUDIT":"",
				"HYVERSION":"13",
				"BILL_STATUS":"",
				"ITEM_ID":"CKItem_1357",
				"IN_PRICE":"110.000",
				"SUPP_CODE":"",
				"WFBILLID":"",
				"PRO_COUNT":"0",
				"All_WEIGHTNUM":"0.00",
				"OUT_COUNT":"10","PRE":"",
				"WEIGHTNUM":"0.322",
				"WAREHOUSE":"ce3458ed-7a0a-489a-9a94-0f0ad9d5144e",
				"CAPACITYNUM":"0.056",
				"LOGISTICS_MEMO":"",
				"PACKING_NUM":"40",
				"REMAINDER_NUM":"0",
				"LOGISTICS_CODE":"","NAME":"","__BILL_STATUS__Option__":"","OUT_PRICE":"0.000",
				"All_CAPACITYNUM":"0.000",
				"ITEM_ID_PK":"0",
				"PRODUCTID":"1662",
				"LOGISTICS_NAME":"",
				"WMCODE":"018-002-07",
				"ORDER_ID":"CK_3390",
				"OUT_CODE":"CK_3390:018-002-07(Window 可吸附窗式太阳能充电器)",
				"OUT_ALL_PRICE":"0.000","UNAUDIT":""
			}
			*/
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, bm=this.bm;
			var record = Ext.apply(bm, {
				__operation:'NEW',
				__flag:'insert',
				__PRODUCTID__WMCODE: '',
				ORDER_ID:this.ORDER_ID,
				ITEM_ID_PK:'',
				ITEM_ID:'',
				PRODUCTID:rm[rowIndex]['ID'],
				PRODUCT_ID:rm[rowIndex]['SDB_PRODUCT_ID'],
				WAREHOUSE:WID,
				NAME:rm[rowIndex]['SDB_NAME'],
				PRO_COUNT:val,
				OUT_PCOUNT:val,
				OUT_COUNT:this.sumCol[rowIndex]['OUT_COUNT'], // 应出数
				OUT_RCOUNT:0,
				OUT_CODE:this.ORDER_ID+':'+rm[rowIndex]['SDB_NAME'],
				WMCODE:'',
				WEIGHTNUM:0,
				All_WEIGHTNUM:0,
				CAPACITYNUM:0,
				ALL_CAPACITYNUM:0,
				PACKING_NUM:0,
				REMAINDER_NUM:0,
				OUT_PRICE:0,
				OUT_ALL_PRICE:0,
				LOGISTICS_MEMO:'',
				LOGISTICS_CODE:'',
				UNAUDIT:'',
				BILL_STATUS:'',
				HYVERSION:''
			});
			return record;
		}
		,change:function(PID,WID,rowIndex,colIndex,val) {
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm;
			//this.rm[rowIndex]['__flag']='update';
			//this.mapData[PID]={};
			//this.sumCol[rowIndex]['OUT_PCOUNT']=val; // 分配数量
			// NOTICE 如果vm中没有分配记录需要新增一条
			//this.mapData[PID][WID]['ROWINDEX']==rowIndex?
			var rowIndex2=this.mapData[PID][WID]['ROWINDEX']; // 对应的dm中的数据行
			if (Hy.isEmpty(rowIndex2)) { // 新增数据
				var record = this.createRecord(PID,WID,rowIndex,colIndex,val);
				dm.push(record);
				this.mapData[PID][WID]['ROWINDEX']=dm.length-1;
				rowIndex2=this.mapData[PID][WID]['ROWINDEX'];
				
				this.mapData[PID][WID]['FLAG']=null;
				delete this.mapData[PID][WID]['FLAG'];
			} 
			//if (Hy.isEmpty(dm[rowIndex2]['HYVERSION']))
			//	dm[rowIndex2]['HYVERSION']=1;
			dm[rowIndex2]['WAREHOUSE']=WID; // 设置数据行
			dm[rowIndex2]['OUT_PCOUNT']=val; // 设置数据行
			this.mapData[PID][WID]['OUT_PCOUNT']=val; // 设置映射数据
			
			this.calRowModel(rowIndex); 
			this.getDataCellEl(rowIndex, 3).innerHTML=this.sumCol[rowIndex]['OUT_PCOUNT']; // 分配数
		}
		,initTemplate:function(){
			var h;
			h='<tr rowIndex={0} rowID={1} >';
			h+='<td nowrap><font color="blue">{2}</font></td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td>';
			for (var i=0;i<this.cm.length*4;i+=4) { // colID={'+(7+i)+'}
				h+='<td>{'+(8+i)+'}</td><td>{'+(9+i)+'}</td><td>{'+(10+i)+'}</td>';
			}
			h+='</tr>';
			this.template = new Ext.Template(h);
			this.template.compile();
		}
		,initMap:function(PID, WID) {
			if (!this.mapData[PID])
				this.mapData[PID]={};
			if (!this.mapData[PID][WID])
				this.mapData[PID][WID]={};
		}
		// OUT_PCOUNT:分配数 PRO_KCCOUNT:库存数 OUT_COUNT:应出库 OUT_RCOUNT:实出库
		,calRowModel:function(rowIndex){ // 单行产品
			if (!this.sumCol[rowIndex]) // 列汇总
				this.sumCol[rowIndex]={};
			this.sumCol[rowIndex]['OUT_COUNT']=0;
			this.sumCol[rowIndex]['OUT_PCOUNT']=0;
			this.sumCol[rowIndex]['PRO_KCCOUNT']=0;
			this.sumCol[rowIndex]['OUT_RCOUNT']=0;
			var rm=this.rm, cm=this.cm, row=this.rm[rowIndex], dm=this.dm, vm=this.vm;
			//row['PRO_KCCOUNT'] = row['PRO_KCCOUNT']=='-1'?0:row['PRO_KCCOUNT']*1; // TODO 库存V_WM_STOCK3数量没有查出来
			cm.each(function(col, colIndex) { // 遍历仓库（列维度） T_DIC_WAREHOUSE
				var PID = row['ID'], WID = col['ID'];
				this.initMap(PID, WID);
				this.sumCol[rowIndex]['OUT_COUNT']=(this.mapData[PID]['OUT_COUNT']||0)*1; // 应出数
				this.sumCol[rowIndex]['OUT_PCOUNT']+=(this.mapData[PID][WID]['OUT_PCOUNT']||0)*1; // 分配数
				this.sumCol[rowIndex]['PRO_KCCOUNT']+=(this.mapData[PID][WID]['PRO_KCCOUNT']||0)*1; // 库存数
				this.sumCol[rowIndex]['OUT_RCOUNT']+=(this.mapData[PID][WID]['OUT_RCOUNT']||0)*1; // 实出数
			}, this);
		}
		,calModel:function(){ // 产品遍历
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm;
			vm.each(function(kuc, index) { // 库存遍历 V_WM_STOCK3
				var PID = kuc.PRODUCTID, WID = kuc.WAREHOUSE;
				this.initMap(PID, WID);
				this.mapData[PID][WID]['PRO_KCCOUNT']=kuc.PRO_KCCOUNT;
			}, this);
			dm.each(function(pre, index) { // 应出库单遍历 OUTPRE_DATAS
				var PID = pre.PRODUCTID, WID = pre.WAREHOUSE;
				if (WID) { // 已经分配过仓库
					this.initMap(PID, WID);
					this.mapData[PID]['OUT_COUNT']=pre.OUT_COUNT||0; // 应出数
					this.mapData[PID][WID]['OUT_PCOUNT']=pre.OUT_PCOUNT||0; // 分配数
					this.mapData[PID][WID]['OUT_RCOUNT']=pre.OUT_RCOUNT||0; // 实出数
					this.mapData[PID][WID]['ROWINDEX']=index;
					//this.mapData[PID][WID]['FLAG']='UPDATE';
				} 
			}, this);
			dm.each(function(pre, index) { // 应出库单遍历 OUTPRE_DATAS
				var PID = pre.PRODUCTID, WID = pre.WAREHOUSE;
				if (!WID) { // 没分配过库存:2种情况，有记录和新增记录
					vm.each(function(kuc, index2) { // 库存遍历 V_WM_STOCK3
						var PID2 = kuc.PRODUCTID, WID2 = kuc.WAREHOUSE;
						var YKC = this.mapData[PID][WID2] && this.mapData[PID][WID2]['PRO_KCCOUNT']*1>0;
						if (PID!=PID2 || !YKC)
							return;
						this.mapData[PID]['OUT_COUNT']=pre.OUT_COUNT||0; // 应出数
						if (Hy.isEmpty(this.mapData[PID][WID2]['ROWINDEX']) && this.hasData[index]!=1) {
							this.mapData[PID][WID2]['OUT_PCOUNT']=pre.OUT_PCOUNT||0; // 分配数
							this.mapData[PID][WID2]['OUT_RCOUNT']=pre.OUT_RCOUNT||0; // 实出数
							
							this.mapData[PID][WID2]['ROWINDEX']=index; // 记录行
							this.hasData[index]=1; // 未分配过仓库的已存在的数据明细
						} else { // 不存在的记录行
							this.mapData[PID][WID2]['OUT_PCOUNT']=0; // 分配数
							this.mapData[PID][WID2]['OUT_RCOUNT']=0; // 实出数
						}
					}, this);
				}
			}, this);
			rm.each(function(row, index) { // 行维度 T_WM_SDBPRODUCT
				this.calRowModel(index);
			}, this);
		}
		,onRender:function(){
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm;
			rm.each(function(row, rowIndex) { // 行维度
				var PID = row['ID'];
				//this.mapRow[PID]=rowIndex;
				var args = [
					this.getRowCount() // 行号 attri
					, row['PRODUCTID'] // 产品ID attri
					//, row['__PRODUCTID__NAME']+'--'+row['PRODUCTID']+'--'+row['ORDER_ID'] // 产品名称
					, row['SDB_NAME'] // 出库产品编号
					, this.sumCol[rowIndex]['PRO_KCCOUNT'] // 库存数
					, this.sumCol[rowIndex]['OUT_COUNT'] // 应出数
					, this.sumCol[rowIndex]['OUT_PCOUNT'] // 分配数
					, this.sumCol[rowIndex]['OUT_RCOUNT'] // 实际出库数
				];
				cm.each(function(col, colIndex) { // 遍历仓库（列维度）
					var WID = col['ID'];
					var T = this.mapData[PID][WID]; // 查应出库信息
					//if (T && T['PRO_KCCOUNT']*1>0 && T['OUT_RCOUNT']*1<T['OUT_COUNT']*1) { // 有库存 并且 应出>实出 才能出库
					if (T && T['PRO_KCCOUNT']*1>0) { // NOTICE 测试 
						args.push(
							WID
							, (T['PRO_KCCOUNT']||0) 
							, '<input class="td1" onchange="GRID0.change(\''+PID+'\',\''+WID+'\','+rowIndex+','+colIndex+',this.value)" value="'+(T['OUT_PCOUNT']||0)+'"></input>'
							, (T['OUT_RCOUNT']||0)
						);
					} else
						args.push(WID, 0, 0, 0);
				}, this);
				this.template.append(this.getGridEl(), args, true);
			}, this);
		}
		,render:function(){
			this.initTemplate();
			this.calModel();
			this.onRender();
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
		,addRow:function(){
			var args = [this.getRowCount()];
			this.template.append(this.getGridEl(), args, true);
		}
	};
	GRID0.render();
	<%
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
	%>
	</script>
</body>
</html>
<%
} catch(Throwable ex) {
	DebugUtil.error("test.jsp", ex);
	out.clear();
    out.println(Warning.getClientErr(ex.getMessage()));
} finally {
	if (srvContext!=null)
		srvContext.close();
}
%>