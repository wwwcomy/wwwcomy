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
	
	qfrm = new MapForm();
	qfrm.set("ORDER_ID", ORDER_ID);
	Page SUBORDERS_SAVED = srvContext.getDBM().findByForm("T_WM_OUTPREDTL", qfrm, 5000, 1, srvContext); 
	int savedSize = SUBORDERS_SAVED.size();
	HashMap map = new HashMap();
	for(int i=0;i<savedSize;i++){
		Qbq3Form frm=(Qbq3Form)SUBORDERS_SAVED.get(i);
		String key = frm.get("WAREHOUSE")+"|"+frm.get("SUBORDERID");
		dfrm = new MapForm();
		dfrm.set("ORDER_ID",ORDER_ID);
		dfrm.set("SUBORDERID",frm.get("SUBORDERID"));
		dfrm.set("BILL_STATUS1",frm.get("BILL_STATUS1"));
		dfrm.set("WAREHOUSE", frm.get("WAREHOUSE"));

		Qbq3Form warehouseForm=srvContext.getDBM().findByPK("T_DIC_WAREHOUSE", frm.get("WAREHOUSE"),srvContext);
		dfrm.set("NAME", warehouseForm.get("NAME"));
		
		// System.out.println("******"+ warehouseForm.get("NAME"));
		map.put(key, dfrm);
	}

	SUBORDERS_COLS.setData(new ArrayList<Qbq3Form>(map.values()));
	
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
		#grid0 td {
			border:1px solid #000000;
			text-align:center;
			height:30px;
			margin:0px;
			padding:0px;
		}
		#grid0 .td0 {
			width:150px;
			font-weight:bold;
		}
		#grid0 .td1 {
			width:50px;
			font-weight:bold;
		}
		#grid0 .td2 {
			width:150px;
			font-weight:bold;
		}
		#grid0 .tdl {
			color:green;
		}
		.red {
			color : red;
		}
	</style>
