
function renderREM(value, metaData, record, rowIndex, colIndex, store, disVal, colVal, fieldName) {
	if (record.get('CODE').length<6)
		return "";
	return "<button onclick=\"testRem("+rowIndex+");\" type=\"button\" class=\"zbutton1\" >REM</button>";
}
function renderPRE(value, metaData, record, rowIndex, colIndex, store, disVal, colVal, fieldName) {
	return "<button onclick=\"testPre('"+rowIndex+"');\" type=\"button\" class=\"zbutton1\" >PRE</button>";
}

function renderTreeCol(value, metaData, record, rowIndex, colIndex, store, disVal, colVal, fieldName) {
	var parentID=record.get('PARENTID'), isLeaf=record.get('ISLEAF')*1==1, treeCol=record.get(fieldName)  
		,isExpand=false, bufLevel=[]; // special level
	if (!treeCol)
		return value;
	if (Ext.isEmpty(record.data['__isExpand']))
		record.data['__isExpand']=isExpand;
	else
		isExpand=record.data['__isExpand'];
	for (var i=2;i<treeCol.length;i+=2) {
		bufLevel.push('&nbsp;&nbsp;');
	}
	var indent=bufLevel.join(''),
		levelIconTag=['<img src="', Ext.BLANK_IMAGE_URL, 
			'" class="x-tree-ec-icon x-tree-elbow', isLeaf?'':(isExpand?'-minus':'-plus'),
			'" ', isLeaf?'':' style="width:16px;height:18px;background:url(comResource/images/default/tree/elbow'
				+(isExpand?'-minus':'-plus')+'.gif);" ',
			' />'].join(''),
		nodeIconTag=['<img src="', Ext.BLANK_IMAGE_URL, 
			'" class="x-tree-node-icon', ' x-tree-node-inline-icon',
			'" ', isLeaf?'':' style="width:16px;height:18px;background:url(comResource/images/default/tree/folder'
				+(isExpand?'-open':'')+'.gif);" ', 
			' />'].join('');
	var v=['<div class="x-tree-node-el ', 
		isLeaf?'x-tree-node-leaf':(true?'x-tree-node-expanded':'x-tree-node-collapsed'), 
		' x-unselectable " unselectable="on">',
		'<span class="x-tree-node-indent">', indent, "</span>",
		levelIconTag,
		nodeIconTag,
		'<span>', value, "</span></div>"
		].join('');
	return v;
}

