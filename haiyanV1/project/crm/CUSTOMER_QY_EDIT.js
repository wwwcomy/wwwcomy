// role
var __isAdmin = false;
var __isBoss = false;
var __isManager = false;
(function() {
	/*if (Hy.getRoleCodes)
		Hy.getRoleCodes().each(function(el) {
			if ('admin' == el)
				__isAdmin = true;
			else if ('boss' == el)
				__isBoss = true;
			else if ('manager' == el)
				__isManager = true;
		});*/
})();
// showOr HideObj(document.T_CRM_CUSTOMER_QY.REMARK.parentNode);
function remoteValidate() {
	// try {alert($('QQ'));}catch(E) {alert(E.message);}
	if ($('EMAIL').value == '' && $('MSN').value == '' && $('QQ').value == '') {
		// EMAIL // MSN // QQ
		alert('"QQ msn 邮箱" 三项可以不全填但必填一个');
		return false;
	}
	var result = byXmlhttp('comResource/jsp/crm/checkCusName.jsp?' //
			+ '&NAME=' + encodeURI($('NAME').value) //
			+ '&ADDRESS=' + encodeURI($('ADDRESS').value) //
			+ '&ID=' + $('ID').value);
	if (result == '')
		return true;
	var json = result.evalJSON();
	if (json.success)
		return true;
	else {
		$('CONFLICT').value = '1';
		if (!__isAdmin && !__isBoss && !__isManager) { // 业务员不提示冲突
			return true;
		}
		var flag = window.confirm(json.error + ',是否继续保存?');
		if (flag) {
			return true;
		} else
			return false;
	}
	return false;
}