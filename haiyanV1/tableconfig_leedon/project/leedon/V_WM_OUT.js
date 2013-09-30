//应出库明细中的预处理
function testPre(rowIndex) {
	var selModel = Ext.getCmp('SUBGRID').getSelectionModel();
	selModel.clearSelections();
	selModel.selectRow(rowIndex);
	var proID = selModel.selections.items[0].get('PRODUCTID');
	var outPrice = selModel.selections.items[0].get("OUT_PRICE");
	if (Ext.getCmp('SUBGRID2')){
		Hy.setValue("TMP_OUTPRICE",outPrice);
		Ext.getCmp('SUBGRID2').setFilter('PRODUCTID='+(proID||-1));
		Ext.getCmp('SUBGRID2').onStoreLoad();
	}
}

//应出库明细审批
function testAudit(rowIndex, p) {
	p=p||"audit";
	var selModel = Ext.getCmp('SUBGRID').getSelectionModel();
	selModel.clearSelections();
	selModel.selectRow(rowIndex);
	var data='{"__opr_data":"V_WM_OUTPRE","__action":"plugin","__pluginName":"'+p+'","__formSet":['+Ext.encode(selModel.selections.items[0].data)+']}';
	Ext.Ajax.request({
		url:Hy.getContextName()+'/xmlhttp_json.do?__out=json' 
		, method:'post' , jsonData:transClientJson(data)
		, success:function(response, options) {
			Ext.getCmp('SUBGRID1').getStore().reload();
		}
	});
}

//明细出库按钮
Hy.UIFunction.testout = function() {
	var g = Ext.getCmp('SUBGRID1');
	var selModel = Ext.getCmp('SUBGRID2').getSelectionModel();
	var selections = selModel.selections;
 	if(selections.items.length<=0){
		alert("请选择预出库明细!");
		return;
	}
	selections.items.each(function(item) {
		var count = 0;
		if (item.get('OUT_COUNT')<=0)
			count = item.get('PRO_COUNT');
		else
			count = item.get('OUT_COUNT');
		var record=new Ext.data.Record({
			__operation: 'NEW', __flag: 'insert'
			, ID: Hy.UIExp.eval("GetNewID(T_WM_OUTDETAIL)")
			, HEADID: Hy.UIExp.eval("Get(ID)")
			, PRO_NAME: item.get('PRO_NAME')
			, PRODUCTID: item.get('PRODUCTID')
			, __PRODUCTID__CODE: item.get('__PRODUCTID__CODE')
			, SUPP_CODE: item.get('SUPP_CODE')
			, BATCH: item.get('BATCH')
			, PRO_WH: item.get('WAREWHERE')
			, __PRO_WH__CODE: item.get('__WAREWHERE__CODE')
			, PRO_COUNT: count
			, PRO_WMCODE: item.get('PRO_WMCODE')
			, PRO_CAPACITYNUM: item.get('PRO_CAPACITYNUM')
			, PRO_WEIGHTNUM: item.get('PRO_WEIGHTNUM') 
			, PRO_IN_PRICE: item.get('PRO_IN_PRICE')
			//, PRO_OUT_PRICE: item.get('PRO_OUT_PRICE')
			, PRO_OUT_PRICE: $("TMP_OUTPRICE").value
			, OUT_TYPE: 'init' 
			, PRO_MEMO: '' 
		});
		g.getStore().insert(g.getStore().getCount(), [record]); 
	});
	g.getView().refresh(true);
	Ext.getCmp('SUBGRID2').getSelectionModel().clearSelections();
	Ext.getCmp('SUBGRID1').getSelectionModel().clearSelections();
	
	
}
Ext.onReady(function(){
});
