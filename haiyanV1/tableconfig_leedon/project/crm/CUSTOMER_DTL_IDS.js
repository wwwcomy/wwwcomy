// custom renderer function
function renderValue(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	if (colIndex >= 3) // NEXT_CONTACT_DATE
		return String.format(
			'<div ext:qtip="绿色:正常完成, 蓝色:超期完成, 橘色:当日未完成, 红色:超期未完成, 灰色:无效">'
					+ '<a href="javascript:showDetailFrame(\'{0}\', \'{1}\');"><font color={3}>{2}</font></a></div>',
			'edit', record.get(Hy.getIDName()), value, 
			judgeDate(record.get('NEXT_CONTACT_DATE'), record.get('STATUS'), record.get('CQZT'),record.get('AVAILABLE'))
		);
	else
		return value;
}
// renderRef
function renderRef(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	// if (fieldName != 'USER_ID' && fieldName != 'CREATE_USER_ID'
	// && fieldName != 'CUSTOMER_ID') {
	// return displayValue;
	// }
	return String.format(
		'<div ext:qtip="绿色:正常完成, 蓝色:超期完成, 橘色:当日未完成, 红色:超期未完成, 灰色:无效">'
				+ '<font color={1}>{0}</font></div>', displayValue,
		judgeDate(record.get('NEXT_CONTACT_DATE'), record.get('STATUS'), record.get('CQZT'), record.get('AVAILABLE')));
}
// renderOptions
function renderOptions(value, metaData, record, rowIndex, colIndex, store,
		displayValue, refValue, fieldName) {
	return String.format(
		'<div ext:qtip="绿色:正常完成, 蓝色:超期完成, 橘色:当日未完成, 红色:超期未完成, 灰色:无效">'
				+ '<font color={1}>{0}</font></div>', displayValue,
		judgeDate(record.get('NEXT_CONTACT_DATE'), record.get('STATUS'), record.get('CQZT'), record.get('AVAILABLE')));
}
// judgeDate
function judgeDate(dateJour, status, cqzt, available) {
	if (available == 0) {
		return 'gray'; // 无效
	}
	if (status == 1) {
		if (cqzt == 1)
			return 'blue'; // 超期完成
		return 'purpleyellow'; // 正常完成
	}
	if (Ext.isEmpty(__currDateTime))
		return;
	if (Ext.isEmpty(dateJour))
		return;
	// debugJs('judgeDate:'+(status==1)+','+status);
	var yN = __currDateTime.substring(0, 4);
	var mN = __currDateTime.substring(6, 7);
	var dN = __currDateTime.substring(9, 10);
	var yJ = dateJour.substring(0, 4);
	var mJ = dateJour.substring(6, 7);
	var dJ = dateJour.substring(9, 10);
	var dtN = 1 * replaceAll(__currDateTime.substring(0, 10), '-', '');
	var dtJ = 1 * replaceAll(dateJour.substring(0, 10), '-', '');
	// var dtN = new Date(yN, mN, dN);
	// var dtJ = new Date(yJ, mJ, dJ);
	// (dtN + '<' + dtJ + ':' + (dtN < dtJ));
	if (dtN == dtJ) {
		return 'orange'; // 当日未完成
	} else if (dtN > dtJ) {
		return 'red'; // 超期未完成
	}
	// else if (dtN < dtJ) {
	// return 'blue'; // 正常未完成
	// }
	else {
		return ''; // 其他
	}
	// (Date.parseDate('2009-01-23', 'yyyy-MM-dd'));
	// ("2009-01-23 12:32:12" > "2009-01-23 12:32:00");
}