</head>
<body>
	
	<div id="WHOWNER_Fld" style="display:none;"></div>
	<table id="grid0">
		<tr rowspan=3>
			<td class="td0" rowspan=3>产品名<button onclick="javascript:GRID0.btnClick();" >+</button></td>
			<td class="td1" rowspan=3>货号</td>
			<td class="td1" rowspan=3>自有货号</td>
			<td class="td1" rowspan=3>库存数</td>
			<td class="td1" rowspan=3>应出数</td>
			<td class="td1" rowspan=3>分配数</td>
			<td class="td1" rowspan=3>实出数</td>
			<%int size = SUBORDERS_COLS.size();%>
			<%for(int i=0;i<size;i++){
				Qbq3Form frm=(Qbq3Form)SUBORDERS_COLS.get(i);%>
				<td class="td2" colspan=3 >子订单:<%=(i+1)%>(<%=StringUtil.isEmpty(frm.get("SUBORDERID"))?("00"+(i+1)):frm.get("SUBORDERID")%>,
				<%=StringUtil.isEmpty(frm.get("BILL_STATUS1"))?"init":frm.get("BILL_STATUS1")%>)</td>
			<%}%>
		</tr>
		<tr>
			<%for(int i=0;i<size;i++){
				Qbq3Form frm=(Qbq3Form)SUBORDERS_COLS.get(i);%>
				<td class="td2" colspan=3 >仓库:<%=frm.get("NAME")%>(<%=frm.get("WAREHOUSE")%>)</td>
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
		  ,'ITEM_ID' // 子订单主键
		  ,'ORDER_ID' // 订单号
		  ,'SUBORDERID' // 子订单号
		  ,'ORDER_ID_SUB' // 订单-子订单号
		  ,'OUT_SUB_CODE' //订单-子订单-商品号用于退货
		  ,'PRODUCTID' // 产品ID
		  ,'PRODUCT_ID' // 产品ID2
		  ,'OUT_COUNT' // 订单数
		  ,'OUT_RCOUNT' // 实际数
		  ,'OUT_PCOUNT' // 分配数
		  ,'PRO_COUNT' // 数量(预出库数)
		  ,'WAREHOUSE' // 仓库ID
		  ,'BILL_STATUS1' // 子订单状态
		  
		  ,'WMCODE' //货号
		  ,'PRO_NAME' //名称
		  ,'SUPP_CODE' //自有货号
		  ,'SUPPLIER' //商品供应商
		  ,'OUT_PRICE' //EDP销售单价
		  ,'IN_PRICE' //进货单价
		  ,'OUT_ALL_PRICE' //EDP销售总价
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
		,dm:<%=SUBORDERS_DTLS.toJSon()%> // 编辑值模型 T_WM_OUTPREDTL(子订单明细)
		,ym:<%=SUBORDERS_PRE.toJSon()%> // 应出库模型
		,hasData:{} // 判断出库明细是否存在，如果存在在判断是否已经做过出库分配
		,mapData:{} // 数据索引
		,mapDataKC:{} // 库存索引
		,mapDataYC:{} // 应出索引
		,sumRow:{} // 行汇总
		,sumCol:{} // 列汇总
		//,store:STORE
		,addColumn:function(WID, WNAME){
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, bm=this.bm;
			var OID='00'+(cm.length+1);
			var BillStatus1 = 'init';
			cm.push({
				ORDER_ID:this.ORDER_ID
				,SUBORDERID:OID
				,WAREHOUSE:WID
				,__WAREHOUSE_NAME:WNAME
				,BILL_STATUS1:BillStatus1
			});
			rm.each(function(row, index) { // 行维度 T_WM_SDBPRODUCT
				var YCK=this.getYCK(index).innerHTML*1,PID=row['ID'],
				supp = row['SUPPLIER'],inPrice=row['IN_PRICE'],outPrice,outAllPrice;
				var i;
				for(i=0;i<this.ym.length;i++){
					var data=this.ym[i];
					if(data['PRODUCTID']==row['ID']){
						outPrice = data['OUT_PRICE'];
						outAllPrice = data['OUT_ALL_PRICE'];
						break;
					}
				}
				dm.push({
					ITEM_ID:''
					,ORDER_ID:this.ORDER_ID
					,SUBORDERID:OID
					,ORDER_ID_SUB:""+this.ORDER_ID+OID
					,OUT_SUB_CODE:""+this.ORDER_ID+OID+PID
					,WAREHOUSE:WID
					,PRODUCTID:PID
					,OUT_PCOUNT:0
			        ,OUT_RCOUNT:0
			        ,OUT_COUNT:YCK
					,BILL_STATUS1:'init'
					,WMCODE:row['CODE'] //货号
					,PRO_NAME:row['NAME'] //名称
					,SUPP_CODE:row['SUPP_CODE'] //自有货号
					,SUPPLIER:supp //商品供应商
					,OUT_PRICE:outPrice //EDP销售单价
					,IN_PRICE:inPrice //进货单价
					,OUT_ALL_PRICE:outAllPrice //EDP销售总价
				});
			}, this);
			var dom=this.getGridEl().dom;
			dom.children[0].children[0].innerHTML+='<td class="td2" colspan=3>子订单:'+cm.length+'('+OID+','+BillStatus1+')</td>';
			dom.children[0].children[1].innerHTML+='<td class="td2" colspan=3>仓库:'+WNAME+'('+WID+')</td>';
			dom.children[0].children[2].innerHTML+='<td class="td1">库存</td><td class="td1">分配</td><td class="td1">实出</td>';
		}
		,change:function(PID,OID,WID,rowIndex,colIndex,that) {
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, dataIndex=this.mapData[PID][OID]['ROWINDEX']>=0?this.mapData[PID][OID]['ROWINDEX']:rowIndex;
			var val = that.value;
			// 增加实出库不超过应出库的判断
			var YCK = parseInt(this.getYCK(rowIndex).innerHTML);
			var SCK = parseInt(this.getSCK(rowIndex).innerHTML);
			var increasement = val - this.mapData[PID][OID]['OUT_PCOUNT'];
			if((YCK-SCK)<increasement){
				// Ext.Msg.alert("警告","应出库数量已经超出预期,请重新输入!");
				Ext.Msg.alert("警告","分配数量已经超出应出库数量");
				Ext.get(this.getSCK(rowIndex)).addClass("red");
				//that.value = this.mapData[PID][OID]['OUT_PCOUNT'];
				//that.focus();
				//return;
			} else {
				Ext.get(this.getSCK(rowIndex)).removeClass("red");
			}
				
			/* if(dm[dataIndex]){
			} else{
				dm.push({
					ITEM_ID:''
					,ORDER_ID:this.ORDER_ID
					,SUBORDERID:OID
					,WAREHOUSE:WID
					,PRODUCTID:PID
					,OUT_PCOUNT:0
			        ,OUT_RCOUNT:0
			        ,OUT_COUNT:0
				});
			} */
			dm[dataIndex]['OUT_PCOUNT']=val; // 设置数据行的分配数
			this.mapData[PID][OID]['OUT_PCOUNT']=val; // 设置映射数据
			
			this.calRowModel(rowIndex); // 物理行
			var totalFPS = this.sumRow[rowIndex]['OUT_PCOUNT'];
			this.getSCK(rowIndex).innerHTML = totalFPS;// 分配数合计
			
			// 修改父窗口SUBGRID中的分配数
			window.parent.changeFPS(PID,totalFPS);
			
		}
		,initTemplate:function(){
			var h;
			h='<tr rowIndex={0} rowID={1} >';
			h+='<td nowrap><font color="blue">{2}</font></td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td>';
			for (var i=0;i<this.cm.length*4;i+=4) { // colID={'+(7+i)+'}
				h+='<td>{'+(10+i)+'}</td><td>{'+(11+i)+'}</td><td>{'+(12+i)+'}</td>';
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
				var PID = rm[rowIndex]['ID'], OID = cm[colIndex]['SUBORDERID']||('00'+(colIndex+1));
				this.initMap(PID, OID);
				this.sumRow[rowIndex]['OUT_PCOUNT']+=(this.mapData[PID][OID]['OUT_PCOUNT']||0)*1; // 分配数
				this.sumRow[rowIndex]['OUT_RCOUNT']+=(this.mapData[PID][OID]['OUT_RCOUNT']||0)*1; // 实出数
			}
		}
		,calModel:function(){ // 产品遍历
			var rm=this.rm, cm=this.cm, dm=this.dm, vm=this.vm, ym=this.ym;
			ym.each(function(yc, index){
				var PID = yc.PRODUCTID, COUNT = yc.PRO_COUNT;// 使用T_WM_OUTPRE中的PRO_COUNT(源于SDB表) 作为应出库数量
				this.mapDataYC[PID]=COUNT;
			}, this);
			vm.each(function(kuc, index) { // 库存遍历 V_WM_STOCK3
				var PID = kuc.PRODUCTID, WID = kuc.WAREHOUSE;
				this.initMapKC(PID, WID);
				this.mapDataKC[PID][WID]=kuc.PRO_KCCOUNT;
			}, this);
			// 第一次打开时,dm为空
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
					, row['WMCODE'] // 货号
					, row['SUPP_CODE'] // 自有货号
					, this.sumRow[rowIndex]['PRO_KCCOUNT'] // 库存数
					, this.mapDataYC[PID]||0//this.sumRow[rowIndex]['OUT_COUNT'] // 应出数
					, this.sumRow[rowIndex]['OUT_PCOUNT'] // 分配数
					, this.sumRow[rowIndex]['OUT_RCOUNT'] // 实际出库数
				];
				for (var colIndex=0;colIndex<cm.length;colIndex++) { // 遍历子订单（列维度）
					var OID = cm[colIndex]['SUBORDERID']||('00'+(colIndex+1)), t = this.mapData[PID][OID]; // 查应出库信息 某子订单中的某产品
					var billStatus1 = cm[colIndex]['BILL_STATUS1'];
					var readOnly = billStatus1=='init'?false:true;
					//if (t && t['PRO_KCCOUNT']*1>0 && t['OUT_RCOUNT']*1<t['OUT_COUNT']*1) { // 有库存 并且 应出>实出 才能出库
					if (t && t['WAREHOUSE'] && this.mapDataKC[PID][t['WAREHOUSE']]*1>0 && this.mapData[PID]['OUT_COUNT']*1>0) { // NOTICE 测试 
						args.push(
							OID
							, (this.mapDataKC[PID][t['WAREHOUSE']]||0) // 库存
							, '<input class="td1" onchange="GRID0.change(\''+PID+'\',\''+OID+'\',\''+t['WAREHOUSE']+'\','+rowIndex+','+colIndex+',this)" value="'+(t['OUT_PCOUNT']||0)+'" '+(readOnly?'readonly':'')+'></input>' // 分配
							, (t['OUT_RCOUNT']||0) // 实出
						);
					} else if(t && t['WAREHOUSE'] && this.mapDataKC[PID][t['WAREHOUSE']]*1>0){//this.mapDataKC[PID][cm[colIndex]['ID']]*1>0 
						// TODO 这里处理第一次时候,dm还没值的情况,条件需要测试
						args.push(
								OID
								, (this.mapDataKC[PID][t['WAREHOUSE']]||0) // 库存
								, '<input class="td1" onchange="GRID0.change(\''+PID+'\',\''+OID+'\',\''+cm[colIndex]['ID']+'\','+rowIndex+','+colIndex+',this)" value="'+(t['OUT_PCOUNT']||0)+'" '+(readOnly?'readonly':'')+'></input>' // 分配
								, (t['OUT_RCOUNT']||0) // 实出
							);
					}
					else
						args.push(
							OID
							, (this.mapDataKC[PID][t['WAREHOUSE']]||0) // 库存
							, t['OUT_PCOUNT']||0 // 分配
							, 0 // 实出
						);
				}
				this.template.append(this.getGridEl(), args, true);
				var YCK = parseInt(this.getYCK(rowIndex).innerHTML);
				var SCK = parseInt(this.getSCK(rowIndex).innerHTML);
				if(YCK<SCK){
					Ext.get(this.getSCK(rowIndex)).addClass("red");
				} else {
					Ext.get(this.getSCK(rowIndex)).removeClass("red");
				}
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
		,getYCK:function(index){//应出库
			return this.getDataCellEl(index, 4);
		}
		,getSCK:function(index){//分配数
			return this.getDataCellEl(index, 5);
		}
		,getRowCount:function() {
			return this.getGridEl().dom.children[0].children.length-this.FIXROW;
		}
		,addRow:function(){
			var args = [this.getRowCount()];
			this.template.append(this.getGridEl(), args, true);
		}
		,btnClick:function(){
			// 新增仓库
			var scope = new Ext.Window({
				id:'affirmForm-win', name:'affirmForm-win', title:'选择仓库', layout:'fit', selectedids:'',
				plain:true, resizable:true, maximizable:false, animCollapse:false, collapsible:false, closable:false, modal:true,
				width:380, height:120,
				items:[
					new Ext.FormPanel({
						id:'affirmForm', defaultType:'textfield', defaults:{ bodyStyle:'padding:1px' },
						layout:'column', layoutConfig:{ columns:3, msgTarget:'side' },
						items:[{
							xtype:'label', text:'  选择需要分库的仓库', colspan:1, column:1, width:'30%'
						}, {
							xtype:'uxpagingcombo', fieldLabel:'仓库', labelStyle:'color:#990000;size:12;font-weight:bold;',
							id:'__WAREHOUSE__NAME', query_field:'NAME', valueField:'ID', displayField:'NAME', hidden_field:'WAREHOUSE_ID',
							pageSize:20, url:'ext_jsonlist.do?__opr_data=T_DIC_WAREHOUSE_COMBO&__maxNumPerPage=20',
							store_id:'ID', store_root:'T_DIC_WAREHOUSE_COMBO',
							store_fields:[{ name:'ID' }, { name:'CODE' }, { name:'NAME' }],
							colspan:1, column:2, width:'70%', emptyText:'', renderIt:false, disabled:false, allowBlank:false
						}, {
							xtype:'hidden', id:'WAREHOUSE_ID', allowBlank:false
						}]
					})
				],
				buttons:[{
					xtype:'button', text:'确认', colspan:1, column:3, width:'100%',
					handler:function() {
						var v = Ext.getCmp('WAREHOUSE_ID').getValue();
						var vName = Ext.getCmp("__WAREHOUSE__NAME").getValue();
						if (Ext.isEmpty(v)||Ext.isEmpty(vName)) {
							alert('先选择仓库');
							return;
						}
						this.ownerCt.ownerCt.close();
						GRID0.addColumn(v,vName);
						GRID0.refresh();
					}
				}]
			}).show();
		}
	};
	GRID0.render();
	//修改单据状态为下发
	function changeStatus(){
		GRID0.dm.each(function(data,index){
			data.BILL_STATUS1='send';
		},GRID0);
	};
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