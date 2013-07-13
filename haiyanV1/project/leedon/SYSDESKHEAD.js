(function() {
	var scope = new Ext.Window({
		id:'affirmForm-win', name:'affirmForm-win', title:'选择当前货主', layout:'fit', selectedids:'',
		plain:true, resizable:true, maximizable:false, animCollapse:false, collapsible:false, closable:false, modal:true,
		width:380, height:120,
		items:[
			new Ext.FormPanel({ // columns:1, // layout:'table',
				id:'affirmForm', defaultType:'textfield', defaults:{ bodyStyle:'padding:1px' },
				layout:'column', layoutConfig:{ columns:3, msgTarget:'side' },
				items:[{
					xtype:'label', text:'  选择当前使用的仓库', colspan:1, column:1, width:'30%'
				}, {
					xtype:'uxpagingcombo', fieldLabel:'仓库', labelStyle:'color:#990000;size:12;font-weight:bold;',
					id:'__WAREHOUSE__NAME', query_field:'NAME', valueField:'ID', displayField:'NAME', hidden_field:'WAREHOUSE_ID',
					pageSize:20, url:'ext_jsonlist.do?__opr_data=T_DIC_WAREHOUSE_COMBO&__maxNumPerPage=20',
					store_id:'ID', store_root:'T_DIC_WAREHOUSE_COMBO',
					store_fields:[{ name:'ID' }, { name:'CODE' }, { name:'NAME' }],
					colspan:1, column:2, width:'70%', emptyText:'', renderIt:false, disabled:false, allowBlank:false
				}, {
					xtype:'hidden', id:'WAREHOUSE_ID', allowBlank:false
				} //
				, {
					xtype:'label', text:'  选择当前库主', colspan:1, column:1, width:'30%', hidden:true
				}, {
					xtype:'uxpagingcombo', fieldLabel:'库主', labelStyle:'color:#990000;size:12;font-weight:bold;',
					id:'__WHOWNER__NAME', query_field:'NAME', valueField:'ID', displayField:'NAME', hidden_field:'WHOWNER_ID',
					pageSize:20, url:'ext_jsonlist.do?__opr_data=T_WM_OPERATOR&__maxNumPerPage=20',
					store_id:'ID', store_root:'T_WM_OPERATOR',
					store_fields:[{ name:'ID' }, { name:'CODE' }, { name:'NAME' }],
					colspan:1, column:2, width:'70%', emptyText:'', renderIt:false, disabled:false, allowBlank:false, hidden:true
				}, {
					xtype:'hidden', id:'WHOWNER_ID', allowBlank:false
				}]
			})
		],
		buttons:[{
			xtype:'button', text:'确认', colspan:1, column:3, width:'100%',
			handler:function() {
				//if (!Ext.getCmp('affirmForm').getForm().isValid()) {
				//	alert('请选择');
				//	return;
				//}
				var v = Ext.getCmp('WAREHOUSE_ID').getValue();
				if (Ext.isEmpty(v)) {
					alert('先选择当前使用的仓库');
					return;
				}
				var v2 = Ext.getCmp('WHOWNER_ID').getValue();
				//if (Ext.isEmpty(v2)) {
				//	alert('先选择当前库主');
				//	return;
				//}
				Hy.mask();
				Ext.Ajax.request({
					url:'project/leedon/SYSDESKHEAD.jsp?__out=json&__affirmWarehouseID='+v+'&__affirmWhownerID='+v2,
					method:'post',
					success:function(response, options) {
						scope.close();
						Hy.unmask();
					}
				});
			}
		}]
	}).show();
}).defer(1000);