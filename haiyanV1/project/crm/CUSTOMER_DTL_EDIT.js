
// interceptorOnItemClick
function interceptorOnItemClick(item, checked) {
	if (item.name == 'AddNew') {
		Hy.setValue('ID', '');
		Hy.setValue('STATUS', '');
		Hy.setValue('AVAILABLE', '');
		Hy.setValue('CREATE_USER_ID', '');
		Hy.setValue('__CREATE_USER_ID__NAME', '');
		Hy.setValue('CREATE_TIME', '');
		Hy.setValue('JOURNAL_TIME', '');
		// document.location.href='edit.do?__opr_data=T_CRM_CUSTOMER_DTL';
		return false;
	}
	return true;
}
// //
Ext.onReady(function() {
	// var __tip =
	new Ext.ToolTip({ // <a href="#">
		title : '相关业务员',
		id : 'customer-user-tip',
		target : 'CUSTOMER_ID_Fld',
		anchor : 'right',
		// html : '',
		width : 125,
		// dismissDelay : 4000,
		autoHide : false,
		draggable : true,
		closable : true,
		// anchorOffset : 85,
		// autoLoad : {
		// url : 'comResource/jsp/crm/crm_usertip.jsp?__cusid=' + Hy.getValue('CUSTOMER_ID')
		// },
		// contentEl : 'content-tip',
		listeners : {
			'show' : function() {
				if (this.cusid != Hy.getValue('CUSTOMER_ID')) {
					this.cusid = Hy.getValue('CUSTOMER_ID');
					if (!Hy.isEmpty(this.cusid)) {
						var url = 'comResource/jsp/crm/crm_usertip.jsp?__cusid=' + this.cusid;
						this.load(url);
					}
				}
			}
		}
	});
});