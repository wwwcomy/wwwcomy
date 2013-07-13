
// custom renderer function
function renderLXCS(value, metaData, record, rowIndex, colIndex, store) {
	return String
			.format(
					// url id tablename anchor
					'<a href="javascript:showDetailFrame(\'{0}\', \'\', \'T_CRM_CUSTOMER_DTL\');">{1}</a>',
					'queryByForm.do?&CUSTOMER_ID=' + record.get(Hy.getIDName()),
					value);
}
// custom renderer function
function renderQYCS(value, metaData, record, rowIndex, colIndex, store) {
	return String
			.format(
					// url id tablename anchor
					'<a href="javascript:showDetailFrame(\'{0}\', \'\', \'T_CRM_CONTRACT_MODEL\');">{1}</a>',
					'queryByForm.do?&CUSTOMER_ID=' + record.get(Hy.getIDName()),
					value);
}
// custom renderer function
function renderMSCS(value, metaData, record, rowIndex, colIndex, store) {
	return String
			.format(
					// url id tablename anchor
					'<a href="javascript:showDetailFrame(\'{0}\', \'\', \'T_CRM_MIANSHI\');">{1}</a>',
					'queryByForm.do?&CUSTOMER_ID=' + record.get(Hy.getIDName()),
					value);
}
// custom renderer function
function renderOptions(value, metaData, record, rowIndex, colIndex, store,
		displayValue, colValue, fieldName) {
	if (fieldName != 'CONFLICT')
		return displayValue;
	else {
		if (record.get('CONFLICT') == '1') {
			// alert('conflictids:' + record.get('CONFLICTIDS'));
			return '<a href=\"javascript:openConflict(\''
					+ record.get('CONFLICTIDS') + '\',\'' + record.get('ID')
					+ '\')\"><font color=red>重复客户</font></a>';
		} else
			return '<font color=grean>正常</font>';
	}
}
// openConflict
function openConflict(_conflictids, _rowid) {
	Ext.Msg.confirm('确认', '您要查看冲突用户信息?', function(btn) {
		// alert('btn:' + btn);
		if (btn == 'yes' || btn == 'ok') {
			var cid = null;
			var ids = _conflictids.split(',');
			for (var i = 0; i < ids.length; i++) {
				if (ids[i].indexOf(_rowid) >= 0) {
					// 自己
				} else {
					cid = ids[i].substring(1, ids[i].length - 1);
					break;
				}
			}
			// alert('cid:' + cid);
			if (cid) // _operation, _ID, _tableName
				showDetailFrame('queryOne', cid, Hy.getTableName());
		}
	});
}
// function renderZD(value, metaData, record, rowIndex, colIndex, store) {
// return value;
// }
// /////////////////////////////////////////////////////////////////////////////////////
// Ext.onReady(function() {
var scope = this;
// // interceptorOnItemClick // for query page
// function interceptorOnItemClick(item, checked) {
// if (item.name == 'affirm') { // 指派联系客户
// }
// return true;
// }
Hy.UIFunction['crm_affirm'] = function() {
	if (scope.affirmForm == null) {
		scope.affirmForm = new Ext.Window({
			id : 'affirmForm-win',
			name : 'affirmForm-win',
			title : 'edit',
			layout : 'fit',
			selectedids : '',
			plain : true,
			resizable : true,
			maximizable : false,
			// minimizable : false,
			animCollapse : false,
			constrainHeader : true,
			collapsible : false,
			closable : false,
			width : 410,
			height : 145,
			items : [new Ext.FormPanel({
				id : 'affirmForm',
				name : 'affirmForm',
				// layout : 'table',
				// layout : 'column',
				layout : 'form',
				columns : 1,
				defaultType : 'textfield',
				defaults : {
					bodyStyle : 'padding:1px'
				},
				layoutConfig : {
					columns : 1,
					msgTarget : 'side'
				},
				width : 410,
				height : 145,
				items : [new Ext.ux.form.PagingComboBox({
					fieldLabel : '经纪人',
					labelStyle : 'color:#990000;size:12;font-weight:bold;',
					id : '__AFFIRM_USER_ID__NAME',
					conditionFields : [],
					restrictiveFields : [],
					value : '',
					valueField : 'ID',
					displayField : 'NAME',
					displayValue : '',
					hidden_field : '__AFFIRM_USER_ID',
					hidden_value : '',
					url : 'ext_jsonlist.do?__opr_data=SYSOPERATOR_VIEW&__maxNumPerPage=20',
					query_field : 'NAME',
					store_id : 'ID',
					store_root : 'SYSOPERATOR_VIEW',
					store_fields : [{
								name : 'ID'
							}, {
								name : 'CODE'
							}, {
								name : 'NAME'
							}, {
								name : 'SEX'
							}, {
								name : 'USED'
							}, {
								name : 'EMAIL'
							}, {
								name : 'MOBILE_PHONE'
							}],
					pageSize : 20,
					width : 280,
					emptyText : '',
					renderIt : false,
					disabled : false,
					allowBlank : false
				}), {
					id : '__AFFIRM_USER_ID',
					name : '__AFFIRM_USER_ID',
					xtype : 'hidden'
				},		//
						new Ext.ux.form.DateField({
							fieldLabel : '下次联系时间',
							labelStyle : 'color:#990000;size:12;font-weight:bold;',
							id : '__NEXT_CONTACT_DATE',
							name : '__NEXT_CONTACT_DATE',
							value : '',
							// value : '2009-02-21',
							format : 'Y-m-d H:i:s',
							width : 280,
							disabled : false,
							allowBlank : false
						}), {
							xtype : 'button',
							text : '确认',
							anchor : '100%',
							handler : function() {
								if (!Ext.getCmp('affirmForm').getForm()
										.isValid()) {
									return;
								}
								showMask(true);
								Ext.Ajax.request({
									url : srvContextName
											+ '/comResource/jsp/crm/crm_affirmuser.jsp?&__out=json'
											+ Ext.getCmp('affirmForm-win').selectedids
											+ '&__affirmUserID='
											+ Ext.getCmp('__AFFIRM_USER_ID').value
											+ '&__affirmNextTime='
											+ Ext.getCmp('__NEXT_CONTACT_DATE').value,
									method : 'post', // jsonData : transClientJson(data),
									success : function(response, options) {
										// Ext.getCmp('affirmForm-win').setTitle('操作成功');
										__store.reload();
										scope.affirmForm.setVisible(false);
										showMask(false);
									}
									// failure: function{alert('failure')}
								});
							}
						}, {
							xtype : 'button',
							text : '关闭',
							anchor : '100%',
							handler : function() {
								scope.affirmForm.setVisible(false);
							}
						}]
			})]
		});
	} // end if null
	var records = __selectionModel.getSelections();
	if (records.length == 0) {
		// records = '';
		alert('请选择要指派的客户.');
		return false;
	}
	if (records.length > 10) {
		alert(prompt_toomany_record); // mark too many records
		return false;
	}
	var __selectedIDs = '';
	for (var i = 0; i < records.length; i++) {
		__selectedIDs += '&__selectedID=' + records[i].get(Hy.getIDName()); // +',';
	}
	scope.affirmForm.selectedids = __selectedIDs;
	scope.affirmForm.show();
	Ext.getCmp('__AFFIRM_USER_ID__NAME').setValue('');
	return false;
}