//预入库按钮
Hy.UIFunction.testprebtn=function() {
	var proCount=$('PRO_COUNT').value;
	var proID=$('PRODUCTID').value;
	var numPerBox=$('PRO_PACKINGNUM').value;
	if (Hy.isEmpty(proCount) || Hy.isEmpty(proID) || proCount == 0) {
		alert('Product and Count needed!');
		return;
	}
	if (numPerBox == 0) {
		alert('Amount per Box is 0! Please check the product.');
		return;
	}
	if (Hy.UICache['WMLIST'])
		Hy.UICache['WMLIST'].each(function(item) {
			item.set('PRODUCTID', "");
			item.set('__PRODUCTID__NAME', "");
			item.set('SUPP_CODE', "");
			item.set('PRO_COUNT', "");
			item.set('INPACK_NUM',"");
			item.set('PRO_WMCODE', "");
			//item.set('PRO_CAPACITYNUM', "");
			//item.set('PRO_WEIGHTNUM', "");
			item.set('PRO_IN_PRICE', "");
		});
	Hy.UICache['WMLIST']=[];
	if (Ext.getCmp('SUBGRID1'))
		Ext.getCmp('SUBGRID1').getSelectionModel().selections.each(function(item) {
			if (item.get('CODE').length==6) {
				Hy.UICache['WMLIST'][Hy.UICache['WMLIST'].length]=item;
			}
		});
	if (Hy.UICache['WMLIST'].length<1){
		alert('Ware position needed!');
		return;
	}
	var xiangShu=Math.floor(proCount / numPerBox);
	var yushu=proCount % numPerBox;
	$('REMAINDER_NUM').value=yushu;
	$('BATCH').value="";
	$('PRO_IN_PRICE').value="";
	$('PRO_OUT_PRICE').value="";
	$('ORDER_ID').value="";
	$('SUPP_CODE').value="";
	Hy.UIExp.eval("setValue(PRO_COUNT,"+proCount+")+setValue(XIANSHU,"+xiangShu+")");
	//这里要到应入库明细里面去查一下对应商品的出入库价格和批次
	Ext.getCmp('SUBGRID2').getStore().each(function(item){
		if(item.get("PRODUCTID")==proID){
			var inPrice=item.get("IN_PRICE");
			var outPrice=item.get("OUT_PRICE");
			var orderID=item.get("ORDER_ID");
			Hy.UIExp.eval("setValue(PRO_IN_PRICE,"+inPrice+")+setValue(PRO_OUT_PRICE,"+outPrice+")+setValue(ORDER_ID,"+orderID+")");
		}
	});
	Hy.setValue("PRODUCTID",proID,null,true,function(){
		$('REMAINDER_NUM').value=0;
		var a=Math.floor($('XIANSHU').value/Hy.UICache['WMLIST'].length)*numPerBox;
		Hy.UICache['WMLIST'].each(function(item) {
			if (item.get('CODE').length<6)
				return;
			
			item.set('PRODUCTID', $('PRODUCTID').value);
			item.set('SUPP_CODE', $('SUPP_CODE').value);
			item.set('__PRODUCTID__NAME', $('__PRODUCTID__CODE').value);
			item.set('PRO_COUNT', a);
			item.set('INPACK_NUM',a/numPerBox);
			item.set('PRO_WMCODE', $('HIDDEN_WMCODE').value);
			//item.set('PRO_CAPACITYNUM', $('PRO_CAPACITYNUM').value);
			//item.set('PRO_WEIGHTNUM', $('PRO_WEIGHTNUM').value);
			item.set('PRO_IN_PRICE', $('PRO_IN_PRICE').value);
			item.set('PRO_OUT_PRICE', $('PRO_OUT_PRICE').value);
		});
		var s=$('PRO_COUNT').value-a*Hy.UICache['WMLIST'].length;
		if (s>0) {
			Hy.msg(null, 'Eccess:'+s);
			$('REMAINDER_NUM').value=s;
		}
	});
}
//明细入库
Hy.UIFunction.test2=function(rowIndex) {
	if (Hy.UICache['WMLIST']) {
		var g=Ext.getCmp('SUBGRID');
		if (!g)
			return;
		g.stopEditing(); 
		Hy.UICache['WMLIST'].each(function(item) {
			if (item.get('CODE').length<6 || item.get('PRO_COUNT')<=0)
				return;
			var record=new Ext.data.Record({
				__operation: 'NEW' 
				, __flag: 'insert' 
				, ID: Hy.UIExp.eval("GetNewID(T_WM_INDETAIL)") 
				, HEADID: Hy.UIExp.eval("Get(ID)") 
				//, PREIN_ID: Ext.getCmp('SUBGRID2').getSelectionModel().selections.items[0].get("ID")
				, PREIN_ID:""
				, PRODUCTID: item.get('PRODUCTID')
				, __PRODUCTID__NAME: item.get('__PRODUCTID__NAME')
				, PRO_NAME: item.get('__PRODUCTID__NAME')
				, SUPP_CODE: item.get('SUPP_CODE')
				, BATCH: Ext.isEmpty(item.get('BATCH'))?-1:item.get('BATCH')
				, PRO_WH: item.get('ID')
				, __PRO_WH__CODE: item.get('CODE')
				, PRO_COUNT: item.get('PRO_COUNT')
				, INPACK_COUNT: item.get('INPACK_NUM')
				, __PRODUCTID__CODE: item.get('PRO_WMCODE')
				, PRO_WMCODE: item.get('PRO_WMCODE')
				//, PRO_CAPACITYNUM: item.get('PRO_CAPACITYNUM')
				//, PRO_WEIGHTNUM: item.get('PRO_WEIGHTNUM') 
				, ORDER_ID: $("ORDER_ID").value
				, PRO_IN_PRICE: item.get('PRO_IN_PRICE') 
				, PRO_IN_COST: '' 
				, PRO_OUT_PRICE: item.get('PRO_OUT_PRICE') 
				, PRO_OUT_COST: '' 
				, OUT_TYPE: 'init' 
				, PRO_MEMO: '' 
				, USEDSTATUS: '0' 
			});
			g.getStore().insert(g.getStore().getCount(), [record]); 
		});
		g.getView().refresh(true);
		if (Hy.UICache['WMLIST']){
			Hy.UICache['WMLIST'].each(function(item) {
				item.set('PRODUCTID', "");
				item.set('__PRODUCTID__NAME', "");
				item.set('SUPP_CODE', "");
				item.set('BATCH', "");
				item.set('PRO_COUNT', "");
				item.set('INPACK_NUM',"")
				item.set('PRO_WMCODE', "");
				//item.set('PRO_CAPACITYNUM', "");
				//item.set('PRO_WEIGHTNUM', "");
				item.set('PRO_IN_PRICE', "");
			});
			Hy.UICache['WMLIST'].clear();
		}
		$('ORDER_ID').value="";
		Ext.getCmp('SUBGRID2').getSelectionModel().clearSelections();
		Ext.getCmp('SUBGRID1').getSelectionModel().clearSelections();
	}
}
//批次信息
Hy.UIFunction.getbatchinfo=function(){
	Hy.setValue('BATCHINFO', "");
	var proID = Hy.getValue('PRODUCTID');
	if(Ext.isEmpty(proID))
		return false;
	var preGridStore=Ext.getCmp('SUBGRID2').getStore();
	var orderID = "";
	preGridStore.each(function(item){
		if(proID===item.get("PRODUCTID"))
			orderID=item.get("ORDER_ID");
	});
	if(Ext.isEmpty(orderID))
		return false;
	Ext.Ajax.request( {
		url : 'project/leedon/T_WM_SDBRETURN.jsp?__opr=getBatch&__pid='+proID+'&__orderID='+orderID
		, success : function(response) {
			var d = Ext.decode(response.responseText);
			if (d.success)
				Hy.setValue('BATCHINFO', d.data);
		}
	});	
}
//余数处理
function testRem(rowIndex) {
	var remNum=$('REMAINDER_NUM').value;
	var numPerBox=$('PRO_PACKINGNUM').value;
	if (!remNum || remNum<=0) {
		alert('No REMAINDER!');
		return;
	}
	remNum=parseInt(remNum);
	$('REMAINDER_NUM').value=0;
	var selModel=Ext.getCmp('SUBGRID1').getSelectionModel();
	if(selModel.isSelected(rowIndex)){
		var id=Ext.getCmp('SUBGRID1').store.getAt(rowIndex).id;
		var item1=Ext.getCmp('SUBGRID1').getSelectionModel().selections.key(id);
		//item1.set('__PRODUCTID__NAME', $('__PRODUCTID__NAME').value);
		item1.set('__PRODUCTID__NAME', $('__PRODUCTID__CODE').value);
		item1.set('PRO_COUNT', item1.get('PRO_COUNT')+remNum);
		var PN = remNum /numPerBox;
		var intPn = Math.floor(remNum /numPerBox);
		item1.set('INPACK_NUM',item1.get('INPACK_NUM')+intPn+(intPn<PN?1:0));
		item1.set('PRO_WMCODE', $('HIDDEN_WMCODE').value);
		//item1.set('PRO_CAPACITYNUM', $('PRO_CAPACITYNUM').value);
		//item1.set('PRO_WEIGHTNUM', $('PRO_WEIGHTNUM').value);
		item1.set('SUPP_CODE', $('SUPP_CODE').value);
		if(!Ext.isEmpty(item1.get('PRO_IN_PRICE')))
			return;
		item1.set('PRO_IN_PRICE', $('PRO_IN_PRICE').value);
		item1.set('PRO_OUT_PRICE', $('PRO_OUT_PRICE').value);
		return;
	}
	selModel.selectRow(rowIndex,true);
	Hy.UICache['WMLIST'][Hy.UICache['WMLIST'].length]=selModel.selections.items[selModel.selections.items.length-1];
	var item=selModel.selections.items[selModel.selections.items.length-1];
	item.set('PRODUCTID', $('PRODUCTID').value);
	//item.set('__PRODUCTID__NAME', $('__PRODUCTID__NAME').value);
	item.set('__PRODUCTID__NAME', $('__PRODUCTID__CODE').value);
	item.set('PRO_COUNT', remNum);
	item.set('INPACK_NUM',1)
	item.set('PRO_WMCODE', $('HIDDEN_WMCODE').value);
	//item.set('PRO_CAPACITYNUM', $('PRO_CAPACITYNUM').value);
	//item.set('PRO_WEIGHTNUM', $('PRO_WEIGHTNUM').value);
	item.set('PRO_IN_PRICE', $('PRO_IN_PRICE').value);
	item.set('PRO_OUT_PRICE', $('PRO_OUT_PRICE').value);
	item.set('SUPP_CODE', $('SUPP_CODE').value);
}


