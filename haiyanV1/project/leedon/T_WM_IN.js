// store {sortInfo: {field: 'CODE', direction: "ASC"}}
/*function sumPRO_COST(v, params, data, fldName) {
	return 'Summary:'+parseFloat(v).toFixed(2);
}*/
function showProduct(g, r, c, _e) {
	var t = new Ext.Template('<div>NAME:{NAME} &nbsp;&nbsp;&nbsp; CODE:{CODE}</div>'
		+'<div>Long:{C1} cm&nbsp;&nbsp;&nbsp;Width:{C2} cm&nbsp;&nbsp;&nbsp;Height:{C2} cm&nbsp;&nbsp;Capacity:{CAPACITYNUM} m3</div>');
	// var r = this.getView().findRowIndex(_e.getTarget());
	// var c = this.getView().findCellIndex(_e.getTarget());
	function showTip() {
		Hy.msg('Product', Hy.UICache.PRODUCT[productID], 3);
	}
	var productID = g.getStore().getAt(r).get('PRODUCTID');
	if (productID)
		if (Hy.UICache.PRODUCT[productID]) {
			showTip();
		} else
			Ext.Ajax.request({
				url: Hy.getContextName()+'/ext_jsonedit.do?__opr_data=T_DIC_PRODUCT&ID='+productID,
				success : function(res) {
					var o=Ext.decode(res.responseText);
					if (o.__totalCount*1>0) {
						Hy.UICache.PRODUCT[productID] = t.apply(o.T_DIC_PRODUCT[0]);
						if (Hy.UICache.PRODUCT[productID]) {
							showTip();
						}
					}
					productID=null;
				}
			});
}
function renderCheckboxSelectionModel(v, meta, record, rowIndex, i, store) {
	if (store.treeField && record.get('CODE').length<6)
		return '';
    return '<div class="x-grid3-row-checker">&#160;</div>';
}
function renderPRE(value, metaData, record, rowIndex, colIndex, store, disVal, colVal, fieldName) {
	return "<button onclick=\"testPre('"+rowIndex+"');\" type=\"button\" class=\"zbutton1\" >PRE</button>";
}
function renderIN(value, metaData, record, rowIndex, colIndex, store, disVal, colVal, fieldName) {
	return "<button onclick=\"testIn('"+rowIndex+"');\" type=\"button\" class=\"zbutton1\" >IN_T</button>";
}
function testPre(rowIndex) {
	var selModel = Ext.getCmp('SUBGRID2').getSelectionModel();
	selModel.clearSelections();
	selModel.selectRow(rowIndex);
	var proCount = selModel.selections.items[0].get('PRO_COUNT');
	var proID = selModel.selections.items[0].get('PRODUCTID');
	var numPerBox = selModel.selections.items[0].get('PRO_PACKINGNUM');
	var contractID = selModel.selections.items[0].get('CONTRACTID');
	if (Hy.isEmpty(proCount) || Hy.isEmpty(proID)) {
		alert('Product and Count needed!');
		return;
	}
	if (Hy.UICache['WMLIST'])
		Hy.UICache['WMLIST'].each(function(item) {
			item.set('PRODUCTID', "");
			item.set('__PRODUCTID__NAME', "");
			item.set('PRO_COUNT', "");
			item.set('INPACK_NUM',"")
			item.set('PRO_WMCODE', "");
			item.set('PRO_CAPACITYNUM', "");
			item.set('PRO_WEIGHTNUM', "");
			item.set('PRO_IN_PRICE', "");
		});
	Hy.UICache['WMLIST'] = [];
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
	var xiangShu = Math.floor(proCount / numPerBox);
	var yushu = proCount % numPerBox;
	selModel.selections.items[0].set('REMAINDER_NUM',yushu);
	Hy.UIExp.eval("setValue(CONTRACT_ID,"+contractID+")");
	Hy.UIExp.eval("setValue(PRO_COUNT,"+proCount+") + setValue(XIANSHU," + xiangShu + ")");
	Hy.setValue("PRODUCTID",proID,null,true,function(){
		var a = Math.floor($('XIANSHU').value/Hy.UICache['WMLIST'].length)*numPerBox;
		Hy.UICache['WMLIST'].each(function(item) {
			if (item.get('CODE').length<6)
				return;
			item.set('PRODUCTID', $('PRODUCTID').value);
			item.set('__PRODUCTID__NAME', $('__PRODUCTID__NAME').value);
			item.set('PRO_COUNT', a);
			item.set('INPACK_NUM',a/numPerBox)
			item.set('PRO_WMCODE', $('HIDDEN_WMCODE').value);
			item.set('PRO_CAPACITYNUM', $('PRO_CAPACITYNUM').value);
			item.set('PRO_WEIGHTNUM', $('PRO_WEIGHTNUM').value);
			item.set('PRO_IN_PRICE', $('PRO_IN_PRICE').value);
	//		item.set('IN_TYPE', 'po');
		});
		var s = $('PRO_COUNT').value-a*Hy.UICache['WMLIST'].length;
		if (s>0)
			Hy.msg(null, 'Eccess:'+s);
	});
}
function testIn(rowIndex) {
	if (Hy.UICache['WMLIST']) {
		var g = Ext.getCmp('SUBGRID');
		if (!g)
			return;
		g.stopEditing(); 
		Hy.UICache['WMLIST'].each(function(item) {
			if (item.get('CODE').length<6 || item.get('PRO_COUNT')<=0)
				return;
			var record=new Ext.data.Record({
				__operation: 'NEW' 
				, __flag: 'insert' 
				, ID: Hy.UIExp.eval("GetNewID(T_WM_PRODUCT)") 
				, HEADID: Hy.UIExp.eval("Get(ID)") 
				, PREIN_ID: Ext.getCmp('SUBGRID2').getSelectionModel().selections.items[0].get("ID")
				, CONTRACT_ID: $('CONTRACT_ID').value
				, PRODUCTID: item.get('PRODUCTID')
				, __PRODUCTID__NAME: item.get('__PRODUCTID__NAME')
				, PRO_WH: item.get('ID')
				, __PRO_WH__CODE: item.get('CODE')
				, PRO_COUNT: item.get('PRO_COUNT')
				, INPACK_COUNT: item.get('INPACK_NUM')
				, PRO_WMCODE: item.get('PRO_WMCODE')
				, PRO_CAPACITYNUM: item.get('PRO_CAPACITYNUM')
				, PRO_WEIGHTNUM: item.get('PRO_WEIGHTNUM') 
				, PRO_IN_PRICE: item.get('PRO_IN_PRICE') 
				, PRO_IN_COST: '' 
				, PRO_OUT_PRICE: '' 
				, PRO_OUT_COST: '' 
//				, IN_TYPE: 'po' 
				, OUT_TYPE: 'init' 
				, PRO_MEMO: '' 
				, USEDSTATUS: '0' 
			});
			g.getStore().insert(g.getStore().getCount(), [record]); 
		});
		g.getView().refresh(true);
	}
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
Ext.onReady(function(){
	try {
		if ($('ID')!=null && $('ID').value=='-1') {
			alert('ID lost.Not support!)');
		}
		if ($('BILL_STATUS'))
			if ($('BILL_STATUS').value=='init'||Hy.isEmpty($('BILL_STATUS').value)) {
			} else {
				/*Ext.get('__add__SUBGRID').setVisible(false);
				Ext.get('__delete__SUBGRID').setVisible(false);
				Ext.getCmp('TEST2').setVisible(false);
				Ext.getCmp('TEST').setVisible(false);
				Ext.getCmp('XIANSHU').setVisible(false);
				Ext.getCmp('PRO_COUNT').setVisible(false);
				Ext.getCmp('__PRODUCTID__NAME').setVisible(false);*/
			}
	}catch(E){}
	// var msgCt = Ext.DomHelper.append(document.body, {id:'haiyan-msg-div2'}, true);
	// msgCt.alignTo(document, 't-t');
	Hy.UICache.PRODUCT = {};
	var g;
	g = Ext.getCmp('SUBGRID');
	if (g) {
		g.un('cellcontextmenu', showProduct, g);
		g.on('cellcontextmenu', showProduct, g);
	}
	g = Ext.getCmp('SUBGRID1');
	if (g) {
		g.un('cellcontextmenu', showProduct, g);
		g.on('cellcontextmenu', showProduct, g);
	}
	g = Ext.getCmp('SUBGRID2');
	if (g) {
		g.un('cellcontextmenu', showProduct, g);
		g.on('cellcontextmenu', showProduct, g);
		
		g.getView().getRowClass=function(rec, rowIndex, rowParams, store){
			if (rec.get('PRO_COUNT')!=rec.get('IN_COUNT'))
				return 'x-orange'; // Î´Íê³É
			else
				return '';
		}
		g.getStore().clearGrouping();
		g.getColumnModel().setHidden(2,true);
	}
});
