// //
// Ext.onReady(function() {
// // etc
// Ext.getCmp('SUBGRID1').on('rowclick', function(grid, rowIndex, e) {
// // var record = grid.getStore().getAt(rowIndex); // Get
// // the Record
// });
// Ext.getCmp('SUBGRID2').getStore().proxy.url =
// Hy.getContextName()+'/ext_jsonlist.do?__opr_data=T_RO_CKBILL_DTL'
// + '&__maxNumPerPage=5000&__go2pageNum=1&BTYPE=CK&HEADID='
// + $('ID').value;
// Ext.getCmp('SUBGRID3').getStore().proxy.url =
// Hy.getContextName()+'/ext_jsonlist.do?__opr_data=T_RO_THBILL_DTL'
// + '&__maxNumPerPage=5000&__go2pageNum=1&BTYPE=TH&HEADID='
// + $('ID').value;
// //
// // if ($('STATUS').value == '2') {
// // Ext.getCmp('SUBGRID1').setDisabled(true);
// // } else if ($('STATUS').value == '3') {
// // Ext.getCmp('SUBGRID1').setDisabled(true);
// // Ext.getCmp('SUBGRID2').setDisabled(true);
// // }
// // Ext.getCmp('SUBGRID2').setVisible(false);
// // Ext.getCmp('SUBGRID3').setVisible(false);
// //
// // function recordNewSUBGRID1() {
// // return new Ext.data.Record({
// // ID : '',
// // HEADID : "'" + $('ID').value + "'",
// // __operation : 'HAHA'
// // });
// // }
// // Ext.getCmp('SUBGRID2').getStore().each(function(record2) {
// // if (record2.data.MATER == record.data.MATER
// // && record2.data.XH == record.data.XH)
// // ret += record2.data.NUM * 1;
// // });
// // Ext.getCmp('SUBGRID3').getStore().each(function(record2) {
// // if (record2.data.MATER == record.data.MATER
// // && record2.data.XH == record.data.XH)
// // ret += record2.data.NUM * 1;
// // });
// // (Ext.getCmp('SUBGRID2').getStore().getTotalCount());
// });
// //
// var protocol = new MyWebProtocol({ // xml协议
// parameters : [['__opr_name', 'savePlugin'],
// ['__pluginName', 'saveProtocol']]
// });
// function interceptorOnItemClick(item, checked) { // 监听器
// // (item.name);
// if (item.name == 'saveProtocol') {
// try {
// if (!checkForm())
// return false;
// protocol.addMyWebForm(Hy.getMainForm()); // 主表
// protocol.addExtGrid(Hy.getCmp('SUBGRID1')); // 从表1
// protocol.addExtGrid(Hy.getCmp('SUBGRID2')); // 从表2
// protocol.addExtGrid(Hy.getCmp('SUBGRID3')); // 从表3
// // (protocol.toProtocol());
// protocol.commit(srvContextName + '/xmlhttp_p.do?');
// protocol.clear();
// // load grid
// Ext.getCmp('SUBGRID1').getStore().load();
// Ext.getCmp('SUBGRID2').getStore().load();
// Ext.getCmp('SUBGRID3').getStore().load();
// } catch (E) {
// (E.message);
// }
// return false; // intercept
// }
// }
// // 物料store
// var storeMATER = new Ext.data.JsonStore({ // Store({
// // totalProperty : '__totalCount',
// url :
// Hy.getContextName()+'/ext_jsonlist.do?__opr_data=T_RO_MATER&__maxNumPerPage=5000&__go2pageNum=1',
// autoLoad : true,
// root : 'T_RO_MATER',
// fields : ['ID', 'CODE', 'NAME', 'XH', 'HSDJ', 'BHSDJ']
// });
// // 物料描绘器
// function rendererMATER(value, metaData, record, rowIndex, colIndex, store,
// displayValue, refValue, fieldName) {
// var ret = null;
// storeMATER.filter('ID', value);
// storeMATER.each(function(record2) {
// if (record2.data.ID == value) {
// ret = record2.data.CODE;
// // if (Ext.isEmpty(record2.data.MATER_NAME))
// record.set('MATER_NAME', record2.data.NAME);
// if (Ext.isEmpty(record.data.XH))
// record.set('XH', record2.data.XH);
// if (Ext.isEmpty(record.data.HSDJ))
// record.set('HSDJ', record2.data.HSDJ * 1);
// // else
// // record.set('HSDJ', record.data.HSDJ * 1);
// if (Ext.isEmpty(record.data.BHSDJ))
// record.set('BHSDJ', record2.data.BHSDJ * 1);
// // else
// // record.set('BHSDJ', record.data.BHSDJ * 1);
// }
// });
// if (Ext.isEmpty(ret))
// ret = displayValue;
// if (Ext.isEmpty(ret))
// return '';
// if (typeof(record.data.CONFLICT) != 'undefined'
// && record.data.CONFLICT == '1')
// return '<font color=red>' + ret + '</font>';
// else
// return ret;
// }
// renderer col
function rendererHSDJ(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	return (value * 1).toFixed(2);
}
function rendererBHSDJ(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	return (value * 1).toFixed(2);
}
function rendererCKNUM(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	if (Ext.isEmpty(value))
		return 0;
	return (value * 1);
}
function rendererTHNUM(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	if (Ext.isEmpty(value))
		return 0;
	return (value * 1);
}
// summary row
function rendererHSHJ(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	var ret = 0;
	try {
		ret = ((record.data.NUM) * record.data.HSDJ).toFixed(2);
		if (typeof(record.data.CONFLICT) != 'undefined'
				&& record.data.CONFLICT == '1')
			return '<font color=red>' + ret + '</font>';
		else
			return ret;
	} catch (E) {
		// (E.message);
		return ret;
	}
}
function rendererBHSHJ(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	var ret = 0;
	try {
		ret = ((record.data.NUM) * record.data.BHSDJ).toFixed(2);
		if (typeof(record.data.CONFLICT) != 'undefined'
				&& record.data.CONFLICT == '1')
			return '<font color=red>' + ret + '</font>';
		else
			return ret;
	} catch (E) {
		// (E.message);
		return ret;
	}
}
// summary totalCost
Ext.grid.GroupSummary.Calculations['totalCost1'] = function(v, record, field) {
	return v + ((record.data.NUM) * record.data.HSDJ);
}
Ext.grid.GroupSummary.Calculations['totalCost2'] = function(v, record, field) {
	return v + ((record.data.NUM) * record.data.BHSDJ);
}
function rendererHSHJ2(value, params, data, fldName) {
	return (value * 1).toFixed(2);
}
function rendererBHSHJ2(value, params, data, fldName) {
	return (value * 1).toFixed(2);
}
