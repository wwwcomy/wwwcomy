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
	qfrm.set("USEDSTATUS", "1"); // 列模型：一共有多少子订单(distinct)
	Page SUBORDERS_COLS = srvContext.getDBM().findByForm("T_DIC_WAREHOUSE_COMBO", qfrm, 100, 1, srvContext); // 列模型：子订单-仓库
	
	qfrm = new MapForm();
	qfrm.set("ORDER_ID", ORDER_ID);
	Page SUBORDERS_DTLS = srvContext.getDBM().findByForm("T_WM_OUTPREDTL", qfrm, 5000, 1, srvContext); // 编辑模型：子订单明细
	
	qfrm = new MapForm();
	qfrm.set("ORDER_ID", ORDER_ID);
	Page SUBORDERS_PRE = srvContext.getDBM().findByForm("T_WM_OUTPRE2", qfrm, 5000, 1, srvContext); // 编辑模型：应出库明细
	ArrayList PRODUCTID = new ArrayList();
	ArrayList<Qbq3Form> list = SUBORDERS_PRE.getData();
	for (int i=0;i<list.size();i++) {
		Qbq3Form bean = (Qbq3Form)list.get(i);
		if (!PRODUCTID.contains(bean.get("PRODUCTID"))) {
			JSONObject json = new JSONObject();
			json.put("PRODUCTID", bean.get("PRODUCTID"));
			json.put("OUT_CODE", bean.get("OUT_CODE"));
			PRODUCTID.add(bean.get("PRODUCTID"));
		}
	}
	out.print("PRODUCTID:"+PRODUCTID);
	String temp = PRODUCTID.toString();
	String filterPROD = PRODUCTID.size()>0?temp.substring(1,temp.length()-1):"-1";
		
	Page PRODUCT_ROWS = srvContext.getDBM().findByFilter("T_WM_SDBPRODUCT_COMBO", " and t_1.ID in ("+filterPROD+")", 5000, 1, srvContext); // 行模型：产品
	
	// TODO 从V_WM_STOCK3查库存(产品-仓库-库存查询，映射到pg2中每个bean的PRODUCTID-WAREHOUSE-PRO_KCCOUNT)
	String sql = "select PRODUCTID,WAREHOUSE,sum(PRO_COUNT) from V_WM_STOCK3 where PRODUCTID in ("+filterPROD+") group by PRODUCTID,WAREHOUSE"; // 仓库、产品汇总
	JSONArray KUCUN_JARRAY = srvContext.getDBM().getJSonArray(sql, new IDB2JSon(){ // 固定值模型：库存参考
		public JSONObject rs2json(ResultSet rs) {
			try {
				JSONObject json = new JSONObject();
				json.put("PRODUCTID", rs.getString(1));
				json.put("WAREHOUSE", rs.getString(2));
				json.put("PRO_KCCOUNT", rs.getString(3));
				return json;
			}catch(Throwable e){
				throw new RuntimeException(e);
			}
		}
	});
	// -------------------------------- NOTICE 测试数据 -------------------------------- // 
	Qbq3Form dfrm = null;
	
	ArrayList<Qbq3Form> SUBORDERS=new ArrayList<Qbq3Form>();
	SUBORDERS_COLS.setData(SUBORDERS);
	dfrm = new MapForm();
	dfrm.set("ORDER_ID",ORDER_ID);
	dfrm.set("SUBORDERID","001");
	dfrm.set("WAREHOUSE", "201");
	dfrm.set("__WAREHOUSE_NAME", "金桥");
	SUBORDERS.add(dfrm);
	dfrm = new MapForm();
	dfrm.set("ORDER_ID",ORDER_ID);
	dfrm.set("SUBORDERID","002");
	dfrm.set("WAREHOUSE", "405");
	dfrm.set("__WAREHOUSE_NAME", "浦东");
	SUBORDERS.add(dfrm);
	
	/*
	KUCUN_JARRAY = new JSONArray(); 
	JSONObject json = new JSONObject();
	json.put("WAREHOUSE", "201");
	json.put("PRODUCTID", "1660");
	json.put("PRO_KCCOUNT", "500"); // 库存
	KUCUN_JARRAY.add(json);
	json.put("WAREHOUSE", "405");
	json.put("PRODUCTID", "1660");
	json.put("PRO_KCCOUNT", "300"); // 库存
	KUCUN_JARRAY.add(json);
	json.put("WAREHOUSE", "405");
	json.put("PRODUCTID", "3806");
	json.put("PRO_KCCOUNT", "100"); // 库存
	KUCUN_JARRAY.add(json);
	
	ArrayList<Qbq3Form> SUBORDER_DATAS=new ArrayList<Qbq3Form>();
	SUBORDERS_DTLS.setData(SUBORDER_DATAS);
	dfrm = new MapForm();
	dfrm.set("ORDER_ID",ORDER_ID);
	dfrm.set("SUBORDERID","001");
	dfrm.set("WAREHOUSE", "201");
	dfrm.set("PRODUCTID", "1660");
	dfrm.set("OUT_COUNT", "100"); // 应出
	dfrm.set("OUT_PCOUNT", "0"); // 分配
	dfrm.set("OUT_RCOUNT", "0"); // 实际
	SUBORDER_DATAS.add(dfrm);
	dfrm = new MapForm();
	dfrm.set("ORDER_ID",ORDER_ID);
	dfrm.set("SUBORDERID","002");
	dfrm.set("WAREHOUSE", "405");
	dfrm.set("PRODUCTID", "1660");
	dfrm.set("OUT_COUNT", "100"); // 应出
	dfrm.set("OUT_PCOUNT", "0"); // 分配
	dfrm.set("OUT_RCOUNT", "0"); // 实际
	SUBORDER_DATAS.add(dfrm);
	dfrm = new MapForm();
	dfrm.set("ORDER_ID",ORDER_ID);
	dfrm.set("SUBORDERID","002");
	dfrm.set("WAREHOUSE", "405");
	dfrm.set("PRODUCTID", "3806");
	dfrm.set("OUT_COUNT", "120"); // 应出
	dfrm.set("OUT_PCOUNT", "0"); // 分配
	dfrm.set("OUT_RCOUNT", "0"); // 实际
	SUBORDER_DATAS.add(dfrm);
	*/
	//out.clear();
	//out.println("子订单明细："+SUBORDERS_DTLS.getSize());
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
	
	<div id="WHOWNER_Fld" style="display:none;"></div>
	<table id="grid0">
		<tr rowspan=3>
			<td class="td0" rowspan=3>产品名<button onclick="javascript:GRID0.addColumn('ce3458ed-7a0a-489a-9a94-0f0ad9d5144e','金桥');GRID0.refresh();" >+</button></td>
			<td class="td1" rowspan=3>库存数</td>
			<td class="td1" rowspan=3>应出数</td>
			<td class="td1" rowspan=3>分配数</td>
			<td class="td1" rowspan=3>实出数</td>
			<%int size = SUBORDERS_COLS.size();%>
			<%for(int i=0;i<size;i++){
				Qbq3Form frm=(Qbq3Form)SUBORDERS_COLS.get(i);%>
				<td class="td2" colspan=3 >子订单:<%=(i+1)%>(<%=frm.get("SUBORDERID")%>)</td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<size;i++){
				Qbq3Form frm=(Qbq3Form)SUBORDERS_COLS.get(i);%>
				<td class="td2" colspan=3 >仓库:<%=frm.get("__WAREHOUSE_NAME")%>(<%=frm.get("WAREHOUSE")%>)</td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<size;i++){%>
				<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>
			<%}%>
		</tr>
	</table>
	<script>
	(function(){
		Ext.BLANK_IMAGE_URL=(Ext.isIE6 || Ext.isIE7 || Ext.isAir)?'comResource/js/ext/resources/images/default/s.gif':'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',Hy.upload_service='/haiyan',Hy.language='lang-zh_CN_GB2312',Hy.helper='crm',Hy.skin='xtheme-gray', __currDateTime='2013-07-27 22:41:55';
	})();
	var STORE = new Ext.data.JsonStore({
		autoDestroy: true,
		tableName: 'T_WM_OUTPREDTL',
		getId:function() {return 'T_WM_OUTPREDTL';},
		fields:[ 
		   '__operation' 
		  ,'__flag' 
		  ,'BILL_STATUS' 
		  ,'HYVERSION' 
		  ,'HYFORMKEY' 
		  ,'ORDER_ID' // 订单号
		  ,'SUBORDERID' // 子订单号
		  ,'PRODUCTID' // 产品ID
		  ,'PRODUCT_ID' // 产品ID2
		  ,'OUT_COUNT' // 订单数
		  ,'OUT_RCOUNT' // 实际数
		  ,'OUT_PCOUNT' // 分配数
		  ,'PRO_COUNT' // 数量(预出库数)
		  ,'WAREHOUSE' // 仓库ID
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
		,rm:<%=PRODUCT_ROWS.toJSon()%> // 行模型 T_WM_SDBPRODUCT(效率处理，仅限当前出库单的产品)
		,cm:<%=SUBORDERS_COLS.toJSon()%> // 列模型 T_DIC_WAREHOUSE(仓库)
		,dm:<%=SUBORDERS_DTLS.toJSon()%> // 编辑值模型 T_WM_OUTPRE2(子订单明细)
		,ym:<%=SUBORDERS_PRE.toJSon()%> // 应出库模型
		,hasData:{} // 判断出库明细是否存在，如果存在在判断是否已经做过出库分配
		,mapData:{} // 数据索引
		,mapDataKC:{} // 库存索引
		,mapDataYC:{} // 应出索引
		//,mapRow:{} // 行索引
		//,mapCol:{} // 列索引
		,sumRow:{} // 行汇总
		,sumCol:{} // 列汇总
		//,store:STORE
		,addColumn:function(WID, WNAME){
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, bm=this.bm;
			var OID='00'+(cm.length+1);
			//dfrm.set("ORDER_ID","20130104178141").set("SUBORDERID","1001").set("WAREHOUSE", "201").set("__WAREHOUSE_NAME", "金桥");
			cm.push({
				ORDER_ID:this.ORDER_ID
				,SUBORDERID:OID
				,WAREHOUSE:WID
				,__WAREHOUSE_NAME:WNAME
			});
			//dfrm.set("ORDER_ID",ORDER_ID);
			//dfrm.set("SUBORDERID","1001");
			//dfrm.set("WAREHOUSE", "201");
			//dfrm.set("PRODUCTID", "1886");
			//dfrm.set("OUT_PCOUNT", "0"); // 分配
			//dfrm.set("OUT_RCOUNT", "0"); // 实际
			//dfrm.set("OUT_COUNT", "100"); // 应出
			rm.each(function(row, index) { // 行维度 T_WM_SDBPRODUCT
				var YCK=this.getDataCellEl(index, 2).innerHTML*1;
				//var PID = row['ID'], YCK=this.getDataCellEl(index, 2).innerHTML*1; // 不管有没有应出库都要生成否则表格乱了
				//if (YCK==0) // 没有应出库
				//	return;
				dm.push({
					ORDER_ID:this.ORDER_ID
					,SUBORDERID:OID
					,WAREHOUSE:WID
					,PRODUCTID:row['ID']
					,OUT_PCOUNT:0
			        ,OUT_RCOUNT:0
			        ,OUT_COUNT:YCK
				});
			}, this);
			var dom=this.getGridEl().dom;
			dom.children[0].children[0].innerHTML+='<td class="td2" colspan=3>子订单:'+cm.length+'('+OID+')</td>';
			dom.children[0].children[1].innerHTML+='<td class="td2" colspan=3>仓库:'+WNAME+'('+WID+')</td>';
			dom.children[0].children[2].innerHTML+='<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>';
		}
		,change:function(PID,OID,rowIndex,colIndex,val) {
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, dataIndex=this.mapData[PID][OID]['ROWINDEX'];
			
			dm[dataIndex]['OUT_PCOUNT']=val; // 设置数据行的分配数
			this.mapData[PID][OID]['OUT_PCOUNT']=val; // 设置映射数据
			
			this.calRowModel(rowIndex); // 物理行
			this.getDataCellEl(rowIndex, 3).innerHTML=this.sumRow[rowIndex]['OUT_PCOUNT']; // 分配数合计
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
		,initMapKC:function(PID, WID) {
			if (!this.mapDataKC[PID])
				this.mapDataKC[PID]={};
			if (!this.mapDataKC[PID][WID])
				this.mapDataKC[PID][WID]=0;
		}
		,initMap:function(PID, OID) {
			if (!this.mapData[PID])
				this.mapData[PID]={};
			if (!this.mapData[PID][OID])
				this.mapData[PID][OID]={};
		}
		// OUT_PCOUNT:分配数 PRO_KCCOUNT:库存数 OUT_COUNT:应出库 OUT_RCOUNT:实出库
		,calRowModel:function(rowIndex){ // 单行产品
			if (!this.sumRow[rowIndex]) // 列汇总
				this.sumRow[rowIndex]={};
			this.sumRow[rowIndex]['OUT_PCOUNT']=0;
			this.sumRow[rowIndex]['OUT_RCOUNT']=0;
			var rm=this.rm, cm=this.cm, row=this.rm[rowIndex], dm=this.dm, vm=this.vm;
			//row['PRO_KCCOUNT'] = row['PRO_KCCOUNT']=='-1'?0:row['PRO_KCCOUNT']*1; // TODO 库存V_WM_STOCK3数量没有查出来
			for (var colIndex=0;colIndex<cm.length;colIndex++) {
				var PID = rm[rowIndex]['ID'], OID = cm[colIndex]['SUBORDERID'];
				this.initMap(PID, OID);
				this.sumRow[rowIndex]['OUT_PCOUNT']+=(this.mapData[PID][OID]['OUT_PCOUNT']||0)*1; // 分配数
				this.sumRow[rowIndex]['OUT_RCOUNT']+=(this.mapData[PID][OID]['OUT_RCOUNT']||0)*1; // 实出数
			}
		}
		,calModel:function(){ // 产品遍历
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, ym=this.ym;
			ym.each(function(yc, index){
				var PID = yc.PRODUCTID, COUNT = yc.OUT_COUNT;
				this.mapDataYC[PID]=COUNT;
			}, this);
			vm.each(function(kuc, index) { // 库存遍历 V_WM_STOCK3
				var PID = kuc.PRODUCTID, WID = kuc.WAREHOUSE;
				this.initMapKC(PID, WID);
				this.mapDataKC[PID][WID]=kuc.PRO_KCCOUNT;
			}, this);
			dm.each(function(sub, index) { // 子出库单遍历 SUBORDERS_DTLS
				var PID = sub.PRODUCTID, WID = sub.WAREHOUSE, OID = sub.SUBORDERID;
				if (WID) { // 已经分配过仓库（子订单）(NOTICE 每次新增子订单必须选择仓库)
					this.initMapKC(PID, WID);
					this.initMap(PID, OID);
					this.mapData[PID]['OUT_COUNT']=this.mapDataYC[PID]||0; // 应出数
					this.mapData[PID][OID]['OUT_PCOUNT']=sub.OUT_PCOUNT||0; // 分配数
					this.mapData[PID][OID]['OUT_RCOUNT']=sub.OUT_RCOUNT||0; // 实出数
					this.mapData[PID][OID]['ROWINDEX']=index; // dm行索引
					this.mapData[PID][OID]['WAREHOUSE']=WID;
				} 
			}, this);
			rm.each(function(row, index) { // 行维度 T_WM_SDBPRODUCT
				var PID=row['ID'], vm=this.vm;
				if (!this.sumRow[index]) // 列汇总
					this.sumRow[index]={};
				this.sumRow[index]['PRO_KCCOUNT']=0;
				for (var index2=0;index2<vm.length;index2++) {
					var WID = vm[index2]['WAREHOUSE'];
					if (PID!=vm[index2]['PRODUCTID'])
						continue;
					this.initMapKC(PID, WID);
					this.sumRow[index]['PRO_KCCOUNT']+=(this.mapDataKC[PID][WID]||0)*1; // 库存数
				}
				if (!this.mapData[PID])
					this.mapData[PID]={};
				//this.sumRow[index]['OUT_COUNT']=(this.mapData[PID]['OUT_COUNT']||0)*1; // 应出数
				this.calRowModel(index);
			}, this);
		}
		,onRender:function(){
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm;
			rm.each(function(row, rowIndex) { // 行维度
				var PID = row['ID'], PNAME = row['SDB_NAME'];
				var args = [
					this.getRowCount() // 行号 attri
					, PID // 产品ID attri //, row['__PRODUCTID__NAME']+'--'+row['PRODUCTID']+'--'+row['ORDER_ID'] // 产品名称
					, PNAME+':'+PID // 出库产品编号
					, this.sumRow[rowIndex]['PRO_KCCOUNT'] // 库存数
					, this.mapDataYC[PID]||0//this.sumRow[rowIndex]['OUT_COUNT'] // 应出数
					, this.sumRow[rowIndex]['OUT_PCOUNT'] // 分配数
					, this.sumRow[rowIndex]['OUT_RCOUNT'] // 实际出库数
				];
				for (var colIndex=0;colIndex<cm.length;colIndex++) { // 遍历子订单（列维度）
					var OID = cm[colIndex]['SUBORDERID'], t = this.mapData[PID][OID]; // 查应出库信息 某子订单中的某产品
					//if (t && t['PRO_KCCOUNT']*1>0 && t['OUT_RCOUNT']*1<t['OUT_COUNT']*1) { // 有库存 并且 应出>实出 才能出库
					if (t && t['WAREHOUSE'] && this.mapDataKC[PID][t['WAREHOUSE']]*1>0 && this.mapData[PID]['OUT_COUNT']*1>0) { // NOTICE 测试 
						args.push(
							OID
							, (this.mapDataKC[PID][t['WAREHOUSE']]||0) // 库存
							, '<input class="td1" onchange="GRID0.change(\''+PID+'\',\''+OID+'\','+rowIndex+','+colIndex+',this.value)" value="'+(t['OUT_PCOUNT']||0)+'"></input>' // 分配
							, (t['OUT_RCOUNT']||0) // 实出
						);
					} else
						args.push(
							OID
							, (this.mapDataKC[PID][t['WAREHOUSE']]||0) // 库存
							, t['OUT_PCOUNT']||0 // 分配
							, 0 // 实出
						);
				}
				this.template.append(this.getGridEl(), args, true);
			}, this);
		}
		,render:function() {
			this.initTemplate();
			this.calModel();
			this.onRender();
		}
		,refresh:function() {
			var dom=this.getGridEl().dom, length=dom.children.length;
			for (var i=length-1;i>=1;i--)
				//dom.deleteRow(i);
				dom.children[i].remove();
			this.render();
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
	</script>
</body>
</html>
<%
} catch(Throwable ex) {
	DebugUtil.error("test_new.jsp", ex);
	out.clear();
    out.println(Warning.getClientErr(ex.getMessage()));
} finally {
	if (srvContext!=null)
		srvContext.close();
}
%>