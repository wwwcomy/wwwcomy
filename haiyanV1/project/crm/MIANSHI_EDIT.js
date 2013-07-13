// remoteValidate
function remoteValidate() {
	var result = byXmlhttp('comResource/jsp/crm/checkMsAddr.jsp?' //
			+ '&CITY_ID=' + Hy.getEncodeURIValue('CITY_ID') //
			+ '&MS_ADDR_QU=' + Hy.getEncodeURIValue('MS_ADDR_QU') //
			+ '&MS_ADDR_LU=' + Hy.getEncodeURIValue('MS_ADDR_LU') //
			+ '&MS_ADDR_LO=' + Hy.getEncodeURIValue('MS_ADDR_LO') //
			+ '&MS_ADDR_CE=' + Hy.getEncodeURIValue('MS_ADDR_CE') //
			+ '&ID=' + Hy.getValue('ID'));
	if (result == '')
		return true;
	var json = result.evalJSON();
	if (json.success)
		return true;
	else {
		var flag = window.confirm(json.error + ',是否继续保存?');
		if (flag) {
			$('CONFLICT').value = '1';
			return true;
		} else
			return false;
	}
	return false;
}
(function() {
	// init
	function init(flag) {
		try {
			if (typeof(comboField_ACTIVITY_ID) == 'undefined')
				return;
			if (!flag)
				comboField_ACTIVITY_ID.clearValue();
			var sFilter = '';
			{ // 合成filter: srcV[each]=srcFlds[each].getValue()
				var srcV = comboField_CUSTOMER_ID.getValue();
				srcV = Ext.isEmpty(srcV) ? '-1' : srcV;
				sFilter = '&CUSTOMER_ID=' + srcV;
			}
			comboField_ACTIVITY_ID.setFilter(sFilter);
			comboField_ACTIVITY_ID.onQuery(); // 重新查数
			comboField_ACTIVITY_ID.store.load(); // 刷新控件
		} catch (E) {
			alert(E.message);
		}
	}
	// function changeFilter(tgtFld, keys, srcFlds) {
	Hy_UIFunction['changefilter'] = function(_paras) {
		// if (tgtFld == comboField_ACTIVITY_ID)
		init(false);
	}
	init(true);
	// //
	remoteValidate = remoteValidate.createInterceptor(function() {
				try {
					if (Hy.getValue('JQ') == '1') {
						if (Hy.getValue('MS_ADDR_CE') == ''
								|| Hy.getValue('MS_ADDR_SH') == '') {
							alert('面试地址的 楼 和 室 必须填写');
							return false;
						}
					}
				} catch (E) {
					alert(E.message);
					return false;
				}
				return true;
			});
})();