Hy.UIFunction.checkeq=function() {
	var preGridStore=Ext.getCmp('SUBGRID2').getStore();
	Hy.UICache['CHECKLIST']=[];
	preGridStore.each(function(item){
		Hy.UICache['CHECKLIST'][Hy.UICache['CHECKLIST'].length]=item;
	});
	var g=Ext.getCmp('SUBGRID');
	if (!g)
		return 1;
	var realGridStore=g.getStore();
	Hy.UICache['PROLIST']=[];
	realGridStore.sort('PRODUCTID','ASC');
	var temp=0, realCount=realGridStore.getCount();
	for(var i=0;i<realCount;i++){
		var proID=realGridStore.getAt(i).get('PRODUCTID');
		if(i==0){
			Hy.UICache['PROLIST'][Hy.UICache['PROLIST'].length]=new Ext.data.Record({
				PRODUCTID:proID
				, PRO_COUNT:parseInt(realGridStore.getAt(i).get('PRO_COUNT'))
			});
		}
		else if((i>0 && proID!=realGridStore.getAt(i-1).get('PRODUCTID'))) {
			Hy.UICache['PROLIST'][Hy.UICache['PROLIST'].length]=new Ext.data.Record({
				PRODUCTID:proID
				, PRO_COUNT:parseInt(realGridStore.getAt(i).get('PRO_COUNT'))
			});
		} else {
			Hy.UICache['PROLIST'][Hy.UICache['PROLIST'].length-1].data.PRO_COUNT += parseInt(realGridStore.getAt(i).get('PRO_COUNT'));
		}
	}
	var a,b;
	for(var i=0;i<Hy.UICache['PROLIST'].length;i++){
		for(var j=0; j<Hy.UICache['CHECKLIST'].length;j++){
			var inPID=Hy.UICache['CHECKLIST'][j].get('PRODUCTID');
			var rPID=Hy.UICache['PROLIST'][i].get('PRODUCTID');
			if(rPID==inPID){
				a=Hy.UICache['PROLIST'][i].get('PRO_COUNT'),b=Hy.UICache['CHECKLIST'][j].get('RETURN_RCOUNT');
				if(a==b)
					break;
				else{ // __CONTRACT_CODE__CONTRACT_CODE
					alert("商品为:"+Hy.UICache['CHECKLIST'][j].get('__OUT_CODE__OUT_CODE')+" 的 [实际退货数量"+a+"] 与 [应实退货数量"+b+"] 不符!");
					return 0;
				}
			}
		}
	}
	return 1;
}

Ext.onReady(function(){

	var g;
	g=Ext.getCmp('SUBGRID2');
	
	var resizer = new Ext.Resizable('SUBGRID2', {
		handles: 'e' 
		,resizeChild:true
		,pinned: true
	});
	resizer.on('resize', function() {
		var box = resizer.getEl().getSize();
		this.setSize(box);
		this.doLayout();
	}, g);
});
